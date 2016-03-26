package com.example.android.spinoffhackkpi.serviceClasses.Fragments.DeepService;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MafiaChoiceAdapter extends BaseAdapter{
    Context context;
    ArrayList<MafiaChoiceItem> mafiaChoiceItems;

    public MafiaChoiceAdapter(Context context, ArrayList<MafiaChoiceItem> mafiaChoiceItems){
        this.context = context;
        this.mafiaChoiceItems = mafiaChoiceItems;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

        }
    };
}
