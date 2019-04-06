package com.shan.howard.balltracker;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Event;
import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.viewmodels.EventViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shan.howard.balltracker.datamodels.Event.FOUL;
import static com.shan.howard.balltracker.datamodels.Event.FREE_THROW;
import static com.shan.howard.balltracker.datamodels.Event.THREE_POINTER;
import static com.shan.howard.balltracker.datamodels.Event.TWO_POINTER;
import static java.lang.Math.round;

public class TrackGameActivity extends AppCompatActivity implements Button.OnClickListener {
    public static final String GAME = "GAME";
    public static final String YOUR_TEAM = "YOUR_TEAM";
    public static final String OPPOSING_TEAM = "OPPOSING_TEAM";
    private static final String[] QUARTERS = {"Q1", "Q2", "Q3", "Q4", "OT1", "OT2"};

    private Spinner mQuarterSpinner;
    private RecyclerView mLogRecyclerView;
    private MyAdapter mAdapter;
    private Game mGame;
    private Team mYourTeam, mOpposingTeam;
    private int mCurrentQuarter = 1;

    private long mCurrentTeamId = -1;
    private String mCurrentEventType = null;

    private EventViewModel mEventViewModel;
    private LiveData<List<Event>> mEventsLiveData;
    private List<Event> mEvents;

    private TextView mYourTeamNameTV, mOpposingTeamNameTV;
    private TextView mYourTeamScoreTV, mOpposingTeamScoreTV;
    private Button mYourTeamButton, mOpposingTeamButton;
    private Button mFreeThrowButton, mFoulButton, mTwoPointerButton, mThreePointerButton;

    public boolean isColorDark(int color){
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        if(darkness<0.5){
            return false; // It's a light color
        }else{
            return true; // It's a dark color
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGame = getIntent().getParcelableExtra("GAME");
        mYourTeam = getIntent().getParcelableExtra("YOUR_TEAM");
        mOpposingTeam = getIntent().getParcelableExtra("OPPOSING_TEAM");

        setContentView(R.layout.activity_track_game);

        mQuarterSpinner = findViewById(R.id.track_game_quarter_spinner);
        Utils.initializeSpinner(this, mQuarterSpinner, QUARTERS);
        mQuarterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentQuarter = position + 1;
                setUpLogListener();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mLogRecyclerView = findViewById(R.id.recyclerView);
        mLogRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mLogRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter();
        mLogRecyclerView.setAdapter(mAdapter);

        mYourTeamNameTV = findViewById(R.id.track_game_your_team_name_tv);
        mOpposingTeamNameTV = findViewById(R.id.track_game_opposing_team_name_tv);
        mYourTeamScoreTV = findViewById(R.id.track_game_your_team_score_tv);
        mOpposingTeamScoreTV = findViewById(R.id.track_game_opposing_team_score_tv);
        mYourTeamButton = findViewById(R.id.track_game_your_team_btn);
        mOpposingTeamButton = findViewById(R.id.track_game_opposing_team_btn);
        mFreeThrowButton = findViewById(R.id.track_game_free_throw_btn);
        mFoulButton = findViewById(R.id.track_game_foul_btn);
        mTwoPointerButton = findViewById(R.id.track_game_two_pointer_btn);
        mThreePointerButton = findViewById(R.id.track_game_three_pointer_btn);

        mYourTeamButton.setText(mYourTeam.getName());
        mOpposingTeamButton.setText(mOpposingTeam.getName());
        GradientDrawable ydrawable = (GradientDrawable)mYourTeamButton.getBackground();
        ydrawable.setColor(mYourTeam.getColor());
        GradientDrawable odrawable = (GradientDrawable)mOpposingTeamButton.getBackground();
        odrawable.setColor(mOpposingTeam.getColor());
        if(isColorDark(mYourTeam.getColor()))
            mYourTeamButton.setTextColor(0xffffffff);
        if(isColorDark(mOpposingTeam.getColor()))
            mOpposingTeamButton.setTextColor(0xffffffff);

        mYourTeamButton.setOnClickListener(this);
        mOpposingTeamButton.setOnClickListener(this);
        mFreeThrowButton.setOnClickListener(this);
        mFoulButton.setOnClickListener(this);
        mTwoPointerButton.setOnClickListener(this);
        mThreePointerButton.setOnClickListener(this);

        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        mYourTeamNameTV.setText(mYourTeam.getName());
        mOpposingTeamNameTV.setText(mOpposingTeam.getName());
        mEventsLiveData = mEventViewModel.selectByGameId(mGame.getId());
        setUpLogListener();

        Toolbar myToolbar = findViewById(R.id.track_game_tb);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }

        unselectEventTypeButtons();
    }

