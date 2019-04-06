package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewTeamsActivity extends AppCompatActivity implements Button.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private ListView mLv;
    private TeamListAdapter mAdapter;
    private FloatingActionButton mNewTeamButton;
    private TeamViewModel mTeamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teams);

        mAdapter = new TeamListAdapter(ViewTeamsActivity.this);
        mLv = findViewById(R.id.view_teams_teams_lv);
        mLv.setAdapter(mAdapter);

        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        mTeamViewModel.selectAllLive().observe(this, aTeams -> {
            mAdapter.setTeams(aTeams);
            mAdapter.notifyDataSetChanged();
        });

        Toolbar myToolbar = findViewById(R.id.view_teams_tb);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }

        mNewTeamButton = findViewById(R.id.view_teams_new_team);
        mNewTeamButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_teams_options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.view_teams_options_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_teams_new_team:
                Team myTeam = new Team();
                mTeamViewModel.insert(myTeam);
                editTeam(myTeam);
                break;
            default:
                break;
        }
    }

    class TeamListAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private List<Team> mTeams = new ArrayList<>();
        private List<Team> mDisplayedTeams;

        TeamListAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
            mDisplayedTeams = mTeams;
        }

        public void setTeams(List<Team> aTeams) {
            this.mTeams = aTeams;
            mDisplayedTeams = mTeams;
        }

        @Override
        public int getCount() {
            return mDisplayedTeams.size();
        }

        @Override
        public Object getItem(int i) {
            return mTeams.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = mLayoutInflater.inflate(R.layout.view_teams_item, parent, false);
            TextView nameTV = convertView.findViewById(R.id.view_teams_name_tv);
            Team myTeam = mDisplayedTeams.get(position);
            nameTV.setText(myTeam.getName());
            Log.d(TAG, "sfgesgsgsjglsfghsh");

            convertView.setOnClickListener(v -> editTeam(mDisplayedTeams.get(position)));
            return convertView;
        }

        Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    ArrayList<Team> FilteredArrList = new ArrayList<Team>();
                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = mTeams.size();
                        results.values = mTeams;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < mTeams.size(); i++) {
                            String data = mTeams.get(i).getName();
                            if (data.toLowerCase().contains(charSequence)) {
                                FilteredArrList.add(mTeams.get(i));
                            }
                        }
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mDisplayedTeams = (List<Team>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
    }

    private void editTeam(Team aTeam) {
        Intent myIntent = new Intent(ViewTeamsActivity.this, EditTeamActivity.class);
        myIntent.putExtra("team", aTeam);
        startActivity(myIntent);
    }
}
