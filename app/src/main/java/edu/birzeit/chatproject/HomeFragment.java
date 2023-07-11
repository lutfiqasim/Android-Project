package edu.birzeit.chatproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private ImageView chats;

    /*set a click listener for a chats button to launch the ChatWindow activity.*/
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
        return rootView;
    }
}