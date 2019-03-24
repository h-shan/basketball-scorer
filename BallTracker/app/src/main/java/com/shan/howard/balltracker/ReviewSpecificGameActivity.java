package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shan.howard.balltracker.datamodels.Event;
import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.EventViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.stream.IntStream;

public class ReviewSpecificGameActivity extends AppCompatActivity implements View.OnClickListener {

    // Top Buttons
    private Button reviewGameBtn;
    private Button editGameBtn;
    private Button screenShotBtn;

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
    private EventViewModel mEventViewModel;

    enum Points {
        THREE_POINTER, TWO_POINTER, FREE_THROW, FOUL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_specific_game);

        Event event1 = new Event();
        event1.setId(1);
        event1.setTeamId(1);
        event1.setGameId(10);
        event1.setQuarter(1);
        event1.setEventType("THREE_POINTER");

        Event event2 = new Event();
        event2.setId(2);
        event2.setTeamId(2);
        event2.setGameId(10);
        event2.setQuarter(3);
        event2.setEventType("FREE_THROW");

        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventViewModel.insert(event1);
        mEventViewModel.insert(event2);

        // Get Arrow Button
        reviewGameBtn = findViewById(R.id.review_game_btn);
        editGameBtn = findViewById(R.id.edit_game_btn);
        screenShotBtn = findViewById(R.id.screenshot_btn);

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

        mTeamViewModel.selectById(curGame.getYourTeamId()).observe(this, aTeam -> {
            yourTeam = aTeam;
            setYourTeam(yourTeam);
        });
        //opposingTeam = mTeamViewModel.selectById(curGame.getOpposingTeamId());
        mTeamViewModel.selectById(curGame.getOpposingTeamId()).observe(this, aTeam -> {
            opposingTeam = aTeam;
            setOpposingTeam(opposingTeam);
        });

        setButtons();
        setGameDetail(curGame);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.review_game_btn:
                finish();
                break;

            case R.id.edit_game_btn:
                Intent editGameIntent = new Intent(this, TrackGameActivity.class);
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

            case R.id.screenshot_btn:
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                shareImage(store(getScreenShot(rootView), "ReviewSpecificGameActivity"));
                break;
        }
    }

    public Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public String store(Bitmap bm, String fileName) {
        File imageFile = new File(new File(Environment.getExternalStorageDirectory().toString()), fileName);
        try {
            FileOutputStream out = new FileOutputStream(imageFile);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageFile.getAbsolutePath();
    }

    private void shareImage(String path) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("application/image");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
        startActivity(Intent.createChooser(emailIntent, "Send mail"));
    }

    public void setButtons() {
        reviewGameBtn.setOnClickListener(this);
        editGameBtn.setOnClickListener(this);
        yourTeamBtn.setOnClickListener(this);
        opposingTeamBtn.setOnClickListener(this);
        screenShotBtn.setOnClickListener(this);
    }

    public void setGameDetail(Game curGame) {
        scoreTv.setText(this.curGame.getYourTeamScore() + " - " + this.curGame.getOpposingTeamScore());

        Calendar gameCalendar = curGame.getDate();
        String date = gameCalendar.get(Calendar.MONTH) + "/" + gameCalendar.get(Calendar.DATE) + "/" + gameCalendar.get(Calendar.YEAR);
        dateTv.setText(date);
    }

    public void setOpposingTeam(Team opposingTeam) {
        // Set Team Name
        this.opposingTeamBtn.setText(this.opposingTeam.getName());
        this.opposingTeamTv.setText(this.opposingTeam.getName());

        // Set Quarter, Overtime Summary and Details
        EventViewModel mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventViewModel.selectByGameId(curGame.getId()).observe(this, aEvents -> {

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
            int[] teamQuarters = new int[6];
            int[] teamEvents = new int[4];

            for (int i = 0; i < aEvents.size(); i++) {
                Event curEvent = aEvents.get(i);

                if (curEvent.getTeamId() == this.opposingTeam.getId()) {
                    int qt = curEvent.getQuarter();
                    int event = Points.valueOf(curEvent.getEventType()).ordinal();

                    teamQuarters[qt]++;
                    teamEvents[event]++;
                }

            }

            // Sets each numerical text view
            opposingTeamQt1Tv.setText(String.valueOf(teamQuarters[0]));
            opposingTeamQt2Tv.setText(String.valueOf(teamQuarters[1]));
            opposingTeamQt3Tv.setText(String.valueOf(teamQuarters[2]));
            opposingTeamQt4Tv.setText(String.valueOf(teamQuarters[3]));
            opposingTeamOt1Tv.setText(String.valueOf(teamQuarters[4]));
            opposingTeamOt2Tv.setText(String.valueOf(teamQuarters[5]));
            opposingTeamTotalScoreTv.setText(String.valueOf(IntStream.of(teamQuarters).sum()));

            opposingTeamThreePointersTv.setText(String.valueOf(teamEvents[0]));
            opposingTeamTwoPointersTv.setText(String.valueOf(teamEvents[1]));
            opposingTeamFreeThrowsTv.setText(String.valueOf(teamEvents[2]));
            opposingTeamFoulsTv.setText(String.valueOf(teamEvents[3]));
        });
    }

    public void setYourTeam(Team yourTeam) {

        // Set Team Name
        this.yourTeamBtn.setText(this.yourTeam.getName());
        this.yourTeamTv.setText(this.yourTeam.getName());

        // Set Quarter, Overtime Summary and Details
        EventViewModel mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mEventViewModel.selectByGameId(curGame.getId()).observe(this, aEvents -> {

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
            int[] teamQuarters = new int[6];
            int[] teamEvents = new int[4];

            for (int i = 0; i < aEvents.size(); i++) {
                Event curEvent = aEvents.get(i);

                if (curEvent.getTeamId() == this.yourTeam.getId()) {
                    int qt = curEvent.getQuarter();
                    int event = Points.valueOf(curEvent.getEventType()).ordinal();

                    teamQuarters[qt]++;
                    teamEvents[event]++;
                }

            }

            // Sets each numerical text view
            yourTeamQt1Tv.setText(String.valueOf(teamQuarters[0]));
            yourTeamQt2Tv.setText(String.valueOf(teamQuarters[1]));
            yourTeamQt3Tv.setText(String.valueOf(teamQuarters[2]));
            yourTeamQt4Tv.setText(String.valueOf(teamQuarters[3]));
            yourTeamOt1Tv.setText(String.valueOf(teamQuarters[4]));
            yourTeamOt2Tv.setText(String.valueOf(teamQuarters[5]));
            yourTeamTotalScoreTv.setText(String.valueOf(IntStream.of(teamQuarters).sum()));

            yourTeamThreePointersTv.setText(String.valueOf(teamEvents[0]));
            yourTeamTwoPointersTv.setText(String.valueOf(teamEvents[1]));
            yourTeamFreeThrowsTv.setText(String.valueOf(teamEvents[2]));
            yourTeamFoulsTv.setText(String.valueOf(teamEvents[3]));
        });
    }
}
