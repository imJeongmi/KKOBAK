package com.example.kkobak;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class GpsActivity extends Activity {

    LocationManager locationManager;
    LocationListener locationListener;

    private TextView textView;

    private static final String TAG = "____Main___";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        textView = findViewById(R.id.txt_gps);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                System.out.println("호출됩니다");
                updateMap(location);
            }

            @Override
            public void onFlushComplete(int requestCode) {
                System.out.println("플러시");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                System.out.println("상태변경");
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                System.out.println("제공가능");
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                System.out.println("제공안됨");
            }
        };

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        List<String> l = locationManager.getAllProviders();

//        System.out.println(l.size());
        for (String s : l) {

            System.out.println(s);
        }

        checkPermission();


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLatitude();
            double lat = lastKnownLocation.getLatitude();
            System.out.println("longtitude=" + lng + ", latitude=" + lat);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
    }

    private void updateMap(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        System.out.println("lat: "+ lat +" ,\n "+" lng: "+ lng);
        textView.setText("lat: "+ lat +" ,\n "+" lng: "+ lng);

    }

    private void checkPermission() { // step 3 started (according to content detail)

        // Runtime permission ------------
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) // check runtime permission for location
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1); // If BODY_SENSORS permission has not been taken before then ask for the permission with popup
        } else {
            Log.d(TAG, "ALREADY GRANTED"); //if BODY_SENSORS is allowed for this app then print this line in log.
        }
    }
}
