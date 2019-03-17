package com.shan.howard.balltracker;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.stream.Collectors;

public class NewGameActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private Game mGame = new Game();
    private Spinner mYourTeamSpinner;
    private Spinner mOpposingTeamSpinner;
    private EditText mDateEditText;
    private Button mBackButton;
    private Button mTrackButton;
    private TeamViewModel mTeamViewModel;
    private ArrayAdapter<String> mTeamsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        mYourTeamSpinner = findViewById(R.id.new_game_your_team_spinner);
        mOpposingTeamSpinner = findViewById(R.id.new_game_opposing_team_spinner);
        mTeamsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());

        mDateEditText = findViewById(R.id.new_game_date_edit_text);
        mDateEditText.setOnClickListener(this);
        updateLabel();

        mBackButton = findViewById(R.id.track_game_back_btn);
        mBackButton.setOnClickListener(this);
        mTrackButton = findViewById(R.id.new_game_track_btn);
        mTrackButton.setOnClickListener(this);

        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        mTeamViewModel.selectAll().observe(this, aTeams -> {
            if (aTeams != null) {
                mTeamsAdapter.clear();
                mTeamsAdapter.addAll(aTeams.stream().map(Team::getName).collect(Collectors.toList()));
            }
        });

        mYourTeamSpinner.setAdapter(mTeamsAdapter);
        mOpposingTeamSpinner.setAdapter(mTeamsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.track_game_back_btn:
                finish();
                break;
            case R.id.new_game_track_btn:
                Intent myIntent = new Intent(this, TrackGameActivity.class);
                startActivity(myIntent);
                break;
            case R.id.new_game_date_edit_text:
                new DatePickerDialog(this, this, mGame.getDate().get(Calendar.YEAR),
                        mGame.getDate().get(Calendar.MONTH), mGame.getDate().get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mGame.getDate().set(Calendar.YEAR, year);
        mGame.getDate().set(Calendar.MONTH, month);
        mGame.getDate().set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat mySdf = new SimpleDateFormat(myFormat, Locale.US);

        mDateEditText.setText(mySdf.format(mGame.getDate().getTime()));
    }
}
