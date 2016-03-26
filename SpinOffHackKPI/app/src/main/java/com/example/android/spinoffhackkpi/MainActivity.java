package com.example.android.spinoffhackkpi;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.spinoffhackkpi.serviceClasses.Fragments.LoginFragment;
import com.example.android.spinoffhackkpi.serviceClasses.Fragments.RoomsListFragment;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    LoginFragment loginFragment;
    FragmentTransaction ft;
    public static Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            socket = IO.socket("http://10.241.128.31:4000/");
            socket.connect();
            socketInit(socket);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        loginFragment = new LoginFragment();
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentFrame, loginFragment);
        ft.commit();
    }

    private void socketInit(Socket socket) {
        socket.on("login", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String result;
                        try {
                            result = data.getString("data");

                            RoomsListFragment roomsListFragment = new RoomsListFragment();
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.fragmentFrame, roomsListFragment);
                            ft.commit();
                        } catch (JSONException e) {
                            return;
                        }
                    }
                });
            }
        });

        socket.on("update rooms", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObj = new JSONObject("{data:" + args[0] + "}");
                            JSONArray rooms = jsonObj.getJSONArray("data");
                            RoomsListFragment.roomsListAdapter.clear();
                            for (int i = 0; i < rooms.length(); i++) {
                                JSONObject room = rooms.getJSONObject(i);
                                String name = room.getString("name");
                                RoomsListFragment.roomsListAdapter.add(name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        socket.on("room joined", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RoomsListFragment roomsListFragment = new RoomsListFragment();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentFrame, roomsListFragment);
                        ft.commit();
                    }
                });
            }
        });

        socket.on("error", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObj = (JSONObject) args[0];
                            String errorText = jsonObj.getString("data");
                            Toast.makeText(MainActivity.this, errorText, Toast.LENGTH_LONG).show();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
