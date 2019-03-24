package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.EventViewModel;
import com.shan.howard.balltracker.viewmodels.TeamViewModel;

import java.util.Map;

import static com.shan.howard.balltracker.datamodels.Event.FOUL;
import static com.shan.howard.balltracker.datamodels.Event.FREE_THROW;
import static com.shan.howard.balltracker.datamodels.Event.THREE_POINTER;
import static com.shan.howard.balltracker.datamodels.Event.TWO_POINTER;

public class TrackGameActivity extends AppCompatActivity implements Button.OnClickListener {
    private static final String[] QUARTERS = { "Q1", "Q2", "Q3", "Q4" };
    private static final String[] PLACEHOLDER_EVENTS = { "Player 1 scored 2 points.", "Player 2 scored 3 points." };

    private Spinner mQuarterSpinner;
    private RecyclerView mLogRecyclerView;
    private MyAdapter mAdapter;
    private Game mGame;
    private Team mYourTeam, mOpposingTeam;

    private EventViewModel mEventViewModel;
    private TeamViewModel mTeamViewModel;

    private Map<Long, Team> theTeamCache;

    private TextView mYourTeamNameTV, mOpposingTeamNameTV;
    private TextView mYourTeamScoreTV, mOpposingTeamScoreTV;
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_game);

        mQuarterSpinner = findViewById(R.id.track_game_quarter_spinner);
        Utils.initializeSpinner(this, mQuarterSpinner, QUARTERS);

        mLogRecyclerView = findViewById(R.id.recyclerView);
        mLogRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mLogRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(PLACEHOLDER_EVENTS);
        mLogRecyclerView.setAdapter(mAdapter);


        mYourTeamNameTV = findViewById(R.id.track_game_your_team_name_tv);
        mOpposingTeamNameTV = findViewById(R.id.track_game_opposing_team_name_tv);
        mYourTeamScoreTV = findViewById(R.id.track_game_your_team_score_tv);
        mOpposingTeamScoreTV = findViewById(R.id.track_game_opposing_team_score_tv);
        mBackButton = findViewById(R.id.track_game_back_btn);
        mBackButton.setOnClickListener(this);

        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        mGame = getIntent().getParcelableExtra("GAME");

        mYourTeam = getIntent().getParcelableExtra("YOUR_TEAM");
        mOpposingTeam = getIntent().getParcelableExtra("OPPOSING_TEAM");
        mYourTeamNameTV.setText(mYourTeam.getName());
        mOpposingTeamNameTV.setText(mOpposingTeam.getName());

        mEventViewModel.selectByGameAndTeamId(mGame.getId(), mGame.getYourTeamId()).observe(this, events -> {

        });

        mEventViewModel.selectByGameId(mGame.getId()).observe(this, events -> {
            events.stream().map(anEvent -> {
                StringBuilder myBuilder = new StringBuilder();
                switch (anEvent.getEventType()) {
                   case THREE_POINTER:
                       break;
                   case TWO_POINTER:
                   case FOUL:
                   case FREE_THROW:
                       break;
               }
               return myBuilder.toString();
            });
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.track_game_back_btn:
                finish();
                break;
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private String[] mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            View mView;
            TextView mLogTextView;
            MyViewHolder(View aView) {
                super(aView);
                mView = aView;
                mLogTextView = aView.findViewById(R.id.log_list_item_text);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.log_list_item, parent, false);


            return new MyViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mLogTextView.setText(mDataset[position]);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }

}
