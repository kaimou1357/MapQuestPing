package com.example.kaimou.mapquestping;

import android.os.Handler;
import android.util.Log;

import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.framed.FrameReader;

/**
 * Created by kaimou on 9/11/16.
 */
public class RestAPI {
    private static final String KEY = "xDMwogfLcNYv3sxsy3uTCbrPJVY7GCax";
    private static final String DIRECTION_ENDPOINT = "https://www.mapquestapi.com/directions/v2/route?key=" + KEY + "&from=pier+48+&to=850+bryant+street+san+francisco&outFormat=json&ambiguities=ignore&routeType=fastest&doReverseGeocode=false&enhancedNarrative=false&avoidTimedConditions=false";
    OkHttpClient client;
    MainActivity activity;
    public RestAPI(MainActivity activity){
        this.activity = activity;
        client = new OkHttpClient();

    }

    public ArrayList<LatLng> returnCoordinates(){

        Request request = new Request.Builder()
                .url(DIRECTION_ENDPOINT)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Failed http call", "lol");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ArrayList<LatLng> results = new ArrayList<LatLng>();
                try{
                    JSONObject route = new JSONObject(response.body().string());
                    JSONArray maneuvers = new JSONArray(route.getJSONObject("route").getJSONArray("legs").getJSONObject(0).getJSONArray("maneuvers"));

                    for(int i = 0; i<maneuvers.length(); i++){
                       JSONObject temp = maneuvers.getJSONObject(i);
                        results.add(new LatLng(temp.getJSONObject("startPoint").getDouble("lng"), temp.getJSONObject("startPoint").getDouble("lat")));
                     }




                }
                catch(IOException | JSONException e){
                    e.printStackTrace();
                    Log.d("HTTP", "Problem with HTTP CALL");
                }

                activity.directions = results;

            }
        });


        return null;

    }



}
