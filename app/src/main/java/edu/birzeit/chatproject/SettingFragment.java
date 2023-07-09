package edu.birzeit.chatproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment{

    private TextView language;
    private TextView changePassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        language = rootView.findViewById(R.id.language);
        language.setOnClickListener(v -> Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show());

        changePassword = rootView.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePassword.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}