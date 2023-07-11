package edu.birzeit.chatproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private LinearLayout cs;
    private LinearLayout ce;
    private LinearLayout csy;
    private LinearLayout ee;
    private ImageView chats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        chats = rootView.findViewById(R.id.chats);
        chats.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChatWindow.class);
            startActivity(intent);
        });
        cs = rootView.findViewById(R.id.cs);
        ce = rootView.findViewById(R.id.ce);
        ee = rootView.findViewById(R.id.ee);
        csy = rootView.findViewById(R.id.css);

        // Set click listeners for each LinearLayout
        cs.setOnClickListener(this);
        ce.setOnClickListener(this);
        ee.setOnClickListener(this);
        csy.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        String major = ""; // Default value for major

        // Determine which LinearLayout was clicked and set the major accordingly
        switch (v.getId()) {
            case R.id.cs:
                major = "Computer Science";
                break;
            case R.id.ce:
                major = "Computer Engineering";
                break;
            case R.id.ee:
                major = "Electrical Engineering";
                break;
            case R.id.css:
                major = "Cybersecurity";
                break;
            default:
                // Do nothing or handle the default case as needed
                break;
        }

        // Create an Intent to start the CourseActivity
        Intent intent = new Intent(getActivity(), CourseActivity.class);
        intent.putExtra("major", major);
        startActivity(intent);
    }
}
