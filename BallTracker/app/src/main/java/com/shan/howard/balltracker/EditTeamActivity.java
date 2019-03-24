package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
    private Button playerList;
    private Button myTeamColor;
    private EditText mNameET;
    private EditText mCoachET;
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

        playerList = findViewById(R.id.edit_team_player_list_btn);
        playerList.setOnClickListener(this);

        myTeamColor = findViewById(R.id.colorButton);
        GradientDrawable bg = (GradientDrawable) myTeamColor.getBackground();
        bg.setColor(mTeam.getColor());
        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel .class);

        mNameET = findViewById(R.id.edit_team_name_et);
        mCoachET = findViewById(R.id.edit_team_coach_et);
        mDeleteButton = findViewById(R.id.edit_team_delete_btn);
        mNameET.setText(mTeam.getName());
        mCoachET.setText(mTeam.getCoach());
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
        mCoachET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTeam.setCoach(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
            case R.id.edit_team_player_list_btn:
                Intent i = new Intent(EditTeamActivity.this, ViewPlayersActivity.class);
                i.putExtra("team", mTeam);
//                Log.d(MainActivity.class.getName(),"f");
                startActivity(i);
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

    private  void openDialog (boolean supportAlpha){
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
