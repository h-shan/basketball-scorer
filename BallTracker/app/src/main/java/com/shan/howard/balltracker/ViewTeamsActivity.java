package com.shan.howard.balltracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Team;

import java.util.ArrayList;
import java.util.List;

public class ViewTeamsActivity extends Activity implements Button.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private ListView mLv;
    private Button mBackButton;
    private EditText mEtsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teams);

        List<Team> myTeams = new ArrayList<>();
        Team myTeam1 = new Team();
        myTeam1.setName("Clippers");
        Team myTeam2 = new Team();
        myTeam2.setName("Kings");
        myTeams.add(myTeam1);
        myTeams.add(myTeam2);

        mLv = findViewById(R.id.teamsLV);
        mLv.setAdapter(new TeamListAdapter(ViewTeamsActivity.this, myTeams));

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
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_teams_back_btn:
                finish();
                break;
            default:
                break;
        }
    }
}

class TeamListAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<Team> mTeams;

    TeamListAdapter(Context context, List<Team> aTeams){
        mLayoutInflater = LayoutInflater.from(context);
        mTeams = aTeams;
    }

    @Override
    public int getCount() {
        return mTeams.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.view_teams_item, parent, false);
        TextView nameTV = convertView.findViewById(R.id.view_teams_name_tv);
        Team myTeam = mTeams.get(position);
        nameTV.setText(myTeam.getName());
        return convertView;
    }
}
