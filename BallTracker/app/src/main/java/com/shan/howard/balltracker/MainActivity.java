package com.shan.howard.balltracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements Button.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    Button theNewGameButton;
    Button theTeamsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theNewGameButton = findViewById(R.id.main_new_game_btn);
        theNewGameButton.setOnClickListener(this);
        theTeamsButton = findViewById(R.id.main_teams_btn);
        theTeamsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_new_game_btn:
                Log.d(TAG, "Moving to New Game Activity");
                Intent myIntent = new Intent(this, NewGameActivity.class);
                startActivity(myIntent);
                break;
            case R.id.main_teams_btn:
                Log.d(TAG, "Moving to View Teams Activity");
                Intent myIntent1 = new Intent(this, EditTeamActivity.class);
                startActivity(myIntent1);
                break;
            default:
                break;
        }
    }
}
