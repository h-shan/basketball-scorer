package com.shan.howard.balltracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.shan.howard.balltracker.viewmodels.TeamViewModel;

public class ViewPlayersActivity extends Activity implements Button.OnClickListener{
    private static final String TAG = MainActivity.class.getName();
    private ListView listV;
    private Button backButton;
    private ImageView newPlayerButton;
    private EditText search;
//    private ViewTeamsActivity.TeamListAdapter mAdapter;
//
//    private TeamViewModel mTeamViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_players);
        listV = findViewById(R.id.view_players_list_view);
    }

    @Override
    public void onClick(View view) {

    }
}
