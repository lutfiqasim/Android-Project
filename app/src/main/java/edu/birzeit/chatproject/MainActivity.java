package edu.birzeit.chatproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText pass;
    Button signIn;
    Button singUP;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        signIn = findViewById(R.id.login);
        singUP = findViewById(R.id.SignUP);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        singUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailU = email.getText().toString();
                String passU = pass.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(emailU, passU).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, ChatWindow.class);
                            intent.putExtra("User-id", firebaseAuth.getUid());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to signUP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailU = email.getText().toString();
                String passU = pass.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(emailU, passU).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, ChatWindow.class);
                            intent.putExtra("User-id", firebaseAuth.getUid());
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Logged in succesffully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to log in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}