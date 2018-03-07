package com.hybrez.ontheprowl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hybrez.ontheprowl.config.view.ConfigActivity;
import com.hybrez.ontheprowl.manager.ConfigManager;
import com.hybrez.ontheprowl.model.team.Team;
import com.hybrez.ontheprowl.team.view.TeamActivity;
import com.hybrez.ontheprowl.team.view.TeamFragment;

/**
 * The MainActivity of the application
 * which displays the teams
 *
 * @author Hamzah Aslam
 */
public class MainActivity extends AppCompatActivity implements TeamFragment.OnTeamListInteractionListener {

    /** Swipe to Refresh View */
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check if is initial launch of app
        if (ConfigManager.isInitialLaunch(this)) {
            ConfigManager.setNotInitialLaunch(this);
            Intent intent = new Intent(this, ConfigActivity.class);
            startActivity(intent);
        }

        // TODO: get instances of two viewmodels

        // TODO: inflate the fragment
        TeamFragment fragment = TeamFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, fragment)
                .commit();

        // find views in layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: update fragment with viewmodel data
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        // TODO: update fragment with viewmodel data
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: Handle menu item selection
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, ConfigActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when user selects a team in the list view
     *
     * @param team The selected team
     */
    @Override
    public void onTeamItemInteraction(Team team) {
        Intent intent = new Intent(this, TeamActivity.class);
        intent.putExtra(Constants.TEAM_NAME, team.getInfo().getName());
        intent.putExtra(Constants.TEAM_NUMBER, team.getNumber());
        startActivity(intent);
    }
}
