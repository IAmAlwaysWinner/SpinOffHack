package com.example.android.spinoffhackkpi.serviceClasses.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.spinoffhackkpi.MainActivity;
import com.example.android.spinoffhackkpi.R;

/**
 * Created by Anton on 25.03.2016.
 */
public class LoginFragment extends Fragment {
    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.greeting_layout, null);
        Button loginButton = (Button) rootView.findViewById(R.id.login_button);
        loginButton.setOnClickListener(loginClickListener);
        return rootView;
    }


    View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText loginEdit = (EditText) rootView.findViewById(R.id.edit_login);
            MainActivity.socket.emit("login", loginEdit.getText().toString());
        }
    };
}
