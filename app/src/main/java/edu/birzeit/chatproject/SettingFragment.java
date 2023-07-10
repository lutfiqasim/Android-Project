package edu.birzeit.chatproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private TextView logOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        language = rootView.findViewById(R.id.language);
        language.setOnClickListener(v -> Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show());

        changePassword = rootView.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePassword.class);
            startActivity(intent);
        });

        aboutApplication = rootView.findViewById(R.id.aboutApplication);
        aboutApplication.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutApplication.class);
            startActivity(intent);
        });

        logOut = rootView.findViewById(R.id.logOut);
        logOut.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginPage.class);
            startActivity(intent);

            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoggedIn", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("loginStatus", false);
            editor.putString("email", "");
            editor.putString("password", "");
            editor.apply();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
        });

        return rootView;
    }
}