package com.shan.howard.balltracker;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Utils {
    public static void initializeSpinner(Context aContext, Spinner aSpinner, String[] anItems) {
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(aContext,
                android.R.layout.simple_spinner_item, anItems);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aSpinner.setAdapter(myAdapter);
    }
}
