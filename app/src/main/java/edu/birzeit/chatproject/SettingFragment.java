package edu.birzeit.chatproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment {

    private TextView language;
    private TextView changePassword;
    private TextView aboutApplication;
    private TextView userName;
    private TextView email;
    private TextView logOut;

    private TextView helpSupport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //reading the XML file that describes a layout (or GUI element) and to create the actual
        // objects that correspond to it, and thus make the object visible within an Android app.
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        helpSupport = rootView.findViewById(R.id.helpSupport);
        helpSupport.setOnClickListener(this::sendEmail);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoggedIn", MODE_PRIVATE);

        // Retrieve values from SharedPreferences
        userName=rootView.findViewById(R.id.userName);
        userName.setText(sharedPreferences.getString("name","user name"));
        email=rootView.findViewById(R.id.email);
        email.setText(sharedPreferences.getString("email","example@gmail.com"));

        language = rootView.findViewById(R.id.language);
        language.setOnClickListener(v -> Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show());

        //re_direct to change_password Activity
        changePassword = rootView.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePassword.class);
            startActivity(intent);
        });

        //re_direct to about_application Activity
        aboutApplication = rootView.findViewById(R.id.aboutApplication);
        aboutApplication.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutApplication.class);
            startActivity(intent);
        });


        logOut = rootView.findViewById(R.id.logOut);
        logOut.setOnClickListener(v -> {
            // Start the LoginPage activity
            Intent intent = new Intent(getActivity(), LoginPage.class);
            startActivity(intent);

            // Clear stored login information from SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("loginStatus", false);
            editor.putString("email", "");
            editor.putString("password", "");
            editor.apply();

            // Sign out the user from Firebase Authentication
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
        });

        return rootView;
    }

    public void sendEmail(View view) {
        // Create an Intent with the ACTION_SENDTO action and the email address as the data
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:basel76ziyad24@gmail.com"));

        // Set the subject of the email
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help & Support");

        try {
            // Start the email application or show a chooser if multiple email applications are available
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        } catch (Exception e) {
            // Handle the case when no email application is available
            Toast.makeText(getActivity(), "No email application found.", Toast.LENGTH_SHORT).show();
        }
    }
}