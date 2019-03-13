package com.shan.howard.balltracker;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import yuku.ambilwarna.AmbilWarnaDialog;

public class EditTeamActivity extends Activity implements Button.OnClickListener {

    private Button mTeamButton;
    private Button myTeamColor;
    private  int currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        mTeamButton = findViewById(R.id.edit_team_back_btn);
        mTeamButton.setOnClickListener(this);

        myTeamColor = findViewById(R.id.colorButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_team_back_btn:
                GradientDrawable bg = (GradientDrawable) myTeamColor.getBackground();
                bg.setColor(getResources().getColor(R.color.colorSelector));
                finish();
                break;
            case R.id.edit_team_player_list_btn:
                finish();
                break;
            default:
                break;
        }
    }

    public void btnSelectColor(View view) {

        openDialog(false);
    }

    private  void openDialog (boolean supportAlpha){
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, supportAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                GradientDrawable bg = (GradientDrawable) myTeamColor.getBackground();
                bg.setColor(color);
            }
        });
        dialog.show();
    }
}
