package com.example.kevin.traveltracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;


public class MainActivity extends ActionBarActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener,MemoryDialogFragment.Listener,
        GoogleMap.OnMarkerDragListener,GoogleMap.OnInfoWindowClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "Main Activity";
    private static final String MEMORY_DIALOG_TAG = "MemoryDialog";
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private HashMap<String, Memory> mMemories = new HashMap<>();
    private MemoriesDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mDataSource = new MemoriesDataSource(this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setInfoWindowAdapter(new MarkerAdapter(getLayoutInflater(), mMemories));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnInfoWindowClickListener(this);
        List<Memory> memories = mDataSource.getAllMemories();
        for ( Memory memory: memories) {
            addMarker(memory);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Memory memory = new Memory();

        updateMemoryPosition(latLng, memory);

        MemoryDialogFragment.newInstance(memory).show(getFragmentManager(), MEMORY_DIALOG_TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnCancelClicked(Memory memory) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Memory memory = mMemories.get(marker.getId());
        updateMemoryPosition(marker.getPosition(),memory);
        mDataSource.updateMemory(memory);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "window clicked");
    }

    private void updateMemoryPosition(LatLng latLng, Memory memory) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> matches = null;
        try {
            matches = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Address bestMatch = (matches.isEmpty()) ? null : matches.get(0);
        int maxLine = bestMatch.getMaxAddressLineIndex();

        memory.city = bestMatch.getAddressLine(maxLine - 1);
        memory.country = bestMatch.getAddressLine(maxLine);
        memory.latitude = latLng.latitude;
        memory.longitude = latLng.longitude;
        memory.notes = "I remember I met an old man here with a beard as long as a chair";
    }

    @Override
    public void OnSaveClicked(Memory memory) {
        addMarker(memory);
        mDataSource.createMemory(memory);
    }

    private void addMarker(Memory memory) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .draggable(true)
                .position(new LatLng(memory.latitude, memory.longitude)));

        mMemories.put(marker.getId(), memory);
    }

    private void addGoogleAPIClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mGoogleApiClient.connect();
    }
}
