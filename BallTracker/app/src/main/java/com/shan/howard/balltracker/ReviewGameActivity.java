package com.shan.howard.balltracker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import static android.content.ContentValues.TAG;

public class ReviewGameActivity extends Activity implements View.OnClickListener{

    Button mainMenu_btn;
    EditText search_et;
    ListView teams_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_game);

        mainMenu_btn = findViewById(R.id.mainMenuBtn);
        mainMenu_btn.setOnClickListener(this);
        search_et = findViewById(R.id.searchET);
        teams_lv = findViewById(R.id.teamsLV);
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
