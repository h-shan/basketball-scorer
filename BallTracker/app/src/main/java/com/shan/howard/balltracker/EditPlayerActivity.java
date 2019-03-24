package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shan.howard.balltracker.datamodels.Player;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.PlayerViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

public class EditPlayerActivity extends AppCompatActivity implements Button.OnClickListener {
    private Player mPlayer;
//    private Team mTeam;
    private Button mPlayerBackButton;
    private Button mDeleteButton;
    private EditText mNameET;
    private EditText mTeamET;
    private PlayerViewModel mPlayerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);

        Intent myIntent = getIntent();
        mPlayer = myIntent.getParcelableExtra("player");
//        mTeam = myIntent.getParcelableExtra("team");

        mPlayerBackButton = findViewById(R.id.edit_player_back_btn);
        mPlayerBackButton.setOnClickListener(this);

        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mNameET = findViewById(R.id.edit_player_name_et);
        mTeamET = findViewById(R.id.edit_player_team_name_et);
        mNameET.setText(mPlayer.getName());
//        mTeamET.setText(mTeam.getName());

        mNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPlayer.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDeleteButton = findViewById(R.id.edit_player_delete_btn2);
        mDeleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_player_back_btn:
                mPlayerViewModel.update(mPlayer);
                finish();
                break;
            case R.id.edit_player_delete_btn2:
                mPlayerViewModel.delete(mPlayer);
                finish();
                break;
            default:
                break;
        }
    }
}
