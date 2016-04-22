package org.technowolves.ontheprowl.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.technowolves.ontheprowl.ActivityInteractionListener;
import org.technowolves.ontheprowl.FragmentInteractionListener;
import org.technowolves.ontheprowl.R;
import org.technowolves.ontheprowl.model.Team;
import org.technowolves.ontheprowl.view.fragment.MainFragment;
import org.technowolves.ontheprowl.view.fragment.RobotFragment;
import org.technowolves.ontheprowl.view.fragment.TeamFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentInteractionListener,
        MainFragment.MainFragInteractionListener, TeamFragment.TeamFragInteractionListener,
        RobotFragment.RobotFragInteractionListener {

    public static final String PREFS_OPEN_APP = "APP_OPEN_TIME";
    public static final String PREFS_INIT_STATE = "MAIN_FRAG_INIT";

    private ActivityInteractionListener mListener;
    private SharedPreferences mPrefs;
    private Team mTeam;

    private ViewPager viewPager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!mPrefs.getBoolean(PREFS_OPEN_APP, false)) {
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
            mPrefs.edit().putBoolean(PREFS_OPEN_APP, true).apply();
        }

        setInitState(false);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);

                Fragment fragment = ((ViewPagerAdapter) viewPager.getAdapter())
                        .getItem(tab.getPosition());
                if (fragment instanceof ActivityInteractionListener)
                    mListener = (ActivityInteractionListener) fragment;

                if (fragment instanceof MainFragment) {
                    onChangeFabIcon(R.drawable.ic_cloud_download_white_36dp);
                } else if (fragment instanceof TeamFragment) {
                    onChangeFabIcon(R.drawable.ic_done_all_white_36dp);
                    ((TeamFragment) fragment).setupData();
                } else if (fragment instanceof RobotFragment) {
                    onChangeFabIcon(R.drawable.ic_done_all_white_36dp);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);

                Fragment fragment = ((ViewPagerAdapter) viewPager.getAdapter())
                        .getItem(tab.getPosition());
                if (fragment instanceof ActivityInteractionListener)
                    mListener = (ActivityInteractionListener) fragment;
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onButtonPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // update list after import
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_export:
                Intent exportIntent = new Intent(this, ExportActivity.class);
                //exportIntent.putExtra("", false);
                startActivity(exportIntent);
                return true;
            case R.id.action_import:
                Intent importIntent = new Intent(this, ExportActivity.class);
                startActivityForResult(importIntent, 1);
                return true;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_help:
                Intent helpIntent = new Intent(this, IntroActivity.class);
                startActivity(helpIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onListItemClick(Team team) {
        mTeam = team;
    }

    @Override
    public Team getSelectedTeam() {
        return mTeam;
    }

    @Override
    public void setInitState(boolean state) {
        if (mPrefs != null)
            mPrefs.edit().putBoolean(PREFS_INIT_STATE, state).commit();
    }

    @Override
    public boolean getInitState() {
        if (mPrefs != null)
            return mPrefs.getBoolean(PREFS_INIT_STATE, false);

        return false;
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MainFragment.newInstance(), getString(R.string.tab_one));
        adapter.addFragment(TeamFragment.newInstance(), getString(R.string.tab_two));
        adapter.addFragment(RobotFragment.newInstance(), getString(R.string.tab_three));
        adapter.instantiateItem(viewPager, 2);
        viewPager.setAdapter(adapter);
    }

    private void onChangeFabIcon(int resource) {
        if (fab != null)
            fab.setImageDrawable(getApplicationContext()
                    .getResources()
                    .getDrawable(resource));
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        private String makeFragmentName(int viewId, long id) {
            return "android:switcher:" + viewId + ":" + id;
        }

    }

}
