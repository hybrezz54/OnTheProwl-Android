package com.hybrez.ontheprowl.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.hybrez.ontheprowl.Constants;
import org.technowolves.ontheprowl.R;
import com.hybrez.ontheprowl.SharedMap;
import com.hybrez.ontheprowl.TeamAdapter;
import com.hybrez.ontheprowl.TheBlueAllianceService;
import com.hybrez.ontheprowl.model.team.Team;
import com.hybrez.ontheprowl.util.IOUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The MainActivity of the application
 * which displays the teams
 */
public class MainActivity extends AppCompatActivity implements Callback<List<Team>> {

    /** The view for the list of teams */
    private ListView mListView;

    /** Swipe to Refresh View */
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create adapter for list of teams
        TeamAdapter adapter = new TeamAdapter(this, new ArrayList<Team>());

        // find views in layout
        mListView = (ListView) findViewById(R.id.team_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        // TODO: Just for debugging purposes
        /*adapter.add(new Team("5518", "Techno Wolves"));
        adapter.notifyDataSetChanged();
        Log.e("MainActivity", "Over here!");*/

        // update listview
        mListView.setAdapter(adapter);

        // set list view on click listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get the selected team object
                Team team = (Team) mListView.getAdapter().getItem(position);

                // Launch TeamActivity with team info
                Intent intent = new Intent(MainActivity.this, TeamActivity.class);
                intent.putExtra(Constants.TEAM_NAME, team.getInfo().getName());
                intent.putExtra(Constants.TEAM_NUMBER, team.getNumber());
                startActivity(intent);
            }
        });

        // set on swipe-to-refresh listener
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: Update listview with correct teams
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        // add teams from a stored file or get from TBA
        if (!addTeamsFromStorage()) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TBA_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            TheBlueAllianceService tbaService = retrofit.create(TheBlueAllianceService.class);
            // TODO: Add event key
            Call<List<Team>> call = tbaService.listTeams("2017ncgre", Constants.TBA_AUTH_KEY);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
        // clear the adapter for new list
        TeamAdapter adapter = (TeamAdapter) mListView.getAdapter();
        adapter.clear();

        Log.e("Main Acitivty", response.toString());

        // compare lists to see if equal
        List<Team> teams = (List) response.body();
        Collections.sort(teams, new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                Integer team1 = Integer.parseInt(o1.getNumber());
                Integer team2 = Integer.parseInt(o2.getNumber());
                return team1.compareTo(team2);
            }
        });

        // add teams to list adapter
        adapter.addAll(teams);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call<List<Team>> call, Throwable t) {
        // Show a failure message to the user
        Snackbar.make(mListView, getString(R.string.download_failure),
                Snackbar.LENGTH_LONG).show();
    }

    /**
     * Add teams from a file if stored
     * on previous use of app
     *
     * @return Whether or not file exists
     */
    private boolean addTeamsFromStorage() {
        // TODO Parse files more efficiently than appending into string
        boolean fileExists = IOUtils.doesFileExist(this,
                SharedMap.TBA_DATA_DIR, getFileName());

        // checks whether file exists
        if (fileExists) {
            // parse the JSON in the file
            Gson gson = new Gson();
            String json = IOUtils.read(this,
                    SharedMap.TBA_DATA_DIR, getFileName());
            List<Team> teams = gson.fromJson(json, new TypeToken<List<Team>>() {}.getType());

            // add the teams in the file to the adapter
            TeamAdapter adapter = (TeamAdapter) mListView.getAdapter();
            adapter.clear();
            adapter.addAll(teams);
            adapter.notifyDataSetChanged();
        }

        return fileExists;
    }

    /**
     * Get the filename of the data file
     * based on the chosen event
     *
     * @return The filename
     */
    private String getFileName() {
        SharedPreferences manager = PreferenceManager.getDefaultSharedPreferences(this);

        // get the file name
        String filename = ""; // manager.getString(SettingsActivity.EVENT_KEY, "2017ncral");
        filename += "_teams.json";

        return filename;
    }

}