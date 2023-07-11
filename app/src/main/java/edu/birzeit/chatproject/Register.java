package edu.birzeit.chatproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Register extends AppCompatActivity {
    private TextView login;
    private EditText userName;
    private EditText email;
    private EditText pass;
    private EditText passRe;
    private Spinner major;
    private Button signupbtn;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    private String savedEmail;
    private String savedPassword;
    private String savedPasswordRe;
    private String savedUser;

    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String RePASSWORD_KEY = "Repassword";
    private static final String SAVED_USER = "userName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Get needed views items
        email = findViewById(R.id.rgemail);
        userName = findViewById(R.id.username);
        pass = findViewById(R.id.rgpassword);
        passRe = findViewById(R.id.rgrepassword);
        major = findViewById(R.id.list_major);
        signupbtn = findViewById(R.id.signupbutton);
        login = findViewById(R.id.loginbut);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Uname = userName.getText().toString();
                String Uemail = email.getText().toString().toLowerCase();
                String Upass = pass.getText().toString();
                String reEnterpass = passRe.getText().toString();
                String Umajor = major.getSelectedItem().toString();

                if (Uname.matches("")) {
                    userName.setError("Enter user name");
                } else if (Uemail.matches("")) {
                    email.setError("Enter email first");
                } else if (Upass.matches("")) {
                    pass.setError("Enter password first");
                } else if (reEnterpass.matches("")) {
                    passRe.setError("Re enter password");
                } else if (!Uemail.matches(emailPattern)) {
                    Toast.makeText(Register.this, "Enter valid email and password", Toast.LENGTH_SHORT).show();
                } else if (Upass.length() < 6) {
                    Toast.makeText(Register.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                } else if (!Upass.equals(reEnterpass)) {
                    Toast.makeText(Register.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                } else {
                    SignupAsyncTask signupAsyncTask = new SignupAsyncTask();
                    signupAsyncTask.execute(Uname, Uemail, Upass, Umajor);
                }
            }
        });

        // Restore saved email and password if available
        if (savedInstanceState != null) {
            savedEmail = savedInstanceState.getString(EMAIL_KEY);
            savedPassword = savedInstanceState.getString(PASSWORD_KEY);
            savedUser = savedInstanceState.getString(SAVED_USER);
            savedPasswordRe = savedInstanceState.getString(RePASSWORD_KEY);
        }

        if (savedEmail != null && savedPassword != null) {
            email.setText(savedEmail);
            pass.setText(savedPassword);
        }
        if (savedUser != null) {
            userName.setText(savedUser);
        }
        if (savedPasswordRe != null) {
            passRe.setText(savedPasswordRe);
        }
//                singUP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//
//         }
//        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        savedEmail = email.getText().toString();
        savedPassword = pass.getText().toString();
        savedPasswordRe = passRe.getText().toString();
        savedUser = userName.getText().toString();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EMAIL_KEY, savedEmail);
        outState.putString(PASSWORD_KEY, savedPassword);
        outState.putString(RePASSWORD_KEY, savedPasswordRe);
        outState.putString(SAVED_USER, savedUser);
    }

    //Check for logging in or not in MySQL DATA base
    private class SignupAsyncTask extends AsyncTask<String, Void, String> {

        private static final String SIGNUP_URL = "http://192.168.1.111/androidProj/signup.php";
        private String emailU;
        private String passwordU;
        private String Uname;
        private String Umajor;

        //Uname, Uemail, Upass, Umajor

        @Override
        protected String doInBackground(String... strings) {
            Uname = strings[0];
            emailU = strings[1];
            passwordU = strings[2];
            Umajor = strings[3];
            try {

                URL url = new URL(SIGNUP_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                //Build the POST DATA TO BE GIVEN TO PHP
                //name,specialization,email,password
                StringBuilder data = new StringBuilder();
                data.append(URLEncoder.encode("name", "UTF-8")).append("=").append(URLEncoder.encode(Uname, "UTF-8"));
                data.append("&").append(URLEncoder.encode("major", "UTF-8")).append("=").append(URLEncoder.encode(Umajor, "UTF-8"));
                data.append("&").append(URLEncoder.encode("email", "UTF-8")).append("=").append(URLEncoder.encode(emailU, "UTF-8"));
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
            if (result != null && result.equals("Signed up successfully")) {//For the other uses of firebase i also want to mark user loged in on firebase here
                signUpToFirebase();
            } else {
                Log.e("post", result + "");
                Toast.makeText(Register.this, "SignUP failed. Please try again", Toast.LENGTH_SHORT).show();
            }
        }

        private void signUpToFirebase() {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(emailU, passwordU)
                    .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign up success
                                Intent intent = new Intent(Register.this, LoginPage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Sign up failed
                                if (task.getException() != null) {
                                    Toast.makeText(Register.this, "Failed to sign up: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Register.this, "Failed to sign up.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }

    }
}