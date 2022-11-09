package com.example.kkobak.ui.challenge;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.kkobak.R;
import com.example.kkobak.data.retrofit.api.GpsDataApi;
import com.example.kkobak.data.retrofit.model.GpsDataReq;
import com.example.kkobak.data.room.dao.AccessTokenDao;
import com.example.kkobak.data.room.database.AccessTokenDatabase;
import com.example.kkobak.data.room.entity.AccessToken;
import com.example.kkobak.ui.test.TestActivity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GpsActivity extends AppCompatActivity implements LocationListener {
    AccessTokenDatabase db;
    String accessToken;

    LocationManager locationManager;
    Location mLastLocation;
    TextView hour, minute, second, gpsSpeed, gpsDistance;
    Button startBtn, stopBtn;
    int _hour, _minute, _second;
    Timer timer;
    double speed, distance;
    boolean flag;

    LocalDateTime startTime;

    final int TIMER = 1;
    final int GPS = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        hour = findViewById(R.id.gpsHour);
        minute = findViewById(R.id.gpsMinute);
        second = findViewById(R.id.medTimerMinute);

        gpsSpeed = findViewById(R.id.gpsSpeed);
        gpsDistance = findViewById(R.id.gpsDistance);

        startBtn = findViewById(R.id.gpsStartBtn);
        stopBtn = findViewById(R.id.gpsStopBtn);

        stopBtn.setClickable(false);

        //권한 체크
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) 
            return;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,1, this);

        db = AccessTokenDatabase.getAppDatabase(this);
        try {
            accessToken = new TestActivity.getAccessTokenAsyncTask(db.accessTokenDao()).execute().get().getAccessToken();
        } catch (Exception e) {
            Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우 최초 권한 요청 또는 사용자에 의한 재요청 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 권한 재요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, "변경 감지", Toast.LENGTH_SHORT).show();

//        double getSpeed = location.getSpeed();
        if (mLastLocation != null) {
            double deltaTime, interval;

            deltaTime = (location.getTime() - mLastLocation.getTime()) / 1000.0;
            interval = mLastLocation.distanceTo(location);

            distance += interval;
            speed = (interval / deltaTime);

            distance = Double.parseDouble(String.format("%.2f", distance));
            speed = Double.parseDouble(String.format("%.2f", speed));

            mHandler.sendEmptyMessage(GPS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendGpsData(location.getLatitude(), location.getLongitude(), LocalDateTime.now().withNano(0));
            }
        }
        mLastLocation = location;
    }

    public void sendGpsData(double lat, double lng, LocalDateTime time) {
        int chlId = 91;
        GpsDataReq gpsDataReq = new GpsDataReq(chlId, time.toString(), Double.toString(lat), Double.toString(lng), startTime.toString());
        Call<Boolean> call = GpsDataApi.sendGpsData().sendGpsData(accessToken, gpsDataReq);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(GpsActivity.this, "에러: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(GpsActivity.this, "성공: " + response.body().toString(), Toast.LENGTH_SHORT).show();
                 }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(GpsActivity.this, "연결 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case TIMER:
                    updateTimer();
                    break;
                case GPS:
                    if (flag)
                        updateGpsData();
                    break;
                    default:
                        System.out.println("Run Default Handler");
                    break;
            }
        }
    };

    private void updateTimer() {
        _second++;
        if (_second == 60) {
            _second = 0;
            _minute++;
        }
        if (_minute == 60) {
            _minute = 0;
            _hour++;
        }

        if (_second <= 9) second.setText("0" + _second);
        else        second.setText(Integer.toString(_second));
        if (_minute <= 9) minute.setText("0" + _minute);
        else        minute.setText(Integer.toString(_minute));
        if (_hour <= 9) hour.setText("0" + _hour);
        else        hour.setText(Integer.toString(_hour));
    }

    private void updateGpsData() {
        gpsSpeed.setText(speed + "km");
        gpsDistance.setText(distance + "m");
    }

    private void runTimer() {
        _hour = _minute = _second = 0;

        startBtn.setClickable(false);
        stopBtn.setClickable(true);

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(TIMER);
            }
        };
        timer.schedule(timerTask, 0, 1000); //Timer 실행
    }

    private void destroyTimer() {
        startBtn.setClickable(true); 
        stopBtn.setClickable(false); 

        if (timer != null)
            timer.cancel();
    }

    public void startLogic(View v) {
        runTimer();
        distance = speed = 0;
        flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startTime = LocalDateTime.now().withNano(0);
        }
    }

    public void endLogic(View v) {
        destroyTimer();
        flag = false;
    }

    public static class getAccessTokenAsyncTask extends AsyncTask<Void, Void, AccessToken> {
        private final AccessTokenDao accessTokenDao;

        public getAccessTokenAsyncTask(AccessTokenDao accessTokenDao) {
            this.accessTokenDao = accessTokenDao;
        }

        @Override
        protected AccessToken doInBackground(Void... voids) {
            List<AccessToken> tokens = accessTokenDao.getAll();
            if (tokens == null || tokens.size() == 0)
                return (null);
            else
                return (tokens.get(0));
        }
    }
}