    private void setUpLogListener() {
        mEventsLiveData.removeObservers(this);
        mEventsLiveData.observe(this, events -> {
            mEvents = events;
            Collections.sort(events);
            mYourTeamScoreTV.setText(Integer.toString(getTeamScore(events, mYourTeam.getId())));
            mOpposingTeamScoreTV.setText(Integer.toString(getTeamScore(events, mOpposingTeam.getId())));
            mAdapter.setEventLogs(events.stream()
                    .filter(anEvent -> anEvent.getQuarter() == mCurrentQuarter)
                    .sorted()
                    .map(anEvent -> {
                        String myTeamName = anEvent.getTeamId() == mYourTeam.getId() ? mYourTeam.getName() : mOpposingTeam.getName();
                        return Utils.convertEventToLog(myTeamName, anEvent.getEventType());
                    }).collect(Collectors.toList()));
            mAdapter.notifyDataSetChanged();
            mLogRecyclerView.post(() -> {
                if (mAdapter.getItemCount() > 0) {
                    mLogRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                }
            });
        });
    }

    private int getTeamScore(List<Event> anEvents, long aTeamId) {
        Optional<Integer> myScore = anEvents.stream().filter(anEvent -> anEvent.getTeamId() == aTeamId).map(x -> Utils.getEventValue(x.getEventType())).reduce(Integer::sum);
        if (myScore.isPresent()) {
            return myScore.get();
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.track_game_foul_btn:
                processEventTypeClicked(v, FOUL);
                break;
            case R.id.track_game_free_throw_btn:
                processEventTypeClicked(v, FREE_THROW);
                break;
            case R.id.track_game_two_pointer_btn:
                processEventTypeClicked(v, TWO_POINTER);
                break;
            case R.id.track_game_three_pointer_btn:
                processEventTypeClicked(v, THREE_POINTER);
                break;
            case R.id.track_game_opposing_team_btn:
                processTeamClicked(v, mOpposingTeam.getId());
                break;
            case R.id.track_game_your_team_btn:
                processTeamClicked(v, mYourTeam.getId());
                break;
//            case R.id.track_game_finish_btn:
//                Intent myIntent = new Intent(this, ReviewSpecificGameActivity.class);
//                myIntent.putExtra(GAME, mGame);
//                startActivity(myIntent);
              //  break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.track_game_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.track_game_options_finish:
                Intent myIntent = new Intent(this, ReviewGameActivity.class);
                startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<String> mEventLogs = new ArrayList<>();

        class MyViewHolder extends RecyclerView.ViewHolder {
            View mView;
            TextView mLogTextView;
            Button mDeleteLogButton;

            MyViewHolder(View aView) {
                super(aView);
                mView = aView;
                mLogTextView = aView.findViewById(R.id.log_list_item_text);
                mDeleteLogButton = aView.findViewById(R.id.delete_log);
            }
        }

        public void setEventLogs(List<String> mEventLogs) {
            this.mEventLogs = mEventLogs;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.log_list_item, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mLogTextView.setText(mEventLogs.get(position));
            holder.mDeleteLogButton.setOnClickListener(v -> {
                mEventViewModel.delete(mEvents.get(position));
                this.notifyDataSetChanged();
            });

            int teamColor;
            if(mEvents.get(position).getTeamId() == mYourTeam.getId()) {
                teamColor = mYourTeam.getColor();
                holder.mView.setBackgroundColor(teamColor);
//                System.out.println(teamColor);
            } else {
                teamColor = mOpposingTeam.getColor();
                holder.mView.setBackgroundColor(teamColor);
//                System.out.println(teamColor);
            }

            if(isColorDark(teamColor))
                holder.mLogTextView.setTextColor(0xffffffff);
        }

        @Override
        public int getItemCount() {
            return mEventLogs.size();
        }
    }


    private Event createEvent(String anEventType, long aTeamId) {
        Event myEvent = new Event();
        myEvent.setEventType(anEventType);
        myEvent.setGameId(mGame.getId());
        myEvent.setQuarter(mCurrentQuarter);
        myEvent.setTeamId(aTeamId);
        return myEvent;
    }

    private void resetSelection() {
        mCurrentTeamId = -1;
        mCurrentEventType = null;
        unselectEventTypeButtons();
        unselectTeamButtons();
    }

    private void unselectTeamButtons() {
        mCurrentTeamId = -1;
        unhighlightTeamButton1(mYourTeamButton);
        unhighlightTeamButton2(mOpposingTeamButton);
    }

    private void unselectEventTypeButtons() {
        unhighlightEventTypeButton(mFoulButton);
        unhighlightEventTypeButton(mFreeThrowButton);
        unhighlightEventTypeButton(mTwoPointerButton);
        unhighlightEventTypeButton(mThreePointerButton);
    }

    private void processEventTypeClicked(View aView, String anEventType) {
        unselectEventTypeButtons();
        if (mCurrentTeamId != -1) {
            mEventViewModel.insert(createEvent(anEventType, mCurrentTeamId));
            resetSelection();
        } else {
            unselectEventTypeButtons();
            if (!anEventType.equals(mCurrentEventType)) {
                mCurrentEventType = anEventType;
                highlightEventTypeButton(aView);
            } else {
                mCurrentEventType = null;
            }
        }
    }

    private void processTeamClicked(View aView, long aTeamId) {
        if (mCurrentEventType != null) {
            mEventViewModel.insert(createEvent(mCurrentEventType, aTeamId));
            resetSelection();
        } else {
            unselectTeamButtons();
            if (aTeamId != mCurrentTeamId) {
                mCurrentTeamId = aTeamId;
                highlightTeamButton(aView);
            } else {
                mCurrentTeamId = -1;
            }
        }
    }



    private void highlightEventTypeButton(View aView) {
        switch (aView.getId()) {
            case R.id.track_game_three_pointer_btn:
            case R.id.track_game_two_pointer_btn:
            case R.id.track_game_free_throw_btn:
                aView.setBackgroundResource(R.drawable.green_solid);
                break;
            case R.id.track_game_foul_btn:
                aView.setBackgroundResource(R.drawable.yellow_solid);
                break;
        }
    }

    private void unhighlightEventTypeButton(View aView) {
        GradientDrawable sd = new GradientDrawable();
        sd.setShape(GradientDrawable.RECTANGLE);
        GradientDrawable drawable = (GradientDrawable)aView.getBackground();
        int color = drawable.getColor().getDefaultColor();

        color = (color & 0x00FFFFFF) | 0x2F000000;
        sd.setColor(color);
        sd.setStroke(10,Color.BLACK);
        aView.setBackground(sd);
    }

    private void highlightTeamButton(View aView) {
        aView.setBackgroundResource(R.drawable.bg_circle_selected);
    }

    private void unhighlightTeamButton1(View aView) {
        aView.setBackgroundResource(R.drawable.bg_circle_bt2);
    }

    private void unhighlightTeamButton2(View aView) {
        aView.setBackgroundResource(R.drawable.bg_circle_bt3);
    }


}
