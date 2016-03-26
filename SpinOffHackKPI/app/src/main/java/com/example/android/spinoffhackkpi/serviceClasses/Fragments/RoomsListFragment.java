package com.example.android.spinoffhackkpi.serviceClasses.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.spinoffhackkpi.MainActivity;
import com.example.android.spinoffhackkpi.R;

/**
 * Created by Anton on 25.03.2016.
 */
public class RoomsListFragment extends Fragment {
    public static ArrayAdapter<String> roomsListAdapter;


    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.rooms_list_layout, null);
        Button createRoomButton = (Button) rootView.findViewById(R.id.create_room_button);
        createRoomButton.setOnClickListener(onCreateButtonClick);

        ListView roomsListView = (ListView) rootView.findViewById(R.id.rooms_list_view);

        roomsListAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.rooms_list_item_layout, R.id.rooms_list_item);
        MainActivity.socket.emit("get rooms");
        roomsListView.setAdapter(roomsListAdapter);
        roomsListView.setOnItemClickListener(onRoomClick);
        return rootView;
    }

    View.OnClickListener onCreateButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText roomNameEdit = (EditText) rootView.findViewById(R.id.room_name_edit);
            if(roomNameEdit.getText().toString() != "") {
                MainActivity.socket.emit("new room", roomNameEdit.getText().toString());
                roomNameEdit.setText("");
            } else {
                Toast.makeText(getActivity(), "Wrong room name", Toast.LENGTH_LONG).show();
            }
        }
    };

    AdapterView.OnItemClickListener onRoomClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MainActivity.socket.emit("join room", ((TextView)(view.findViewById(R.id.rooms_list_item))).getText());
        }
    };
}
