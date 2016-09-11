package com.example.kaimou.mapquestping;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapquest.mapping.maps.MapView;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MapboxMap mMapboxMap;
    private MapView mMapView;
    public ArrayList<LatLng> directions = new ArrayList<LatLng>();

    private final com.mapbox.mapboxsdk.geometry.LatLng TECHCRUNCH = new com.mapbox.mapboxsdk.geometry.LatLng(37.775889, -122.386657);
    private final com.mapbox.mapboxsdk.geometry.LatLng POLICE_STATION = new com.mapbox.mapboxsdk.geometry.LatLng(37.775024, -122.404272);
    RestAPI client = new RestAPI(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        client.returnCoordinates();
        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        Log.d("Length of List", Integer.toString(directions.size()));
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                //zoom in on correct area of map.
                mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new com.mapbox.mapboxsdk.geometry.LatLng(37.772043, -122.398978), 13));
                addMarker(mMapboxMap);
                addPoliceMarker(mMapboxMap);
               // setPolygon(mMapboxMap);
                setPolyline(mMapboxMap);
            }

        });
    }

    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(TECHCRUNCH);
        markerOptions.title("Pier 48");
        markerOptions.snippet("TechCrunch Rocks!");
        mapboxMap.addMarker(markerOptions);
    }
    private void setPolyline(MapboxMap mapboxMap) {
        List<com.mapbox.mapboxsdk.geometry.LatLng> coordinates = new ArrayList<>();
        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(37.776060, -122.387055));
        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(37.776351, -122.387817));
        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(37.776279, -122.389945));
        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(37.77885, -122.392761));
        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(37.771808, -122.401672));
        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(37.77428, -122.404762));
        coordinates.add(new com.mapbox.mapboxsdk.geometry.LatLng(37.775066, -122.403747));



        PolylineOptions polyline = new PolylineOptions();
        polyline.addAll(coordinates);
        polyline.width(3);
        polyline.color(Color.BLUE);
        mapboxMap.addPolyline(polyline);
    }


    private void addPoliceMarker(MapboxMap mapboxMap){
        MarkerOptions marker = new MarkerOptions();
        marker.position(POLICE_STATION);
        marker.title("San Francisco Police Station");
        marker.snippet("Get Here Quickly!");
        mapboxMap.addMarker(marker);
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
}
