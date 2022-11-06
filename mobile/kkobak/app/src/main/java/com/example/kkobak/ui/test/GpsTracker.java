package com.example.kkobak.ui.test;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GpsTracker extends Service implements LocationListener {
    private final Context mContext;
    Location location;
    double latitude;
    double longitude;
    TextView tv_gps;

//    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES=10;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES=0;
//    private static final long MIN_TIME_BW_UPDATES=1000 * 60 * 1;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 3;
    protected LocationManager locationManager;

    boolean isGPSEnabled;
    boolean isNetworkEnabled;

    public GpsTracker(Context mContext, TextView tv) {
        this.mContext = mContext;
        tv_gps = tv;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
            }
            else {
                int hasFineLocationPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);

                if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                        hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
                } else
                    return null;

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }


                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            Log.d("@@@", ""+e.toString());
        }

        return location;
    }

    public double getLatitude() {
        if(location != null)
            latitude = location.getLatitude();

        return latitude;
    }

    public double getLongitude() {
        if(location != null)
            longitude = location.getLongitude();

        return longitude;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        this.latitude = this.getLatitude();
        this.longitude = this.getLongitude();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        String time = format.format(date);

        String str = "[ " + time + " ] " + latitude + " " + longitude + "\n" + tv_gps.getText();
        tv_gps.setText(str);
        Log.d("<위치 정보> " ,latitude + " " + longitude);
        System.out.println("좀떠라");
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    public void stopUsingGPS() {
        if(locationManager != null)
            locationManager.removeUpdates(GpsTracker.this);
    }
}
