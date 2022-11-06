package com.example.kkobak;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GpsActivity extends Activity {

    LocationManager locationManager;
    LocationListener locationListener;
//    Timer timer;
    String str;
    private TextView textView;

    private static final String TAG = "____Main___";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        textView = findViewById(R.id.txt_gps);
        str ="";
        textView.setMovementMethod(new ScrollingMovementMethod());
        System.out.println("시작");
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                System.out.println("호출됩니다");
                System.out.println(LocalDateTime.now());
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
        System.out.println("리스너 등록 완료");
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        System.out.println("매니저");
        List<String> l = locationManager.getAllProviders();

        System.out.println(l.size());
        for (String s : l) {

            System.out.println(s);
        }

        checkPermission();


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLatitude();
            double lat = lastKnownLocation.getLongitude();
            System.out.println("longtitude=" + lng + ", latitude=" + lat);
        }
//        timer = new Timer();
//        TimerTask TT = new TimerTask() {
//            @Override
//            public void run() {
//                double lng = lastKnownLocation.getLatitude();
//                double lat = lastKnownLocation.getLongitude();
//                System.out.println("longtitude=" + lng + ", latitude=" + lat);
//            }
//
//        };
//
//
//
//        timer.schedule(TT, 0, 1000); //Timer 실행

    }

    @Override
    protected void onDestroy() {
//        timer.cancel();//타이머 종료
        super.onDestroy();
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
    }

    private void updateMap(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        str += "lat: "+ lat +" ,\n "+" lng: "+ lng+ "\n";
        System.out.println("lat: "+ lat +" ,\n "+" lng: "+ lng);
        textView.setText(str);

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
