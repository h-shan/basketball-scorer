package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.GameViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

public class ReviewSpecificGameActivity extends AppCompatActivity implements View.OnClickListener {

    Button trackGameBtn;
    Button reviewGameBtn;
    Button trashBtn;
    Button team1Btn;
    Button team2Btn;
    TextView scoreTV;
    TextView dateTV;

    TextView team1TV;
    TextView team1QT1TV;
    TextView team1QT2TV;
    TextView team1QT3TV;
    TextView team1QT4TV;
    TextView team1OT1TV;
    TextView team1OT2TV;
    TextView team1TotalTV;

    TextView team2TV;
    TextView team2QT1TV;
    TextView team2QT2TV;
    TextView team2QT3TV;
    TextView team2QT4TV;
    TextView team2OT1TV;
    TextView team2OT2TV;
    TextView team2TotalTV;

    TextView team13PtTV;
    TextView team12PtTV;
    TextView team1FreeThrowTV;
    TextView team1FoulTV;

    TextView team23PtTV;
    TextView team22PtTV;
    TextView team2FreeThrowTV;
    TextView team2FoulTV;

    Game currentGame;
    GameViewModel mGameViewModel;

    Team yourTeam;
    Team opposingTeam;
    TeamViewModel mTeamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_specific_game);

        // Pass extras through intent
        Intent myIntent = getIntent();
        Long gameID = myIntent.getLongExtra("gameID", -1);

        // Get Widgets by id
        trackGameBtn = findViewById(R.id.trackGame_btn);
        reviewGameBtn = findViewById(R.id.reviewGame_btn);
        trashBtn = findViewById(R.id.trash_btn);
        team1Btn = findViewById(R.id.team1_btn);
        team2Btn = findViewById(R.id.team2_btn);
        scoreTV = findViewById(R.id.score_tv);
        dateTV = findViewById(R.id.date_tv);
        
        team1TV = findViewById(R.id.team1_tv);
        team1QT1TV = findViewById(R.id.team1QT1_tv);
        team1QT2TV = findViewById(R.id.team1QT2_tv);
        team1QT3TV = findViewById(R.id.team1QT3_tv);
        team1QT4TV = findViewById(R.id.team1QT4_tv);
        team1OT1TV = findViewById(R.id.team1OT1_tv);
        team1OT2TV = findViewById(R.id.team1OT2_tv);
        team1TotalTV = findViewById(R.id.team1Total_tv);

        team2TV = findViewById(R.id.team2_tv);
        team2QT1TV = findViewById(R.id.team2QT1_tv);
        team2QT2TV = findViewById(R.id.team2QT2_tv);
        team2QT3TV = findViewById(R.id.team2QT3_tv);
        team2QT4TV = findViewById(R.id.team2QT4_tv);
        team2OT1TV = findViewById(R.id.team2OT1_tv);
        team2OT2TV = findViewById(R.id.team2OT2_tv);
        team2TotalTV = findViewById(R.id.team2Total_tv);

        team13PtTV = findViewById(R.id.team1_3Pt);
        team12PtTV = findViewById(R.id.team1_2Pt);
        team1FreeThrowTV = findViewById(R.id.team1_freeThrow);
        team1FoulTV = findViewById(R.id.team1_foul);

        team23PtTV = findViewById(R.id.team2_3Pt);
        team22PtTV = findViewById(R.id.team2_2Pt);
        team2FreeThrowTV = findViewById(R.id.team2_freeThrow);
        team2FoulTV = findViewById(R.id.team2_foul);

        // TODO Get Game object
        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        mGameViewModel.selectById(gameID);
        mGameViewModel.selectAllLive().observe(this, aGames -> {
            currentGame = aGames.get(0);
        });

        // TODO Get Teams object
        Long team1ID = currentGame.getYourTeamId();
        Long team2ID = currentGame.getOpposingTeamId();

        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        mTeamViewModel.selectById(team1ID);
        mTeamViewModel.selectAllLive().observe(this, aTeam -> {
            yourTeam = aTeam.get(0);
        });

        mTeamViewModel.selectById(team2ID);
        mTeamViewModel.selectAllLive().observe(this, aTeam -> {
           opposingTeam = aTeam.get(0);
        });

        // Set Widget Text
        team1Btn.setText(yourTeam.getName());
        team2Btn.setText(opposingTeam.getName());
        scoreTV.setText(currentGame.getYourTeamScore() + " - " + currentGame.getOpposingTeamScore());
        dateTV.setText(currentGame.getDate().toString());

        // TODO Game implement QT, OT, and 3-2-1 Pt
        team1TV.setText(yourTeam.getName());
        team2TV.setText(opposingTeam.getName());

        // Set Widget Button
        trackGameBtn.setOnClickListener(this);
        reviewGameBtn.setOnClickListener(this);
        trashBtn.setOnClickListener(this);
        team1Btn.setOnClickListener(this);
        team2Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.trackGame_btn:
                finish();
                break;
            case R.id.reviewGame_btn:
                Intent intentReview = new Intent(ReviewSpecificGameActivity.this, TrackGameActivity.class);
                // TODO Add getIntent in TrackGameActivity
                intentReview.putExtra("gameID", currentGame.getId());
                startActivity(intentReview);
                break;
            case R.id.trash_btn:
                // TODO Add popup (snackbar)
                break;
            case R.id.team1_tv:
                Intent intentTeam1 = new Intent(ReviewSpecificGameActivity.this, ViewTeamsActivity.class);
                intentTeam1.putExtra("teamID", yourTeam.getId());
                startActivity(intentTeam1);
                break;
            case R.id.team2_tv:
                Intent intentTeam2 = new Intent(ReviewSpecificGameActivity.this, ViewTeamsActivity.class);
                intentTeam2.putExtra("teamID", opposingTeam.getId());
                startActivity(intentTeam2);
                break;
        }
    }
}
