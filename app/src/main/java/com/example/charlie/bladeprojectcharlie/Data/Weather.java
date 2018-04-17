package com.example.charlie.bladeprojectcharlie.Data;

import android.util.Log;

//Singleton
public class Weather {

    //Attributs
    private double mLatitude;
    private double mLongitude;
    private static Weather INSTANCE = null;


    //Methods
    private Weather()
    {}

    //Singleton Implementation
    public static synchronized Weather getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new Weather();
        }
        return INSTANCE;
    }


    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }
}
