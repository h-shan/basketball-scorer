package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.GameViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.shan.howard.balltracker.TrackGameActivity.GAME;

public class ReviewGameActivity extends AppCompatActivity {

    private List<Team> mAllTeams;

    private ListView gameLV;
    private Toolbar gameTB;

    private GameViewModel mGameViewModel;
    private TeamViewModel mTeamViewModel;
    private GameListAdapter mGameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_game);

        mGameAdapter = new GameListAdapter(ReviewGameActivity.this);
        gameLV = findViewById(R.id.view_games_lv);
        gameLV.setAdapter(mGameAdapter);

        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        mGameViewModel.selectAllLive().observe(this, aGames -> {
            mGameAdapter.setGames(aGames);
            mGameAdapter.notifyDataSetChanged();
        });

        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        mTeamViewModel.selectAllLive().observe(this, aTeams -> {
            mAllTeams = aTeams;
        });

        gameTB = findViewById(R.id.game_tb);
        setSupportActionBar(gameTB);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.game_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mGameAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
            for (int i = 0; i < mAllTeams.size(); i++)
                if (mAllTeams.get(i).getId() == currentGame.getYourTeamId()) {
                    yourTeam = mAllTeams.get(i);
                } else if (mAllTeams.get(i).getId() == currentGame.getOpposingTeamId()) {
                    opposingTeam = mAllTeams.get(i);
                }

            if (yourTeam != null && opposingTeam != null) {
                String teamsVs = yourTeam.getName() + " vs " + opposingTeam.getName();
                teamsTV.setText(teamsVs);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat mySdf = new SimpleDateFormat(myFormat, Locale.US);

                dateTV.setText(mySdf.format(currentGame.getDate().getTime()));
            }

            convertView.setOnClickListener(v -> {
                Intent myIntent = new Intent(ReviewGameActivity.this, ReviewSpecificGameActivity.class);
                myIntent.putExtra(GAME, mDisplayedGames.get(position));
                startActivity(myIntent);
            });

            return convertView;
        }

        Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    ArrayList<Game> FilteredArrList = new ArrayList<Game>();
                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = mGames.size();
                        results.values = mGames;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < mGames.size(); i++) {
                            long yourTeamId = mGames.get(i).getYourTeamId();
                            long opposingTeamId = mGames.get(i).getOpposingTeamId();

                            Team yourTeam = null;
                            Team opposingTeam = null;

                            for (int j = 0; j < mAllTeams.size(); j++) {
                                if (yourTeam != null && opposingTeam != null) {
                                    break;
                                }
                                if (mAllTeams.get(j).getId() == yourTeamId) {
                                    yourTeam = mAllTeams.get(j);
                                }
                                if (mAllTeams.get(j).getId() == opposingTeamId) {
                                    opposingTeam = mAllTeams.get(j);
                                }
                            }

                            String data = yourTeam.getName() + " vs " + opposingTeam.getName();
                            if (data.toLowerCase().contains(charSequence)) {
                                FilteredArrList.add(mGames.get(i));
                            }
                        }
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mDisplayedGames = (List<Game>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
    }
}
