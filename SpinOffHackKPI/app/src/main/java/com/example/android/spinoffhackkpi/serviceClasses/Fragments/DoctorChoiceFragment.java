package com.example.android.spinoffhackkpi.serviceClasses.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.spinoffhackkpi.MainActivity;
import com.example.android.spinoffhackkpi.R;

/**
 * Created by Anton on 26.03.2016.
 */
public class DoctorChoiceFragment extends Fragment {
    public static ArrayAdapter<String> doctorChoicesListAdapter;
    public View rootView;
    ListView choiceListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.mafia_choice_fragment, null);

        choiceListView = (ListView) rootView.findViewById(R.id.choice_list);
        doctorChoicesListAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.doctor_choice_item_layout, R.id.choice_list_name);
        choiceListView.setAdapter(doctorChoicesListAdapter);
        choiceListView.setOnItemClickListener(doctorHealItemClick);
        return rootView;
    }

    AdapterView.OnItemClickListener doctorHealItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MainActivity.socket.emit("doctor vote", ((TextView) (view.findViewById(R.id.choice_list_name))).getText());
        }
    };
}
