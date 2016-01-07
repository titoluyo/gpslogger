package com.mendhak.gpslogger;

import android.location.Location;
import android.os.Bundle;

public class Ubicacion {

    private String mProvider;
    private long mTime = 0;
    private long mElapsedRealtimeNanos = 0;
    private double mLatitude = 0.0;
    private double mLongitude = 0.0;
    private boolean mHasAltitude = false;
    private double mAltitude = 0.0f;
    private boolean mHasSpeed = false;
    private float mSpeed = 0.0f;
    private boolean mHasBearing = false;
    private float mBearing = 0.0f;
    private boolean mHasAccuracy = false;
    private float mAccuracy = 0.0f;
    private Bundle mExtras = null;
    private boolean mIsFromMockProvider = false;


    public Ubicacion(Location location){

    }


}
