package com.hybrez.ontheprowl.team.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hybrez.ontheprowl.Constants;
import com.hybrez.ontheprowl.R;
import com.hybrez.ontheprowl.manager.ConfigManager;
import com.hybrez.ontheprowl.manager.SharedMap;
import com.hybrez.ontheprowl.manager.TheBlueAllianceService;
import com.hybrez.ontheprowl.model.team.Team;
import com.hybrez.ontheprowl.team.TeamRecyclerViewAdapter;
import com.hybrez.ontheprowl.util.io.IOUtils;
import com.hybrez.ontheprowl.util.io.JSONUtil;

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
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnTeamListInteractionListener}
 * interface.
 *
 * @author Hamzah Aslam
 */
public class TeamFragment extends Fragment implements Callback<List<Team>> {

    /** Callback interface for the list view adapter to communicate */
    private OnTeamListInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TeamFragment() {
    }

    /**
     * Get an instance of a TeamFragment
     *
     * @return A new instance of the fragment
     */
    public static TeamFragment newInstance() {
        TeamFragment fragment = new TeamFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // TODO: get team list from viewmodel
            recyclerView.setAdapter(new TeamRecyclerViewAdapter(new ArrayList<Team>(), mListener));
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTeamListInteractionListener) {
            mListener = (OnTeamListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTeamListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTeamListInteractionListener {

        /** Called when user selects a team in the list view */
        void onTeamItemInteraction(Team team);

    }

    @Override
    public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
        // clear the adapter for new list
        TeamAdapter adapter = (TeamAdapter) mListView.getAdapter();
        adapter.clear();

        // sort the list by number
        List<Team> teams = response.body();
        Collections.sort(teams, new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                Log.e("MainActivity", o1.getNumber() + " " + o2.getNumber());
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
     * Check if teams are stored in data. If not,
     * get the teams from The Blue Alliance.
     */
    private void getTeams() {
        // add teams from a stored file or get from TBA
        if (!addTeamsFromStorage()) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TBA_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            TheBlueAllianceService service = retrofit.create(TheBlueAllianceService.class);
            Call<List<Team>> call = service.listTeams("2017ncgre", Constants.TBA_AUTH_KEY);
            call.enqueue(this);
        }
    }

    /**
     * Add teams from a file if stored
     * on previous use of app
     *
     * @return Whether or not file exists
     */
    private boolean addTeamsFromStorage() {
        String file = SharedMap.getInstance(this)
                .getTeamDataPath(ConfigManager.getEvent(this));
        boolean fileExists = IOUtils.doesFileExist(file);

        // checks whether file exists
        if (fileExists) {
            // parse the JSON in the file
            List<Team> teams = JSONUtil.read(file);

            // add the teams in the file to the adapter
            TeamAdapter adapter = (TeamAdapter) mListView.getAdapter();
            adapter.clear();
            adapter.addAll(teams);
            adapter.notifyDataSetChanged();
        }

        return fileExists;
    }

}
