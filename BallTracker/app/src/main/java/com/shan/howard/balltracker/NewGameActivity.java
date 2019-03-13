package com.shan.howard.balltracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewGameActivity extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private static final String[] PLACEHOLDER_TEAMS = { "Clippers", "Kings", "Warriors" };

    private Spinner mYourTeamSpinner;
    private Spinner mOpposingTeamSpinner;
    private EditText mDateEditText;
    private Button mBackButton;
    private Button mTrackButton;

    private Calendar theCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        mYourTeamSpinner = findViewById(R.id.new_game_your_team_spinner);
        Utils.initializeSpinner(this, mYourTeamSpinner, PLACEHOLDER_TEAMS);
        mOpposingTeamSpinner = findViewById(R.id.new_game_opposing_team_spinner);
        Utils.initializeSpinner(this, mOpposingTeamSpinner, PLACEHOLDER_TEAMS);

        mDateEditText = findViewById(R.id.new_game_date_edit_text);
        mDateEditText.setOnClickListener(this);

        mBackButton = findViewById(R.id.track_game_back_btn);
        mBackButton.setOnClickListener(this);
        mTrackButton = findViewById(R.id.new_game_track_btn);
        mTrackButton.setOnClickListener(this);
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
                new DatePickerDialog(this, this, theCalendar.get(Calendar.YEAR),
                        theCalendar.get(Calendar.MONTH), theCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        theCalendar.set(Calendar.YEAR, year);
        theCalendar.set(Calendar.MONTH, month);
        theCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat mySdf = new SimpleDateFormat(myFormat, Locale.US);

        mDateEditText.setText(mySdf.format(theCalendar.getTime()));
    }
}
