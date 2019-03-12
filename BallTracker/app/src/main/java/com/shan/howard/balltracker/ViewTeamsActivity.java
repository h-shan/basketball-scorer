package com.shan.howard.balltracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ViewTeamsActivity extends Activity implements Button.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private ListView mLv;
    private Button mBackButton;
    private EditText mEtsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teams);
        mLv = findViewById(R.id.lv_teams);
        mLv.setAdapter(new MyListAdapter(ViewTeamsActivity.this));

        mBackButton = findViewById(R.id.view_teams_back_btn);
        mBackButton.setOnClickListener(this);
        mEtsearch = findViewById(R.id.et_search);
        mEtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("edittext", charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_teams_back_btn:
                finish();
                break;
            default:
                break;
        }
    }
}
