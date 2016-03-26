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

public class MafiaChoiceFragment extends Fragment {
    public static ArrayAdapter<String> mafiaChoicesListAdapter;
    public View rootView;
    ListView choiceListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.mafia_choice_fragment, null);

        choiceListView = (ListView) rootView.findViewById(R.id.choice_list);
        mafiaChoicesListAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.mafia_choice_item_layout, R.id.choice_list_name);
        choiceListView.setAdapter(mafiaChoicesListAdapter);
        choiceListView.setOnItemClickListener(playerClickListener);
        return rootView;
    }

    AdapterView.OnItemClickListener playerClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MainActivity.socket.emit("mafia vote", ((TextView) (view.findViewById(R.id.choice_list_name))).getText());
        }
    };
}
