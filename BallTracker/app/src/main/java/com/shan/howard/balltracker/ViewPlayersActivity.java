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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shan.howard.balltracker.datamodels.Player;
import com.shan.howard.balltracker.viewmodels.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewPlayersActivity extends AppCompatActivity implements Button.OnClickListener{
    private static final String TAG = MainActivity.class.getName();
    private ListView mLv;
    private Button mBackButton;
    private ImageView mNewPlayerButton;
    private EditText mETsearch;
    private PlayerListAdapter mAdapter;

    private PlayerViewModel mPlayerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_players);

        mAdapter = new PlayerListAdapter(ViewPlayersActivity.this);
        mLv = findViewById(R.id.view_players_players_view);
        mLv.setAdapter(mAdapter);

        mBackButton = findViewById(R.id.view_players_back_btn);
        mBackButton.setOnClickListener(this);
        mETsearch = findViewById(R.id.searchET);
        mETsearch.addTextChangedListener(new TextWatcher() {
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

        mNewPlayerButton = findViewById(R.id.view_players_new_player_btn);
        mNewPlayerButton.setOnClickListener(this);

        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mPlayerViewModel.selectAllLive().observe(this, aPlayers -> {
            Log.d(TAG, Integer.toString(aPlayers.size()));
            mAdapter.setPlayers(aPlayers);
            mAdapter.notifyDataSetChanged();
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

    public class PlayerListAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private List<Player> mPlayers = new ArrayList<Player>();
        private List<Player> mDisplayedPlayers;

        PlayerListAdapter(Context context){
            mLayoutInflater = LayoutInflater.from(context);
//            mPlayers.add(new Player());
            mDisplayedPlayers = mPlayers;
//            Log.d(TAG, Integer.toString(mPlayers.size()));
        }

        public void setPlayers(List<Player> newPlayers){
            mPlayers = newPlayers;
            mDisplayedPlayers = mPlayers;
//            Log.d(TAG, "131231215");
//            Log.d(TAG, Integer.toString(mPlayers.size()));

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = mLayoutInflater.inflate(R.layout.view_players_item, parent, false);
            TextView nameTV = convertView.findViewById(R.id.view_players_name_tv);
            Player myPlayer = mDisplayedPlayers.get(position);
            nameTV.setText(myPlayer.getName());
            Log.d(TAG, "sfgesgsgsjglsfghsh");
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(ViewPlayersActivity.this, EditPlayerActivity.class);
                    myIntent.putExtra("player", mDisplayedPlayers.get(position));
                    startActivity(myIntent);
                }
            });
            return convertView;
        }

        Filter getFilter(){
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    ArrayList<Player> FilteredArrList = new ArrayList<Player>();
                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = mPlayers.size();
                        results.values = mPlayers;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < mPlayers.size(); i++) {
                            String data = mPlayers.get(i).getName();
                            if (data.toLowerCase().contains(charSequence)) {
                                FilteredArrList.add(mPlayers.get(i));
                            }
                        }
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mDisplayedPlayers = (List<Player>) filterResults.values;
                    Log.d(TAG, "hello");
                    notifyDataSetChanged();
                }
            };
        }
    }
}
