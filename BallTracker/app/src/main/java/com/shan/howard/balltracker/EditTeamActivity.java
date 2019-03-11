package com.shan.howard.balltracker;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditTeamActivity extends Activity implements Button.OnClickListener {

    private Button mTeamButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        mTeamButton = findViewById(R.id.view_teams_back_btn);
        mTeamButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_team_back_btn:
                finish();
                break;
            case R.id.edit_team_player_list_btn:
                finish();
                break;
            default:
                break;
        }
    }
}
