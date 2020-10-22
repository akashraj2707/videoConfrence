package com.example.videochatbasic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.videochatbasic.Fragments.HistoryFragment;
import com.example.videochatbasic.Fragments.ProfileFragment;
import com.example.videochatbasic.Fragments.SearchUserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashBoardActivty extends AppCompatActivity {
    EditText joinCode;
    Button joinRoom,createRoom;
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_activty);
        joinCode =findViewById(R.id.joinCode);
        joinRoom=findViewById(R.id.buttonJoin);
        createRoom=findViewById(R.id.buttonCreate);
        URL serverURL;


        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId())
                {   case R.id.nav_home:

                    case R.id.nav_profile:
                        selectorFragment=new ProfileFragment();
                        break;

                    case R.id.nav_search:
                        selectorFragment=new SearchUserFragment();
                        break;

                    case R.id.nav_history:
                        selectorFragment=new HistoryFragment();
                        break;
                }
                if(selectorFragment!=null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
                return true;
            }

        });

        try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions
                    = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }
        joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions options
                        = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(joinCode.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();

                JitsiMeetActivity.launch(DashBoardActivty.this,options);
            }
        });




    }
}