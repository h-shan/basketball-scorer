package com.shan.howard.balltracker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewTeamsActivity extends AppCompatActivity implements Button.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private ListView mLv;
    private Button mBackButton;
    private ImageView mNewTeamButton;
    private EditText mEtsearch;
    private TeamListAdapter mAdapter;

    private TeamViewModel mTeamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teams);

        mAdapter = new TeamListAdapter(ViewTeamsActivity.this);
        mLv = findViewById(R.id.view_teams_teams_lv);
        mLv.setAdapter(mAdapter);

        mBackButton = findViewById(R.id.view_teams_back_btn);
        mBackButton.setOnClickListener(this);
        mEtsearch = findViewById(R.id.searchET);
        mEtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("edittext", charSequence.toString());
                mAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mNewTeamButton = findViewById(R.id.view_teams_new_team_btn);
        mNewTeamButton.setOnClickListener(this);

        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        mTeamViewModel.getAllTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable final List<Team> aTeams) {
                Log.d(TAG, "New teams size: " + aTeams.size());
                mAdapter.setTeams(aTeams);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_teams_new_team_btn:
                mTeamViewModel.insert(new Team());
                Log.d(TAG, "New team!");
                break;
            case R.id.view_teams_back_btn:
                finish();
                break;
            default:
                break;
        }
    }

    class TeamListAdapter extends BaseAdapter implements com.shan.howard.balltracker.TeamListAdapter {
        private LayoutInflater mLayoutInflater;
        private List<Team> mTeams = new ArrayList<>();
        private List<Team> mDisplayedTeams;

        TeamListAdapter(Context context){
            mLayoutInflater = LayoutInflater.from(context);
            mDisplayedTeams=mTeams;
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

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent myIntent = new Intent(ViewTeamsActivity.this, EditTeamActivity.class);
                    myIntent.putExtra("team", mDisplayedTeams.get(position));
                    startActivity(myIntent);
                }
            });
            return convertView;
        }

        @Override
        public Filter getFilter(){
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    ArrayList<Team> FilteredArrList = new ArrayList<Team>();
                     if(charSequence == null || charSequence.length() == 0){
                         results.count = mTeams.size();
                         results.values = mTeams;
                     } else {
                         charSequence = charSequence.toString().toLowerCase();
                         for(int i=0; i<mTeams.size(); i++){
                             String data = mTeams.get(i).getName();
                             if(data.toLowerCase().contains(charSequence)){
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
            return  filter;
        }
    }
}


