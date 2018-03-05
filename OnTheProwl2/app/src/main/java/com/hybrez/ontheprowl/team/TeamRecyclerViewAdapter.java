package com.hybrez.ontheprowl.team;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hybrez.ontheprowl.R;
import com.hybrez.ontheprowl.model.team.Team;
import com.hybrez.ontheprowl.team.view.TeamFragment;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Team} and makes a call to the
 * specified {@link TeamFragment.OnTeamListInteractionListener}.
 *
 * @author Hamzah Aslam
 */
public class TeamRecyclerViewAdapter extends RecyclerView.Adapter<TeamRecyclerViewAdapter.ViewHolder> {

    /** List of teams in the list view */
    private final List<Team> mTeams;
    private final TeamFragment.OnTeamListInteractionListener mListener;

    /**
     * Construct a new adapter for the team list view
     *
     * @param teams The list of teams in the list view
     * @param listener The callback interface to communicate back with TeamFragment
     */
    public TeamRecyclerViewAdapter(List<Team> teams,
                                   TeamFragment.OnTeamListInteractionListener listener) {
        mTeams = teams;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        /** Update the team and views */
        holder.mItem = mTeams.get(position);
        holder.mNameView.setText(mTeams.get(position).getInfo().getName());
        holder.mNumberView.setText(mTeams.get(position).getNumber());

        /** Delegate to the fragment to handle item selection */
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onTeamItemInteraction(holder.mItem);
                }
            }
        });
    }

    /**
     * Get the number of teams in the list view
     *
     * @return The number of team items
     */
    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    /**
     * A class used to reduce memory usage and recycle
     * the views used in each item in the team list view
     *
     * @author Hamzah Aslam
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /** The view of an item in the list view */
        public final View mView;

        /** The view to contain the team's name */
        public final TextView mNameView;

        /** The view to contain the team's number */
        public final TextView mNumberView;

        /** The team to represent the item in the list view */
        public Team mItem;

        /**
         * Construct a new ViewHolder for the team list view
         *
         * @param view The view of each item in the list view
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.team_name);
            mNumberView = view.findViewById(R.id.team_number);
        }

        /**
         * Get a string representation of the view holder
         *
         * @return A string representation
         */
        @Override
        public String toString() {
            return super.toString() + " '" + mNumberView.getText() + "'";
        }
    }

}
