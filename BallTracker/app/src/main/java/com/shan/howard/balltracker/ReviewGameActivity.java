package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.GameViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewGameActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button mainMenuBtn;
    private Spinner yourTeamSpinner;
    private Spinner opposingTeamSpinner;
    private ListView viewGamesLv;

    private List<Team> mTeams;
    private List<Game> mAllGames;

    private GameViewModel mGameViewModel;
    private TeamViewModel mTeamViewModel;
    private GameListAdapter mGameAdapter;
    private ArrayAdapter<String> mTeamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_game);

        // Set Widgets
        mainMenuBtn = findViewById(R.id.main_menu_btn);
        yourTeamSpinner = findViewById(R.id.yourTeam_spinner);
        opposingTeamSpinner = findViewById(R.id.opposingTeam_spinner);
        viewGamesLv = findViewById(R.id.view_games_lv);

        // Set click listener
        mainMenuBtn.setOnClickListener(this);

        // Set Team Adapter
        mTeamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());
        mTeamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Get all of the teams and store into mTeams
        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        mTeamViewModel.selectAllLive().observe(this, aTeams -> {
            if (aTeams != null) {
                mTeams = aTeams;
                mTeamAdapter.clear();
                mTeamAdapter.addAll(aTeams.stream().map(Team::getName).collect(Collectors.toList()));
                mTeamAdapter.notifyDataSetChanged();
            }
        });

        // Populate spinners with teams
        yourTeamSpinner.setAdapter(mTeamAdapter);
        yourTeamSpinner.setOnItemSelectedListener(this);

        opposingTeamSpinner.setAdapter(mTeamAdapter);
        opposingTeamSpinner.setOnItemSelectedListener(this);

        // Set game list view adapter
        mGameAdapter = new GameListAdapter(ReviewGameActivity.this);
        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        mGameViewModel.selectAllLive().observe(this, aGames -> {
            mAllGames = aGames;
            mGameAdapter.setGames(aGames);
            mGameAdapter.notifyDataSetChanged();
        });
        viewGamesLv.setAdapter(mGameAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu_btn:
                finish();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Team yourTeam = null;
        Team opposingTeam = null;

        // Selects team from spinners if not blank
        if (yourTeamSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION) {
            yourTeam = mTeams.get(yourTeamSpinner.getSelectedItemPosition());
        }
        if (opposingTeamSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION) {
            opposingTeam = mTeams.get(opposingTeamSpinner.getSelectedItemPosition());
        }

        List<Game> filterGameList = new ArrayList<>();

        // No teams searched
        if (yourTeam == null && opposingTeam == null) {
            filterGameList = mAllGames;
        }

        // Search games for only one team
        else if (yourTeam == null ^ opposingTeam == null) {
            Long searchTeamId = (yourTeam != null ? yourTeam : opposingTeam).getId();
            for (int i = 0; i < mAllGames.size(); i++) {
                Game currentGame = mAllGames.get(i);
                if (searchTeamId == currentGame.getYourTeamId() || searchTeamId == currentGame.getOpposingTeamId()) {
                    filterGameList.add(mAllGames.get(i));
                }
            }
        }

        // Search games for two teams
        else {
            Long searchYourTeamId = yourTeam.getId();
            Long searchOpposingTeamId = opposingTeam.getId();

            for (int i = 0; i < mAllGames.size(); i++) {
                Game currentGame = mAllGames.get(i);
                if ((searchYourTeamId == currentGame.getYourTeamId() && searchOpposingTeamId == currentGame.getOpposingTeamId()) ||
                        (searchYourTeamId == currentGame.getOpposingTeamId() && searchOpposingTeamId == currentGame.getYourTeamId())) {
                    filterGameList.add(mAllGames.get(i));
                }
            }
        }

        mGameAdapter.setGames(filterGameList);
        mGameAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class GameListAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private List<Game> mGames = new ArrayList<>();
        private List<Game> mDisplayedGames;

        GameListAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
            mDisplayedGames = mGames;
        }

        public void setGames(List<Game> aGames) {
            this.mGames = aGames;
            mDisplayedGames = mGames;
        }

        @Override
        public int getCount() {
            return mDisplayedGames.size();
        }

        @Override
        public Object getItem(int i) {
            return mGames.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = mLayoutInflater.inflate(R.layout.view_games_item, parent, false);

            TextView teamsTV = convertView.findViewById(R.id.teams_tv);
            TextView dateTV = convertView.findViewById(R.id.date_tv);

            Game currentGame = mDisplayedGames.get(position);
            Team yourTeam = mTeamViewModel.selectById(currentGame.getYourTeamId());
            Team opposingTeam = mTeamViewModel.selectById(currentGame.getOpposingTeamId());

            teamsTV.setText(yourTeam.getName() + " vs " + opposingTeam.getName());
            dateTV.setText(currentGame.getDate().toString());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(ReviewGameActivity.this, ReviewSpecificGameActivity.class);
                    myIntent.putExtra("game", mDisplayedGames.get(position));
                    startActivity(myIntent);
                }
            });

            return convertView;
        }
    }
}
