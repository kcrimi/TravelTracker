package com.example.kevin.traveltracker;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created by kevin on 5/27/15.
 */
public class MemoriesDataSource {
    private DbHelper mDbHelper;

    public MemoriesDataSource(Context context){
        mDbHelper = DbHelper.getInstance(context);
    }

    public void createMemory(Memory memory){
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_LATITUDE, memory.latitude);
        values.put(DbHelper.COLUMN_LONGITUDE, memory.longitude);
        values.put(DbHelper.COLUMN_CITY, memory.city);
        values.put(DbHelper.COLUMN_COUNTRY, memory.country);
        values.put(DbHelper.COLUMN_NOTES, memory.notes);
        mDbHelper.getWritableDatabase().insert(DbHelper.MEMORIES_TABLE, null, values);
    }
}
