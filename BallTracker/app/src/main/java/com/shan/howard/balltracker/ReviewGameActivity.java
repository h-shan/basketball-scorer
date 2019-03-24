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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.GameViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewGameActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button mainMenuBtn;
    private Spinner yourTeamSpinner;
    private Spinner opposingTeamSpinner;
    private ListView viewGamesLv;

    private List<Team> mAllTeams;
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

        // Get all of the teams and store into mAllTeams
        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        Team team1 = new Team();
        team1.setId(1);
        team1.setName("Team1");
        Team team2 = new Team();
        team2.setId(2);
        team2.setName("Team2");
        mTeamViewModel.insert(team1);
        mTeamViewModel.insert(team2);

        mTeamViewModel.selectAllLive().observe(this, aTeams -> {
            if (aTeams != null) {
                mAllTeams = aTeams;
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

        Game game1 = new Game();
        game1.setId(10);
        game1.setYourTeamId(1);
        game1.setOpposingTeamId(2);
        mGameViewModel.insert(game1);

        viewGamesLv.setAdapter(null);
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
            yourTeam = mAllTeams.get(yourTeamSpinner.getSelectedItemPosition());
        }
        if (opposingTeamSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION) {
            opposingTeam = mAllTeams.get(opposingTeamSpinner.getSelectedItemPosition());
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
        yourTeamSpinner.setSelection(0);
        opposingTeamSpinner.setSelection(0);
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

            Team yourTeam = null;
            Team opposingTeam = null;

            // Gets the team
            for (int i = 0; i < mAllTeams.size(); i++) {
                if (mAllTeams.get(i).getId() == currentGame.getYourTeamId()) {
                    yourTeam = mAllTeams.get(i);
                } else if (mAllTeams.get(i).getId() == currentGame.getOpposingTeamId()) {
                    opposingTeam = mAllTeams.get(i);
                }
            }

            if (yourTeam != null && opposingTeam != null) {
                String teamsVs = yourTeam.getName() + " vs " + opposingTeam.getName();
                teamsTV.setText(teamsVs);

                Calendar gameCalendar = currentGame.getDate();
                String date = gameCalendar.get(Calendar.MONTH) + "/" + gameCalendar.get(Calendar.DATE) + "/" + gameCalendar.get(Calendar.YEAR);
                dateTV.setText(date);
            }

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
