package edu.birzeit.chatproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginPage extends AppCompatActivity {
    //And I need you to link this with mysql so i can also check for logins in firebase authintecation
    //We need Email and password to be saved in sharedPreferences and
    //Move with intents (userName), so we could use it in system messsages
    EditText email;
    EditText pass;
    Button signIn;
    TextView singIN;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        email = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        signIn = findViewById(R.id.login);
        singIN = findViewById(R.id.SignUP);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
//        checkLoggedIn();
        singIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailU = email.getText().toString();
                String passU = pass.getText().toString();
                if (emailU.matches("")) {
                    email.setError("Enter email");
                } else if (passU.matches("")) {
                    pass.setError("Enter password first");
                } else if (!emailU.matches(emailPattern)) {
                    Toast.makeText(LoginPage.this, "Enter valid email and password", Toast.LENGTH_SHORT).show();
                } else if (passU.length() < 6) {
                    Toast.makeText(LoginPage.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                } else {
                    //Try logging in
                    SignINAsyncTask signinAsyncTask = new SignINAsyncTask();
                    signinAsyncTask.execute(emailU, passU);
                }
            }
        });
    }

    private void checkLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoggedIn", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("loginStatus", false);
        if (isLoggedIn) {
            //Make this to mainActivity hazem
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
            finish();
        }
    }

    //Check for logging in or not in MySQL DATA base
    private class SignINAsyncTask extends AsyncTask<String, Void, String> {
        private static final String SIGNIN_URL = "http://192.168.1.25:1234/androidProj/login.php";
        private String emailU;
        private String passwordU;

        @Override
        protected String doInBackground(String... strings) {
            emailU = strings[0];
            passwordU = strings[1];

            try {
                URL url = new URL(SIGNIN_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                //Build the POST DATA TO BE GIVEN TO PHP
                StringBuilder data = new StringBuilder();
                data.append(URLEncoder.encode("email", "UTF-8")).append("=").append(URLEncoder.encode(emailU, "UTF-8"));
                data.append("&").append(URLEncoder.encode("password", "UTF-8")).append("=")
                        .append(URLEncoder.encode(passwordU, "UTF-8"));
                //Send that data
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(data.toString().getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                //Read response NOTE: in php is given as echo
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();

                //
            } catch (Exception e) {
                Log.e("PHPconnection", e.getMessage().toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && result.equals("logged In")) {//For the other uses of firebase i also want to mark user loged in on firebase here
                signInToFireBase();
            } else {
                Log.e("post", result + "");
                Toast.makeText(LoginPage.this, "SignIn failed. Please try again", Toast.LENGTH_SHORT).show();
            }
        }

        private void signInToFireBase() {
            firebaseAuth.signInWithEmailAndPassword(emailU, passwordU).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginPage.this, HomePage.class);
                        intent.putExtra("User-id", firebaseAuth.getUid());
                        SharedPreferences sharedPreferences = getSharedPreferences("LoggedIn", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("loginStatus", true);
                        editor.putString("email", emailU);
                        editor.putString("password", passwordU);
                        editor.apply();
                        startActivity(intent);
                        finish();
//                        Toast.makeText(LoginPage.this, "Logged in succesffully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginPage.this, "Failed to log in", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}