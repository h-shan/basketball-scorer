package com.shan.howard.balltracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.viewmodels.GameViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ReviewGameActivity extends AppCompatActivity implements View.OnClickListener{

    Button mainMenu_btn;
    EditText search_et;
    ListView games_lv;

    GameViewModel mGameViewModel;
    GameListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_game);

        // Games List View and Adapter
        mAdapter = new GameListAdapter(ReviewGameActivity.this);
        games_lv = findViewById(R.id.viewGamesLv);
        games_lv.setAdapter(mAdapter);

        // Main Menu Button
        mainMenu_btn = findViewById(R.id.mainMenuBtn);
        mainMenu_btn.setOnClickListener(this);

        // Search Game Edit Text
        search_et = findViewById(R.id.searchET);
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Game View Model
        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        mGameViewModel.selectAllLive().observe(this, aGames -> {
            mAdapter.setGames(aGames);
            mAdapter.notifyDataSetChanged();
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

    class GameListAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private List<Game> mGames = new ArrayList<>();
        private List<Game> mDisplayedGames;

        GameListAdapter(Context context){
            mLayoutInflater = LayoutInflater.from(context);
            mDisplayedGames = mGames;
        }

        public void setGames(List<Game> aGames){
            this.mGames = aGames;
            mDisplayedGames = mGames;
        }

        @Override
        public int getCount(){return mDisplayedGames.size();}

        @Override
        public Object getItem(int i){return mGames.get(i);}

        @Override
        public long getItemId(int i){return i;}

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){

            // TODO Set text and get game

            // User inputs game ID
            convertView = mLayoutInflater.inflate(R.layout.view_games_item, parent, false);
            TextView idTV = convertView.findViewById(R.id.id_tv);
            TextView dateTV = convertView.findViewById(R.id.date_tv);
            TextView teamsTV = convertView.findViewById(R.id.teams_tv);

            Game myGame = mDisplayedGames.get(position);
            idTV.setText(Long.toString(myGame.getId()));
            dateTV.setText(myGame.getDate().toString());

            String yourTeam = Long.toString(myGame.getYourTeamId());
            String opposingTeam = Long.toString(myGame.getOpposingTeamId());
            String vs = yourTeam + " vs " + opposingTeam;
            teamsTV.setText(vs);

            convertView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent myIntent = new Intent(ReviewGameActivity.this, ReviewSpecificGameActivity.class);
                    myIntent.putExtra("gameID", mDisplayedGames.get(position).getId());
                    startActivity(myIntent);
                }
            });

            return convertView;
        }

        Filter getFilter(){
            return new Filter(){
                @Override
                protected FilterResults performFiltering(CharSequence charSequence){
                    FilterResults results = new FilterResults();
                    ArrayList<Game> FilteredGameList = new ArrayList<>();

                    if(charSequence==null || charSequence.length()==0){
                        results.count = mGames.size();
                        results.values = mGames;
                    }
                    else{
                        charSequence = charSequence.toString().toLowerCase();

                        for(int i = 0; i < mGames.size(); i++){
                            String data = Long.toString(mGames.get(i).getId());
                            if(data.toLowerCase().contains(charSequence)){
                                FilteredGameList.add(mGames.get(i));
                            }
                        }

                        results.count = FilteredGameList.size();
                        results.values = FilteredGameList;
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults){
                    mDisplayedGames = (List<Game>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
    }
}
