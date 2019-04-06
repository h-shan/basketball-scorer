package com.shan.howard.balltracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.GameViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class NewGameActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private Game mGame = new Game();
    private Spinner mYourTeamSpinner;
    private Spinner mOpposingTeamSpinner;
    private EditText mDateEditText;
    private Button mTrackButton;
    private TeamViewModel mTeamViewModel;
    private GameViewModel mGameViewModel;
    private ArrayAdapter<String> yourTeamsAdapter;
    private ArrayAdapter<String> opposingTeamsAdapter;
    private long yourTeamID;
    private long opposingTeamID;

    private List<Team> mTeams;
    private List<Team> yourTeams;
    private List<Team> opposingTeams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        mYourTeamSpinner = findViewById(R.id.new_game_your_team_spinner);
        mOpposingTeamSpinner = findViewById(R.id.new_game_opposing_team_spinner);
        yourTeamsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());
        opposingTeamsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());

        mDateEditText = findViewById(R.id.new_game_date_edit_text);
        mDateEditText.setOnClickListener(this);
        updateLabel();

        mTrackButton = findViewById(R.id.new_game_track_btn);
        mTrackButton.setOnClickListener(this);

        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        mTeamViewModel.selectAllLive().observe(this, aTeams -> {
            if (aTeams != null && aTeams.size() >= 2) {
                mTeams = aTeams;
                yourTeamID = mTeams.get(0).getId();
                opposingTeamID = mTeams.get(1).getId();

                yourTeamsAdapter.clear();
                yourTeams = newTeams(mTeams);
                removeTeamByID(yourTeams,opposingTeamID);
                yourTeamsAdapter.addAll(yourTeams.stream().map(Team::getName).collect(Collectors.toList()));
                yourTeamsAdapter.notifyDataSetChanged();

                opposingTeamsAdapter.clear();
                opposingTeams = newTeams(mTeams);
                removeTeamByID(opposingTeams,yourTeamID);
                opposingTeamsAdapter.addAll(opposingTeams.stream().map(Team::getName).collect(Collectors.toList()));
                opposingTeamsAdapter.notifyDataSetChanged();

            } else {
                alertNotEnoughTeams();
            }
        });

        mYourTeamSpinner.setAdapter(yourTeamsAdapter);

        mOpposingTeamSpinner.setAdapter(opposingTeamsAdapter);

        mYourTeamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opposingTeams = newTeams(mTeams);
                removeTeamByID(opposingTeams,yourTeams.get(position).getId());
                opposingTeamsAdapter.clear();
                opposingTeamsAdapter.addAll(opposingTeams.stream().map(Team::getName).collect(Collectors.toList()));
                opposingTeamsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        mOpposingTeamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yourTeams = newTeams(mTeams);
                removeTeamByID(yourTeams,opposingTeams.get(position).getId());
                yourTeamsAdapter.clear();
                yourTeamsAdapter.addAll(yourTeams.stream().map(Team::getName).collect(Collectors.toList()));
                yourTeamsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        Toolbar myToolbar = findViewById(R.id.new_game_tb);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }
    }

    public void removeTeamByID(List<Team> Teams, long ID){
        for (Iterator<Team> iterator = Teams.iterator(); iterator.hasNext(); ) {
            Team value = iterator.next();
            if (value.getId() == ID) {
                iterator.remove();
            }
        }
    }

    private List<Team> newTeams(List<Team> mTeams){
        List<Team> res = new ArrayList<Team>(mTeams);
        return  res;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.track_game_back_btn:
                finish();
                break;
            case R.id.new_game_track_btn:
                saveGame();
                Intent myIntent = new Intent(this, TrackGameActivity.class);
                myIntent.putExtra("GAME", mGame);
                myIntent.putExtra("YOUR_TEAM", yourTeams.get(mYourTeamSpinner.getSelectedItemPosition()));
                myIntent.putExtra("OPPOSING_TEAM", opposingTeams.get(mOpposingTeamSpinner.getSelectedItemPosition()));
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

    private void saveGame() {
        Team myYourTeam = mTeams.get(mYourTeamSpinner.getSelectedItemPosition());
        Team myOpposingTeam = mTeams.get(mOpposingTeamSpinner.getSelectedItemPosition());
        mGame.setOpposingTeamId(myOpposingTeam.getId());
        mGame.setYourTeamId(myYourTeam.getId());
        mGameViewModel.insert(mGame);
    }

    private void alertNotEnoughTeams() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Activity myActivity = this;
// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("You do not have enough teams to create a new game. Please create at least two teams by clicking on the \"Team\" button on the home page.")
                .setTitle("Not Enough Teams!")
                .setPositiveButton("OK", (dialog, id) -> {
                    myActivity.finish();
                });

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
