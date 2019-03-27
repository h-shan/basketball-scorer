package com.shan.howard.balltracker;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

import static com.shan.howard.balltracker.datamodels.Event.FOUL;
import static com.shan.howard.balltracker.datamodels.Event.FREE_THROW;
import static com.shan.howard.balltracker.datamodels.Event.THREE_POINTER;
import static com.shan.howard.balltracker.datamodels.Event.TWO_POINTER;

public class Utils {
    private static final Map<String, String> EVENT_TYPE_TO_ACTION = new HashMap<>();

    static {
        EVENT_TYPE_TO_ACTION.put(FOUL, "fouled.");
        EVENT_TYPE_TO_ACTION.put(FREE_THROW, "scored a free throw.");
        EVENT_TYPE_TO_ACTION.put(TWO_POINTER, "made a 2-point shot.");
        EVENT_TYPE_TO_ACTION.put(THREE_POINTER, "made a 3-point shot.");
    }

    public static void initializeSpinner(Context aContext, Spinner aSpinner, String[] anItems) {
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(aContext,
                android.R.layout.simple_spinner_item, anItems);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aSpinner.setAdapter(myAdapter);
    }

    public static String convertEventToLog(String aTeamName, String anEvent) {
        return String.format("%s %s", aTeamName, EVENT_TYPE_TO_ACTION.get(anEvent));
    }
}
