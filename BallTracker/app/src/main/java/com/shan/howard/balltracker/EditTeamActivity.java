package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import yuku.ambilwarna.AmbilWarnaDialog;

public class EditTeamActivity extends AppCompatActivity implements Button.OnClickListener {
    private Team mTeam;
    private Button mTeamButton;
    private Button mDeleteButton;
    private EditText mNameET;
    private EditText mNotesET;
    private Button myTeamColor;
    private int currentColor;

    private TeamViewModel mTeamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        Intent myIntent = getIntent();
        mTeam = myIntent.getParcelableExtra("team");

        mTeamButton = findViewById(R.id.edit_team_back_btn);
        mTeamButton.setOnClickListener(this);
        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel .class);
        myTeamColor = findViewById(R.id.colorButton);
        GradientDrawable bg = (GradientDrawable) myTeamColor.getBackground();
        bg.setColor(mTeam.getColor());
        mNameET = findViewById(R.id.edit_team_name_et);
        mDeleteButton = findViewById(R.id.edit_team_delete_btn);
        mNameET.setText(mTeam.getName());
        mNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTeam.setName(charSequence.toString());
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
        mDeleteButton.setOnClickListener(this);

        Toolbar myToolbar = findViewById(R.id.edit_team_tb);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_team_back_btn:
                mTeamViewModel.update(mTeam);
                finish();
                break;
            case R.id.edit_team_delete_btn:
                mTeamViewModel.delete(mTeam);
                finish();
                break;
            default:
                break;
        }
    }

    public void btnSelectColor(View view) {
        openDialog(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnClickListener(v -> {
            Log.d("EditTeamActivity", "Search Pressed!");
        });

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
}
