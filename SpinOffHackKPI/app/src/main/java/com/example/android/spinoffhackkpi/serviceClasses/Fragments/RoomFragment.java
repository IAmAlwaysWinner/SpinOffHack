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

public class RoomFragment extends Fragment {
    public TextView roomNameTextView;
    public static ArrayAdapter<String> roomsPlayerListAdapter;
    public View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.room_fragment_layout, null);

        roomNameTextView = (TextView) rootView.findViewById(R.id.room_name_text_view);
        ListView playersListView = (ListView) rootView.findViewById(R.id.players_list_view);
        roomsPlayerListAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.room_player_item, R.id.room_players_item);
        playersListView.setAdapter(roomsPlayerListAdapter);
        return rootView;
    }

}
