package org.technowolves.ontheprowl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.technowolves.ontheprowl.model.team.Team;

import java.util.ArrayList;

/**
 * The adapter for the ListView of Teams
 */
public class TeamAdapter extends ArrayAdapter<Team> {

    /** init variables for selected item */
    private int selectedIndex;

    /**
     * Passes the context and array so Android
     * can inflate the views in the adapter
     *
     * @param context The context of the listview
     * @param teams The array of teams to inflate in the adapter
     */
    public TeamAdapter(Context context, ArrayList<Team> teams) {
        super(context, 0, teams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Team team = getItem(position);

        // get the view of the list item
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.team_item, parent, false);

        // find the views in the view
        TextView txtNumber = (TextView) convertView.findViewById(R.id.team_number);
        TextView txtName = (TextView) convertView.findViewById(R.id.team_name);

        // update textviews appropriately
        txtNumber.setText(team.getNumber());
        txtName.setText(team.getName());

        return convertView;
    }

    /**
     * Set index of list adapter and
     * update the adapter accordingly
     *
     * @param index Index to set at
     */
    public void setSelectedIndex(int index) {
        selectedIndex = index;
        notifyDataSetChanged();
    }

    /**
     * Get the selected index of the
     * list adadpter
     *
     * @return The selected index
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

}
