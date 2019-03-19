package com.shan.howard.balltracker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.viewmodels.GameViewModel;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ReviewGameActivity extends AppCompatActivity implements View.OnClickListener{

    Button mainMenu_btn;
    EditText search_et;
    ListView teams_lv;
    GameViewModel mGameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_game);

        mainMenu_btn = findViewById(R.id.mainMenuBtn);
        mainMenu_btn.setOnClickListener(this);
        search_et = findViewById(R.id.searchET);
        teams_lv = findViewById(R.id.view_teams_teams_lv);

        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        mGameViewModel.selectAllLive().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable final List<Game> aGames) {
                Log.d(TAG, "New games size: " + aGames.size());

            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.mainMenuBtn:
                Log.d(TAG, "hi");
                finish();
                break;
        }
    }

}
