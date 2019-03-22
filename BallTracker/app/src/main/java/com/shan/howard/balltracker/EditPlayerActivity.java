package com.shan.howard.balltracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shan.howard.balltracker.datamodels.Player;
import com.shan.howard.balltracker.viewmodels.PlayerViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

public class EditPlayerActivity extends AppCompatActivity implements Button.OnClickListener {
    private Player mTeam;
    private Button mTeamButton;
    private Button mDeleteButton;
    private EditText mNameET;
    private EditText mTeamET;
    private PlayerViewModel mPlayerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);
    }

    @Override
    public void onClick(View view) {

    }
}
