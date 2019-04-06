package com.shan.howard.balltracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.GameViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.shan.howard.balltracker.TrackGameActivity.GAME;

public class ReviewGameActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Toolbar gameToolbar;

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

        gameToolbar = (Toolbar) findViewById(R.id.game_tb);
        setSupportActionBar(gameToolbar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

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
    }

    public static class CreateGameDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


            return builder.create();
        }
    }
}
