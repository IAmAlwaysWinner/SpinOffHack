package com.example.android.spinoffhackkpi.serviceClasses.Fragments.DeepService;

/**
 * Created by Anton on 26.03.2016.
 */
public class MafiaChoiceItem {
    String playerName;
    int votes;
    boolean box;

    public MafiaChoiceItem(String name, int votes, boolean box){
        this.playerName = name;
        this.votes = votes;
        this.box = box;
    }
}
