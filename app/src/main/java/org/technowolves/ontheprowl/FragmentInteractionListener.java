package org.technowolves.ontheprowl;

import android.view.View;

import org.technowolves.ontheprowl.model.Team;

public interface FragmentInteractionListener {

    void onCreateSnackbar(View view, String msg);
    Team getSelectedTeam();

}
