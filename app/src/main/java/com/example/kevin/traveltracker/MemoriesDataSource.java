package com.example.kevin.traveltracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 5/27/15.
 */
public class MemoriesDataSource {
    private DbHelper mDbHelper;
    private String[] allColumns = {
            DbHelper.COLUMN_ID, DbHelper.COLUMN_LATITUDE, DbHelper.COLUMN_LONGITUDE,
            DbHelper.COLUMN_CITY, DbHelper.COLUMN_COUNTRY,DbHelper.COLUMN_NOTES
    };

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

    public List<Memory> getAllMemories(){
        List<Memory> memories = new ArrayList<>();
        Cursor cursor = mDbHelper.getReadableDatabase().query(DbHelper.MEMORIES_TABLE, allColumns, null, null, null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Memory memory = cursorToMemory(cursor);
            memories.add(memory);
            cursor.moveToNext();
        }
        cursor.close();
        return memories;
    }

    private Memory cursorToMemory(Cursor cursor){
        Memory memory = new Memory();
        memory.latitude = cursor.getDouble(1);
        memory.longitude = cursor.getDouble(2);
        memory.city = cursor.getString(3);
        memory.country = cursor.getString(4);
        memory.notes = cursor.getString(5);
        return memory;
    }
}
