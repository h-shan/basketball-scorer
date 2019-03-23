package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Event;
import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.EventViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

public class ReviewSpecificGameActivity extends AppCompatActivity implements View.OnClickListener {

    // Top Arrow Buttons
    private Button reviewGameBtn;
    private Button editGameBtn;

    // Game Summary
    private Button yourTeamBtn;
    private Button opposingTeamBtn;
    private TextView scoreTv;
    private TextView dateTv;

    // Quarter and Overtime Summary
    private TextView yourTeamTv;
    private TextView yourTeamQt1Tv;
    private TextView yourTeamQt2Tv;
    private TextView yourTeamQt3Tv;
    private TextView yourTeamQt4Tv;
    private TextView yourTeamOt1Tv;
    private TextView yourTeamOt2Tv;
    private TextView yourTeamTotalScoreTv;

    private TextView opposingTeamTv;
    private TextView opposingTeamQt1Tv;
    private TextView opposingTeamQt2Tv;
    private TextView opposingTeamQt3Tv;
    private TextView opposingTeamQt4Tv;
    private TextView opposingTeamOt1Tv;
    private TextView opposingTeamOt2Tv;
    private TextView opposingTeamTotalScoreTv;

    // Game Details
    private TextView yourTeamThreePointersTv;
    private TextView yourTeamTwoPointersTv;
    private TextView yourTeamFreeThrowsTv;
    private TextView yourTeamFoulsTv;

    private TextView opposingTeamThreePointersTv;
    private TextView opposingTeamTwoPointersTv;
    private TextView opposingTeamFreeThrowsTv;
    private TextView opposingTeamFoulsTv;

    // Local Variables
    private Game curGame;
    private Team yourTeam;
    private Team opposingTeam;

    private TeamViewModel mTeamViewModel;

    enum Points {
        THREE_POINTER, TWO_POINTER, FREE_THROW, FOUL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_specific_game);

        // Get Arrow Button
        reviewGameBtn = findViewById(R.id.review_game_btn);
        editGameBtn = findViewById(R.id.edit_game_btn);

        // Get Game Summary
        yourTeamBtn = findViewById(R.id.your_team_btn);
        opposingTeamBtn = findViewById(R.id.opposing_team_btn);
        scoreTv = findViewById(R.id.score_tv);
        dateTv = findViewById(R.id.date_tv);

        // Get Quarter and Overtime Summary
        yourTeamTv = findViewById(R.id.your_team_tv);
        yourTeamQt1Tv = findViewById(R.id.your_team_qt1_tv);
        yourTeamQt2Tv = findViewById(R.id.your_team_qt2_tv);
        yourTeamQt3Tv = findViewById(R.id.your_team_qt3_tv);
        yourTeamQt4Tv = findViewById(R.id.your_team_qt4_tv);
        yourTeamOt1Tv = findViewById(R.id.your_team_ot1_tv);
        yourTeamOt2Tv = findViewById(R.id.your_team_ot2_tv);
        yourTeamTotalScoreTv = findViewById(R.id.your_team_total_score_tv);

        opposingTeamTv = findViewById(R.id.opposing_team_tv);
        opposingTeamQt1Tv = findViewById(R.id.opposing_team_qt1_tv);
        opposingTeamQt2Tv = findViewById(R.id.opposing_team_qt2_tv);
        opposingTeamQt3Tv = findViewById(R.id.opposing_team_qt3_tv);
        opposingTeamQt4Tv = findViewById(R.id.opposing_team_qt4_tv);
        opposingTeamOt1Tv = findViewById(R.id.opposing_team_ot1_tv);
        opposingTeamOt2Tv = findViewById(R.id.opposing_team_ot2_tv);
        opposingTeamTotalScoreTv = findViewById(R.id.opposing_team_total_score_tv);

        // Get Game Details
        yourTeamThreePointersTv = findViewById(R.id.your_team_three_pointers_tv);
        yourTeamTwoPointersTv = findViewById(R.id.your_team_two_pointers_tv);
        yourTeamFreeThrowsTv = findViewById(R.id.your_team_free_throws_tv);
        yourTeamFoulsTv = findViewById(R.id.your_team_fouls_tv);

        opposingTeamThreePointersTv = findViewById(R.id.opposing_team_three_pointers_tv);
        opposingTeamTwoPointersTv = findViewById(R.id.opposing_team_two_pointers_tv);
        opposingTeamFreeThrowsTv = findViewById(R.id.opposing_team_free_throws_tv);
        opposingTeamFoulsTv = findViewById(R.id.opposing_team_fouls_tv);

