package com.example.android.spinoffhackkpi.serviceClasses.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.spinoffhackkpi.MainActivity;
import com.example.android.spinoffhackkpi.R;

/**
 * Created by Anton on 25.03.2016.
 */
public class LoginFragment extends Fragment{
    View rootView;
    Button loginButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.greeting_layout, null);
        loginButton = (Button) rootView.findViewById(R.id.login_button);
        loginButton.setOnClickListener(loginClickListener);
        return rootView;
    }


    View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText loginEdit = (EditText) rootView.findViewById(R.id.edit_login);
            if(loginEdit.getText().toString() == ""){
                Toast.makeText(getContext(),"Wrong nickname",Toast.LENGTH_LONG);
            } else {
                MainActivity.socket.emit("login", loginEdit.getText().toString());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(loginButton.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    };
}
