package edu.birzeit.chatproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private LinearLayout chat;
    private LinearLayout schedule;
    private TextView changePassword;
    private TextView userName;

    private TextView major;
    private TextView email;

    /* retrieve and display user information from SharedPreferences
     and set click listeners for buttons.*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //reading the XML file that describes a layout (or GUI element) and to create the actual
        // objects that correspond to it, and thus make the object visible within an Android app.
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoggedIn", MODE_PRIVATE);

        userName=rootView.findViewById(R.id.userName);
        userName.setText(sharedPreferences.getString("name","user name"));

        email=rootView.findViewById(R.id.email);
        email.setText(sharedPreferences.getString("email","example@gmail.com"));

        major=rootView.findViewById(R.id.major);
        major.setText(sharedPreferences.getString("major","CS"));

        chat=rootView.findViewById(R.id.chat);
        chat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChatWindow.class);
            startActivity(intent);
        });

        schedule=rootView.findViewById(R.id.schedule);
        schedule.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ScheduleWindow.class);
            startActivity(intent);
        });

        changePassword=rootView.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePassword.class);
            startActivity(intent);
        });
        // Inflate the layout for this fragment
        return rootView;
    }
}