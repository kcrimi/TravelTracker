package com.example.kevin.traveltracker;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.kevin.traveltracker.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

/**
 * Created by kevin on 5/26/15.
 */
public class MarkerAdapter implements GoogleMap.InfoWindowAdapter{

    private LayoutInflater mLayoutInflater;
    private View mView;
    private HashMap<String, Memory> mMemories;

    MarkerAdapter(LayoutInflater layoutInflater, HashMap<String, Memory> memories){
        mLayoutInflater = layoutInflater;
        mMemories = memories;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (mView == null){
            mView = mLayoutInflater.inflate(R.layout.marker, null);
        }
        Memory memory = mMemories.get(marker.getId());

        TextView titleView = (TextView)mView.findViewById(R.id.title);
        titleView.setText(memory.city);
        TextView snippetView = (TextView)mView.findViewById(R.id.snippet);
        snippetView.setText(memory.country);
        TextView notesView = (TextView)mView.findViewById(R.id.notes);
        notesView.setText(memory.notes);

        return mView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
}
