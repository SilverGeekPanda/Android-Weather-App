package com.example.charlie.bladeprojectcharlie.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.example.charlie.bladeprojectcharlie.HttpRequest.HttpConfig;
import com.example.charlie.bladeprojectcharlie.HttpRequest.RequestBackground;
import com.example.charlie.bladeprojectcharlie.R;
import com.example.charlie.bladeprojectcharlie.Data.Weather;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;
    private Weather weather = Weather.getInstance();

    private boolean mLocationPermissionGranted = false;
    private static final int REQUEST_PERMISSIONS_FINE_LOCATION = 100;

    protected Location mLastLocation;

    private static final String WEATHER_API_KEY = "bad323a72bc8491283f130909181404";
    private final Handler handler = new Handler();
    private HttpConfig httpConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();

        if(!mLocationPermissionGranted) {
            Log.d("DEBUG1","Requesting Permissions");
            requestLocationPermission();
            getLastLocation();
        } else {
            Log.d("DEBUG1","Premission has been granting: Getting Last Location");
            getLastLocation();
        }

        getActualLocalWeather();
    }



    private void getActualLocalWeather() {


        Log.d("REQUEST1","URL construction...");

        String url = "http://api.worldweatheronline.com/premium/v1/weather.ashx?key="+
                     WEATHER_API_KEY;
        httpConfig = new HttpConfig(url);
        httpConfig.addParameter("&q="+weather.getLatitude()+","+weather.getLongitude());

        Log.d("REQUEST1","Queue Construction");
        httpConfig.setQueue(Volley.newRequestQueue(this.getApplicationContext()));

        Log.d("REQUEST1","URL constructed: " + httpConfig.getUrl());
        Log.d("REQUEST1","Launching Request...");

        handler.post(new Runnable() {
            @Override
            public void run() {
                RequestBackground request = new RequestBackground();
                request.execute(httpConfig);
            }
        });

        Log.d("REQUEST1","Response="+httpConfig.getResponse());
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isSuccessful() && task.getResult() != null)
                        {
                            mLastLocation = task.getResult();
                            weather.setLatitude(mLastLocation.getLatitude());
                            weather.setLongitude(mLastLocation.getLongitude());
                            Log.d("DEBUG1","Latitude: " + weather.getLatitude());
                            Log.d("DEBUG1", "Longitude: " + weather.getLongitude());
                        }
                        else
                        {
                            Log.d("DEBUG1", "Need the authorization");
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean  requestLocationPermission() {
         mLocationPermissionGranted = false;

        //Check if the location permission is already available
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Location permission is already available
            mLocationPermissionGranted = true;
        }
        else {
            //Location permission has not been granted
            //Explain to the user why the permissions is necessary
            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this, "Location permission is needed to get the best weather information.",
                                Toast.LENGTH_SHORT).show();
            }

            //Request Location Permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                               REQUEST_PERMISSIONS_FINE_LOCATION);
        }

        return mLocationPermissionGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_PERMISSIONS_FINE_LOCATION) {
            //Received permission result for location fine permission

            //Check if the permission has been granted
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //CameraPermissions has been granted
                Log.d("DEBUG1", "Permissions has been granted");
                Toast.makeText(this, "Permissions has been granted",
                                Toast.LENGTH_SHORT).show();
                mLocationPermissionGranted = true;
            } else {
                Log.d("DEBUG1","Permissions was not granted");
                Toast.makeText(this, "Permissions was not granted",
                               Toast.LENGTH_SHORT).show();
                mLocationPermissionGranted=false;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}