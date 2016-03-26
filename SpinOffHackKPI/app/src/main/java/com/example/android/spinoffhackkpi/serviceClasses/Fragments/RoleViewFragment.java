package com.example.android.spinoffhackkpi.serviceClasses.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.spinoffhackkpi.R;

public class RoleViewFragment extends Fragment {
    public TextView roomNameTextView;
    public static ArrayAdapter<String> roomsPlayerListAdapter;
    public View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.role_view_fragment, null);
        return rootView;
    }


    View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
