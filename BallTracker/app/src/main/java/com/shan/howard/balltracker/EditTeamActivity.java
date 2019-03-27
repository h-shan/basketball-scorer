package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

public class EditTeamActivity extends AppCompatActivity implements Button.OnClickListener {
    private Team mTeam;
    private Button mTeamButton;
    private Button mDeleteButton;
    private EditText mNameET;
    private EditText mNotesET;
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
}
