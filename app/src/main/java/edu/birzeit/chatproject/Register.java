package edu.birzeit.chatproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //        singUP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {

                /*
                * String emailU = email.getText().toString();
                String passU = pass.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(emailU, passU).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginPage.this, ChatWindow.class);
                            intent.putExtra("User-id", firebaseAuth.getUid());
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginPage.this, "Failed to signUP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                *
                * */
//            }
//        });
    }
}