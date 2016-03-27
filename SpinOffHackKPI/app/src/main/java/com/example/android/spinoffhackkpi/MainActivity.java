package com.example.android.spinoffhackkpi;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.spinoffhackkpi.serviceClasses.Fragments.CherifAcceptFragment;
import com.example.android.spinoffhackkpi.serviceClasses.Fragments.CherifChoiceFragment;
import com.example.android.spinoffhackkpi.serviceClasses.Fragments.DoctorChoiceFragment;
import com.example.android.spinoffhackkpi.serviceClasses.Fragments.KickVoteFragment;
import com.example.android.spinoffhackkpi.serviceClasses.Fragments.LoginFragment;
import com.example.android.spinoffhackkpi.serviceClasses.Fragments.MafiaChoiceFragment;
import com.example.android.spinoffhackkpi.serviceClasses.Fragments.RoleViewFragment;
import com.example.android.spinoffhackkpi.serviceClasses.Fragments.RoomFragment;
import com.example.android.spinoffhackkpi.serviceClasses.Fragments.RoomsListFragment;
import com.example.android.spinoffhackkpi.serviceClasses.Fragments.SleepFragment;
import com.example.android.spinoffhackkpi.serviceClasses.Fragments.WhoreChoiceFragment;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                        } catch (Throwable e) {
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
                    RoomFragment roomFragment = new RoomFragment();
                    ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentFrame, roomFragment);
                    ft.commit();
                    String roomName;
                    roomName = (String) args[0];
                    getFragmentManager().executePendingTransactions();
                    ((TextView) roomFragment.rootView.findViewById(R.id.room_name_text_view)).setText(roomName);
                }
            });
            }
        });

        socket.on("update players", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    try {
                        JSONObject jsonObj = new JSONObject("{data:" + args[0] + "}");
                        JSONArray players = jsonObj.getJSONArray("data");
                        RoomFragment.roomsPlayerListAdapter.clear();
                        for (int i = 0; i < players.length(); i++) {
                            JSONObject player = players.getJSONObject(i);
                            String nickname = player.getString("nickname");
                            RoomFragment.roomsPlayerListAdapter.add(nickname);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    }
                });
            }
        });


        socket.on("start game", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                JSONObject data = (JSONObject) args[0];
                String role;
                try {
                    RoleViewFragment roleViewFragment = new RoleViewFragment();
                    ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentFrame, roleViewFragment);
                    ft.commit();
                    getFragmentManager().executePendingTransactions();
                    role = data.getString("role");
                    ((TextView) roleViewFragment.rootView.findViewById(R.id.role_is)).setText(role);
                } catch (JSONException e) {
                    return;
                }
                }
            });
            }
        });

        socket.on("err", new Emitter.Listener() {
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

        socket.on("mafia begin", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MafiaChoiceFragment mafiaChoiceFragment = new MafiaChoiceFragment();
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.fragmentFrame, mafiaChoiceFragment);
                            ft.commit();
                            getFragmentManager().executePendingTransactions();
                            JSONObject jsonObj = new JSONObject("{data:" + args[0] + "}");
                            JSONArray noMafiaPlayers = jsonObj.getJSONArray("data");
                            MafiaChoiceFragment.mafiaChoicesListAdapter.clear();
                            for (int i = 0; i < noMafiaPlayers.length(); i++) {
                                JSONObject noMafiaPlayer = noMafiaPlayers.getJSONObject(i);
                                String name = noMafiaPlayer.getString("nickname");
                                MafiaChoiceFragment.mafiaChoicesListAdapter.add(name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        socket.on("mafia success", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SleepFragment sleepFragment = new SleepFragment();
                    ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentFrame, sleepFragment);
                    ft.commit();
                    getFragmentManager().executePendingTransactions();
                }
            });
            }
        });

        socket.on("doctor begin", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DoctorChoiceFragment doctorChoiceFragment = new DoctorChoiceFragment();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentFrame, doctorChoiceFragment);
                        ft.commit();
                        JSONObject jsonObj = new JSONObject("{data:" + args[0] + "}");
                        JSONArray playersToHeal = jsonObj.getJSONArray("data");
                        getFragmentManager().executePendingTransactions();
                        doctorChoiceFragment.doctorChoicesListAdapter.clear();
                        for (int i = 0; i < playersToHeal.length(); i++) {
                            JSONObject playerToHeal = playersToHeal.getJSONObject(i);
                            String name = playerToHeal.getString("nickname");
                            doctorChoiceFragment.doctorChoicesListAdapter.add(name);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            }
        });

        socket.on("doctor success", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SleepFragment sleepFragment = new SleepFragment();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentFrame, sleepFragment);
                        ft.commit();
                        getFragmentManager().executePendingTransactions();
                    }
                });
            }
        });

        socket.on("sheriff begin", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            CherifChoiceFragment cherifChoiceFragment = new CherifChoiceFragment();
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.fragmentFrame, cherifChoiceFragment);
                            ft.commit();
                            JSONObject jsonObj = new JSONObject("{data:" + args[0] + "}");
                            JSONArray playersToInspect = jsonObj.getJSONArray("data");
                            getFragmentManager().executePendingTransactions();
                            cherifChoiceFragment.cherifChoiceListAdapter.clear();
                            for (int i = 0; i < playersToInspect.length(); i++) {
                                JSONObject playerToInspect = playersToInspect.getJSONObject(i);
                                String name = playerToInspect.getString("nickname");
                                cherifChoiceFragment.cherifChoiceListAdapter.add(name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        socket.on("sheriff result", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CherifAcceptFragment cherifAcceptFragment = new CherifAcceptFragment();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentFrame, cherifAcceptFragment);
                        ft.commit();
                        String resultText = (String) args[0];
                        getFragmentManager().executePendingTransactions();
                        ((TextView)(cherifAcceptFragment.rootView.findViewById(R.id.cherif_answer_text_view))).setText(resultText);
                    }
                });
            }
        });

        socket.on("sheriff success", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SleepFragment sleepFragment = new SleepFragment();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentFrame, sleepFragment);
                        ft.commit();
                        getFragmentManager().executePendingTransactions();
                    }
                });
            }
        });

        socket.on("quean begin", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        WhoreChoiceFragment whoreChoiceFragment = new WhoreChoiceFragment();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentFrame, whoreChoiceFragment);
                        ft.commit();
                        JSONObject jsonObj = new JSONObject("{data:" + args[0] + "}");
                        JSONArray playersToPutAlibiOn = jsonObj.getJSONArray("data");
                        getFragmentManager().executePendingTransactions();
                        whoreChoiceFragment.whoreChoicesListAdapter.clear();
                        for (int i = 0; i < playersToPutAlibiOn.length(); i++) {
                            JSONObject playerToPutAlibiOn = playersToPutAlibiOn.getJSONObject(i);
                            String name = playerToPutAlibiOn.getString("nickname");
                            whoreChoiceFragment.whoreChoicesListAdapter.add(name);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            }
        });


        socket.on("quean success", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SleepFragment sleepFragment = new SleepFragment();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentFrame, sleepFragment);
                        ft.commit();
                        getFragmentManager().executePendingTransactions();
                    }
                });
            }
        });

        socket.on("night begin", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SleepFragment sleepFragment = new SleepFragment();
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentFrame, sleepFragment);
                        ft.commit();
                        getFragmentManager().executePendingTransactions();
                    }
                });
            }
        });

        socket.on("arrest begin", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            KickVoteFragment kickVoteFragment = new KickVoteFragment();
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.fragmentFrame, kickVoteFragment);
                            ft.commit();
                            getFragmentManager().executePendingTransactions();
                            JSONObject jsonObj = new JSONObject("{data:" + args[0] + "}");
                            JSONArray players = jsonObj.getJSONArray("data");
                            KickVoteFragment.kickChoicesListAdapter.clear();
                            for (int i = 0; i < players.length(); i++) {
                                JSONObject player = players.getJSONObject(i);
                                String name = player.getString("nickname");
                                KickVoteFragment.kickChoicesListAdapter.add(name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
