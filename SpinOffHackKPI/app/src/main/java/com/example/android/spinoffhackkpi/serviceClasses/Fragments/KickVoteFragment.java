package com.example.android.spinoffhackkpi.serviceClasses.Fragments;

import android.app.Fragment;
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

public class KickVoteFragment extends Fragment {
    public static ArrayAdapter<String> kickChoicesListAdapter;
    public View rootView;
    ListView choiceListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.kick_choice_layout,null);

        choiceListView = (ListView) rootView.findViewById(R.id.arrest_choice_list);
        kickChoicesListAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.kick_choice_item_layout, R.id.arrest_choice_list_name);
        choiceListView.setAdapter(kickChoicesListAdapter);
        choiceListView.setOnItemClickListener(arrestClickListener);
        return rootView;
    }

    AdapterView.OnItemClickListener arrestClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MainActivity.socket.emit("arrest vote", ((TextView) (view.findViewById(R.id.arrest_choice_list_name))).getText());
        }
    };
}
