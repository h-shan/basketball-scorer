package com.shan.howard.balltracker.datamodels;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

public class Converter {
    @TypeConverter
    public static Calendar timestampToCalendar(Long value) {
        if (value == null) {
            return null;
        }
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.setTimeInMillis(value);
        return myCalendar;
    }

    @TypeConverter
    public static Long calendarToTimestamp(Calendar aCalendar) {
        return aCalendar == null ? null : aCalendar.getTimeInMillis();
    }
}
