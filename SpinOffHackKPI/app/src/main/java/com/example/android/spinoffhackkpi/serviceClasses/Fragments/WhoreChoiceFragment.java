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

public class WhoreChoiceFragment extends Fragment {
    public static ArrayAdapter<String> whoreChoicesListAdapter;
    public View rootView;
    ListView choiceListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.whore_choice_fragment, null);

        choiceListView = (ListView) rootView.findViewById(R.id.whore_choice_list);
        whoreChoicesListAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.whore_choice_item_layout, R.id.whore_choice_list_name);
        choiceListView.setAdapter(whoreChoicesListAdapter);
        choiceListView.setOnItemClickListener(whoreClickListener);
        return rootView;
    }

    AdapterView.OnItemClickListener whoreClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MainActivity.socket.emit("quean vote", ((TextView) (view.findViewById(R.id.whore_choice_list_name))).getText());
        }
    };
}
