package org.technowolves.ontheprowl.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.technowolves.ontheprowl.Constants;
import org.technowolves.ontheprowl.view.fragment.InfoFragment;
import org.technowolves.ontheprowl.R;
import org.technowolves.ontheprowl.view.fragment.RobotFragment;
import org.technowolves.ontheprowl.model.Team;

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
                    getSupportActionBar().setTitle(mTeam.getName());
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
        mTeam = new Team(intent.getStringExtra(Constants.TEAM_NUMBER),
                intent.getStringExtra(Constants.TEAM_NAME));

        // Create instances of fragments
        mInfoFrag = InfoFragment.newInstance(mTeam.getName(), mTeam.getNumber());
        mRobotFrag = RobotFragment.newInstance(mTeam.getNumber());

        // Update action bar text
        getSupportActionBar().setTitle(mTeam.getName());

        // Add first shown fragment to activity
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, mInfoFrag)
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
