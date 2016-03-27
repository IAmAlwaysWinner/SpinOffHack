package com.example.android.spinoffhackkpi.serviceClasses.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.android.spinoffhackkpi.MainActivity;
import com.example.android.spinoffhackkpi.R;

public class CherifAcceptFragment extends Fragment {
    public static View rootView;
    Button acceptButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.cherif_accept_fragment, null);
        acceptButton = (Button) rootView.findViewById(R.id.cherif_accept_button);
        acceptButton.setOnClickListener(acceptButtonClickListener);
        return rootView;
    }

    View.OnClickListener acceptButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity.socket.emit("sheriff accept");
        }
    };
}