        // Setup Team View Model
        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        // Get Game and Team from Intent
        Intent myIntent = getIntent();
        curGame = myIntent.getParcelableExtra("game");
        yourTeam = mTeamViewModel.selectById(curGame.getYourTeamId());
        opposingTeam = mTeamViewModel.selectById(curGame.getOpposingTeamId());

        setButtons();
        setTextViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.review_game_btn:
                finish();
                break;

            case R.id.edit_game_btn:
                Intent editGameIntent = new Intent(this, ReviewGameActivity.class);
                editGameIntent.putExtra("game", curGame);
                startActivity(editGameIntent);
                break;

            case R.id.your_team_btn:
                Intent yourTeamIntent = new Intent(this, EditTeamActivity.class);
                yourTeamIntent.putExtra("team", yourTeam);
                startActivity(yourTeamIntent);
                break;

            case R.id.opposing_team_btn:
                Intent opposingTeamIntent = new Intent(this, EditTeamActivity.class);
                opposingTeamIntent.putExtra("team", opposingTeam);
                startActivity(opposingTeamIntent);
                break;

        }
    }

    public void setButtons() {
        reviewGameBtn.setOnClickListener(this);
        editGameBtn.setOnClickListener(this);
        yourTeamBtn.setOnClickListener(this);
        opposingTeamBtn.setOnClickListener(this);
    }

    public void setTextViews() {

        // Set Game Summary Text
        yourTeamBtn.setText(yourTeam.getName());
        opposingTeamBtn.setText(opposingTeam.getName());
        scoreTv.setText(curGame.getYourTeamScore() + " - " + curGame.getOpposingTeamScore());
        dateTv.setText(curGame.getDate().toString());

        // Set Quarter, Overtime Summary and Details
        yourTeamTv.setText(yourTeam.getName());
        opposingTeamTv.setText(opposingTeam.getName());

        EventViewModel mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventViewModel.selectByGameId(curGame.getId()).observe(this, aEvents -> {

            // Row 0: yourTeam
            // Row 1: opposingTeam

            // teamQuarters
            // Col 0: Qt1
            // Col 1: Qt2
            // Col 2: Qt3
            // Col 3: Qt4
            // Col 4: Ot1
            // Col 5: Ot2

            // teamEvents
            // Col 0: THREE_POINTER
            // Col 1: TWO_POINTER
            // Col 2: FREE_THROW
            // Col 3: FOUL

            // Categorizes each event based on team, quarter, and event
            int[][] teamQuarters = new int[2][6];
            int[][] teamEvents = new int[2][4];

            for (int i = 0; i < aEvents.size(); i++) {
                Event curEvent = aEvents.get(i);

                int team = curEvent.getTeamId()==yourTeam.getId() ? 0 : 1;
                int qt = curEvent.getQuarter();
                int event = Points.valueOf(curEvent.getEventType()).ordinal();

                teamQuarters[team][qt]++;
                teamEvents[team][event]++;
            }

            // Sets each numerical text view
            yourTeamQt1Tv.setText(teamQuarters[0][0]);
            yourTeamQt2Tv.setText(teamQuarters[0][1]);
            yourTeamQt3Tv.setText(teamQuarters[0][2]);
            yourTeamQt4Tv.setText(teamQuarters[0][3]);
            yourTeamOt1Tv.setText(teamQuarters[0][4]);
            yourTeamOt2Tv.setText(teamQuarters[0][5]);

            opposingTeamQt1Tv.setText(teamQuarters[1][0]);
            opposingTeamQt2Tv.setText(teamQuarters[1][1]);
            opposingTeamQt3Tv.setText(teamQuarters[1][2]);
            opposingTeamQt4Tv.setText(teamQuarters[1][3]);
            opposingTeamOt1Tv.setText(teamQuarters[1][4]);
            opposingTeamOt2Tv.setText(teamQuarters[1][5]);
            
            yourTeamThreePointersTv.setText(teamEvents[0][0]);
            yourTeamTwoPointersTv.setText(teamEvents[0][1]);
            yourTeamFreeThrowsTv.setText(teamEvents[0][2]);
            yourTeamFoulsTv.setText(teamEvents[0][3]);

            opposingTeamThreePointersTv.setText(teamEvents[1][0]);
            opposingTeamTwoPointersTv.setText(teamEvents[1][1]);
            opposingTeamFreeThrowsTv.setText(teamEvents[1][2]);
            opposingTeamFoulsTv.setText(teamEvents[1][3]);
        });
    }
}
