package com.shan.howard.balltracker;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Player;
import com.shan.howard.balltracker.viewmodels.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewPlayersActivity extends AppCompatActivity implements Button.OnClickListener{
    private static final String TAG = MainActivity.class.getName();
    private ListView listV;
    private Button backButton;
    private ImageView newPlayerButton;
    private EditText search;
    private PlayerListAdapter mAdapter;

    private PlayerViewModel mPlayerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_players);

        mAdapter = new PlayerListAdapter(ViewPlayersActivity.this);
        listV = findViewById(R.id.view_players_list_view);
        listV.setAdapter(mAdapter);

        backButton = findViewById(R.id.view_players_back_btn);
        backButton.setOnClickListener(this);

        newPlayerButton = findViewById(R.id.view_players_new_player_btn);
        newPlayerButton.setOnClickListener(this);

        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mPlayerViewModel.selectAllLive().observe(this, Players -> {
            mAdapter.setPlayers(Players);
            mAdapter.notifyDataSetChanged();
        });

        search = findViewById(R.id.searchEditText);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("edittext", charSequence.toString());
                mAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_players_new_player_btn:
                mPlayerViewModel.insert(new Player());
                Log.d(TAG, "New player!");
                break;
            case R.id.view_players_back_btn:
                finish();
                break;
            default:
                break;
        }
    }
    class PlayerListAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private List<Player> mPlayers = new ArrayList<>();
        private List<Player> mDisplayedPlayers;

        PlayerListAdapter(Context context){
            mLayoutInflater =LayoutInflater.from(context);
            mDisplayedPlayers = mPlayers;
        }

        public void setPlayers(List<Player> newPlayers){
            this.mPlayers = newPlayers;
            mDisplayedPlayers = mPlayers;
        }

        @Override
        public int getCount() {
            return mDisplayedPlayers.size();
        }

        @Override
        public Object getItem(int i) {
            return mPlayers.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = mLayoutInflater.inflate(R.layout.view_teams_item, viewGroup, false);
            TextView nameTV = view.findViewById(R.id.view_teams_name_tv);
            Player myPlayer = mDisplayedPlayers.get(i);
            nameTV.setText(myPlayer.getName());

            view.setOnClickListener(v -> {

//                    Intent myIntent = new Intent(ViewPlayersActivity.this, EditPlayerActivity.class);
//                    myIntent.putExtra("team", mDisplayedPlayers.get(i));
//                    startActivity(myIntent);
            });
            return view;
        }
        Filter getFilter(){
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    return null;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                }
            };
        }
    }
}
