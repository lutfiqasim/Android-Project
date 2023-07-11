package edu.birzeit.chatproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChangePassword extends AppCompatActivity {

    private EditText password;
    private EditText oldPassword;

    private EditText confirm_password;
    private Button changePassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        // Initialize views
        oldPassword = findViewById(R.id.oldPassword);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        changePassword = findViewById(R.id.changePassword);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the input values
                String currentPassword = oldPassword.getText().toString();
                String newPassword = password.getText().toString();
                String confirmPassword = confirm_password.getText().toString();

                // Validate the inputs
                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    showToast("Please fill in all fields.");
                    return;
                }

                // Check if the new password and confirmation password match
                if (!newPassword.equals(confirmPassword)) {
                    showToast("New password and confirmation password do not match.");
                    return;
                }

//                 Check if the new password and old password match
                if (newPassword.equals(oldPassword)) {
                    showToast("New password and old password match.");
                    return;
                }

                // Perform password change logic
                if (performPasswordChange(currentPassword, newPassword)) {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    firebaseUser.updatePassword(newPassword)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Password updated successfully
                                    Toast.makeText(getApplicationContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Failed to update password
                                    Toast.makeText(getApplicationContext(), "Failed to update password", Toast.LENGTH_SHORT).show();
                                }
                            });

                    Intent intent = new Intent(ChangePassword.this, LoginPage.class);
                    startActivity(intent);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loginStatus", false);
                    editor.putString("email", "");
                    editor.putString("password", "");
                    editor.apply();
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                } else {
                    showToast("Failed to change password. Please try again.");
                }

            }
        });
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {

        private String url;
        private String data;

        public HttpRequestTask(String url, String data) {
            this.url = url;
            this.data = data;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // Create an HTTP connection
                URL url = new URL(this.url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Write the data to the connection
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(data.getBytes());
                outputStream.flush();
                outputStream.close();

                // Get the response from the PHP script
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();

                    return response.toString();
                } else {
                    return "Error: " + responseCode;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Display a Toast message based on the PHP script response
            Toast.makeText(ChangePassword.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    private void showToast(String message) {
        Toast.makeText(ChangePassword.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean performPasswordChange(String currentPassword, String newPassword) {
        sharedPreferences = getSharedPreferences("LoggedIn", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        // Create an HTTP request to the PHP script
        String url = "http://192.168.1.111/androidProj/change_password.php";
        String data = "current_password=" + currentPassword + "&new_password=" + newPassword
                + "&confirm_password=" + currentPassword + "&email=" + email;
        HttpRequestTask httpRequestTask = new HttpRequestTask(url, data);
        httpRequestTask.execute();

        return !newPassword.isEmpty();
    }
}