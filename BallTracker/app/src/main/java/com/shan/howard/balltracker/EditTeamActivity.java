package com.shan.howard.balltracker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.GameViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import java.util.List;
import java.util.stream.Collectors;

import yuku.ambilwarna.AmbilWarnaDialog;

public class EditTeamActivity extends AppCompatActivity {
    private Team mTeam;
    private EditText mNameET;
    private EditText mNotesET;
    private Button myTeamColor;
    private int currentColor;
    private String mScoreSuffix = "";

    private TeamViewModel mTeamViewModel;
    private GameViewModel mGameViewModel;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        Intent myIntent = getIntent();
        mTeam = myIntent.getParcelableExtra("team");

        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel .class);
        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        myTeamColor = findViewById(R.id.colorButton);
        GradientDrawable bg = (GradientDrawable) myTeamColor.getBackground();
        bg.setColor(mTeam.getColor());
        mNameET = findViewById(R.id.edit_team_name_et);
        mNameET.setText(mTeam.getName());
        mNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTeam.setName(charSequence.toString());
                setTitle(mTeam.getName() + mScoreSuffix);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mNotesET = findViewById(R.id.edit_team_notes_edit_text);
        mNotesET.setText(mTeam.getNotes());
        mNotesET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTeam.setNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Toolbar myToolbar = findViewById(R.id.edit_team_tb);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }

        setTitle(mTeam.getName());
        mGameViewModel.selectAllLive().observe(this, aGames -> {
            if (aGames != null) {
                List<Game> myYourGames = aGames.stream()
                        .filter(aGame -> aGame.getYourTeamId() == mTeam.getId())
                        .collect(Collectors.toList());
                List<Game> myOpposingGames = aGames.stream()
                        .filter(aGame -> aGame.getOpposingTeamId() == mTeam.getId())
                        .collect(Collectors.toList());
                long numWon = myYourGames.stream().filter(aGame -> aGame.getYourTeamScore() > aGame.getOpposingTeamScore()).count();
                numWon += myOpposingGames.stream().filter(aGame -> aGame.getYourTeamScore() < aGame.getOpposingTeamScore()).count();
                long numDrew = myYourGames.stream().filter(aGame -> aGame.getYourTeamScore() == aGame.getOpposingTeamScore()).count();
                numDrew += myOpposingGames.stream().filter(aGame -> aGame.getYourTeamScore() == aGame.getOpposingTeamScore()).count();
                long numLost = myYourGames.size() + myOpposingGames.size() - numWon - numDrew;
                mScoreSuffix = String.format(" (%d - %d - %d)", numWon, numLost, numDrew);
                setTitle(mTeam.getName() + mScoreSuffix);
            }
        });
    }

    public void btnSelectColor(View view) {
        openDialog(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_team_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void openDialog (boolean supportAlpha){
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, supportAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                GradientDrawable bg = (GradientDrawable) myTeamColor.getBackground();
                bg.setColor(color);
                Log.d("edit team", String.format("Color: %x", color));
                mTeam.setColor(color);
            }
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("EditTeamActivity", "Tapped back!");
                mTeamViewModel.update(mTeam);
                finish();
                return true;
            case R.id.edit_team_options_delete:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Are you sure?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Delete",
                        (dialog, id) -> {
                            dialog.cancel();
                            mTeamViewModel.delete(mTeam);
                            finish();
                        });
                builder1.setNegativeButton(
                        "Cancel",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
