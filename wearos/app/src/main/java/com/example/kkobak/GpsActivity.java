package com.example.kkobak;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.kkobak.repository.request.GPSRequest;
import com.example.kkobak.repository.util.RetrofitClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GpsActivity extends Activity {

    LocationManager locationManager;
    LocationListener locationListener;
//    Timer timer;
    String str;
    private TextView textView;

    private Button btn_gps_start;
    private Button btn_gps_end;
    
    // 시작 시간 저장용
    LocalDateTime chk;

    private static final String TAG = "____GPS____";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        textView = findViewById(R.id.txt_gps);
        str ="";
        textView.setMovementMethod(new ScrollingMovementMethod());
        
        // 권한 확인
        checkPermission();

        //로케이션 리스너 설정
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                System.out.println("호출됩니다");
                System.out.println(LocalDateTime.now());
                updateMap(location);
                sendOne(location.getLatitude(), location.getLongitude(), LocalDateTime.now());
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

        // 프로바이더 리스트 체크 후 출력
        List<String> l = locationManager.getAllProviders();

        System.out.println(l.size());
        for (String s : l) {

            System.out.println(s);
        }


//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,locationListener);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLatitude();
            double lat = lastKnownLocation.getLongitude();
            System.out.println("longtitude=" + lng + ", latitude=" + lat);
        }


        btn_gps_start = findViewById(R.id.button_gps_start);
        btn_gps_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 확인
                checkPermission();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,locationListener);
                chk = LocalDateTime.now();
            }
        });

        btn_gps_end = findViewById(R.id.button_gps_end);
        btn_gps_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locationManager != null){
                    locationManager.removeUpdates(locationListener);
                }
            }
        });

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
        str = "timestamp: " +LocalDateTime.now() + "\n" + "lat: "+ lat +" ,\n "+" lng: "+ lng+ "\n" + "startTime: "+ chk+"\n";
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

    public void sendOne(double lat, double lng, LocalDateTime time) {
        //Retrofit 호출
        GPSRequest gpsRequest = new GPSRequest(1L, time, Double.toString(lat), Double.toString(lng), chk);
        Call<Boolean> call = RetrofitClient.getApiService().sendGPSOne(gpsRequest);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
