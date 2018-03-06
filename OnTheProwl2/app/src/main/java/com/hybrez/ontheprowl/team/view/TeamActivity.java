package com.hybrez.ontheprowl.team.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hybrez.ontheprowl.Constants;
import com.hybrez.ontheprowl.R;
import com.hybrez.ontheprowl.model.team.Team;

public class TeamActivity extends AppCompatActivity implements
        InfoFragment.OnFragmentInteractionListener, RobotFragment.OnFragmentInteractionListener {

    /** Team object */
    private Team mTeam;

    /** InfoFragment instance */
    private InfoFragment mInfoFrag;

    /** RobotFragment instance */
    private RobotFragment mRobotFrag;

    /** Bottom Navigation View */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Begin fragment transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            // TODO: Add null checking on fragments

            switch (item.getItemId()) {
                case R.id.navigation_info:
                    // Replace frame with InfoFragment and update title
                    getSupportActionBar().setTitle(mTeam.getInfo().getName());
                    ft.replace(R.id.content, mInfoFrag)
                            .commit();
                    return true;
                case R.id.navigation_robot:
                    // Replace frame with RobotFragment and update title
                    getSupportActionBar().setTitle(mTeam.getNumber() + "'s Robot");
                    ft.replace(R.id.content, mRobotFrag)
                            .commit();
                    return true;
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        // Get team number and name
        Intent intent = getIntent();
        mTeam = new Team(intent.getStringExtra(Constants.TEAM_NUMBER));

        // Create instances of fragments
        mInfoFrag = InfoFragment.newInstance(mTeam.getInfo().getName(), mTeam.getNumber());
        mRobotFrag = RobotFragment.newInstance(mTeam.getNumber());

        // Update action bar text
        getSupportActionBar().setTitle(mTeam.getInfo().getName());

        // Add first shown fragment to activity
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_team, mInfoFrag)
                .commit();

        // Add listener to navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onInfoInteraction(Uri uri) {
        // TODO: Fix and implement method
    }

    @Override
    public void onRobotInteraction(Uri uri) {
        // TODO: Fix and implement method
    }

}
