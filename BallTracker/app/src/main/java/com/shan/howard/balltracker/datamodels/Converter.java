package com.shan.howard.balltracker.datamodels;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

public class Converter {
    @TypeConverter
    public static Calendar timestampToCalendar(long value) {
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.setTimeInMillis(value);
        return myCalendar;
    }

    @TypeConverter
    public static long calendarToTimestamp(Calendar aCalendar) {
        return aCalendar.getTimeInMillis();
    }
}
