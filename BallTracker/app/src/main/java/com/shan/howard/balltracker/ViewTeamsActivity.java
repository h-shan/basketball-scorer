package com.shan.howard.balltracker;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ViewTeamsActivity extends Activity {
    private ListView mLv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teams);
        mLv = findViewById(R.id.lv_teams);
        mLv.setAdapter(new MyListAdapter(ViewTeamsActivity.this));

    }
}
