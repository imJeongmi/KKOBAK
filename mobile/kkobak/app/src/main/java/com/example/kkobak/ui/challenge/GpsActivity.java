package com.example.kkobak.ui.challenge;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.kkobak.R;
import com.example.kkobak.data.retrofit.api.ChallengeChkApi;
import com.example.kkobak.data.retrofit.api.GpsDataApi;
import com.example.kkobak.data.retrofit.model.GpsDataReq;
import com.example.kkobak.data.retrofit.model.JudgeReq;
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
    TextView remindDistance;
    TextView title;
    ImageView img;
    Button btn;
    int _hour, _minute, _second;
    Timer timer;
    double speed, distance;
    double remind;
    boolean flag;

    String chlId;
    String name;
    double goal;

    int imgFlag;

    LocalDateTime startTime;

    final int TIMER = 1;
    final int GPS = 2;

    final int GPS_TIME = 2000;
    final int GPS_DISTANCE = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        hour = findViewById(R.id.gpsHour);
        minute = findViewById(R.id.gpsMinute);
        second = findViewById(R.id.medTimerMinute);

        gpsSpeed = findViewById(R.id.gpsSpeed);
        gpsDistance = findViewById(R.id.gpsDistance);
        remindDistance = findViewById(R.id.remindDistance);
        title = findViewById(R.id.gpsTitle);

        btn = findViewById(R.id.gpsBtn);
        img = findViewById(R.id.gpsImg);

//        Glide.with(this).load(R.drawable.running).into(img);

        //권한 체크
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) 
            return;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_TIME, GPS_DISTANCE, this);

        db = AccessTokenDatabase.getAppDatabase(this);
        try {
            accessToken = new TestActivity.getAccessTokenAsyncTask(db.accessTokenDao()).execute().get().getAccessToken();
        } catch (Exception e) {
            Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show();
        }

        Intent intent = getIntent();
        name = intent.getStringExtra("title");
        goal = intent.getIntExtra("goal", 1) * 1000;
        if (intent.getStringExtra("chlId") != null)
            chlId = intent.getStringExtra("chlId");
        else
            chlId = "-1";

        title.setText(name);
        remindDistance.setText(convertMtoKm(goal) + " km");

        remind = Double.parseDouble(convertMtoKm(goal));
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_DISTANCE, GPS_DISTANCE, this);
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

            if (imgFlag == 0) {
                if (speed < 6.0) {
                    Glide.with(this).load(R.drawable.walk).into(img);
                    imgFlag = 1;
                }
                else {
                    Glide.with(this).load(R.drawable.run).into(img);
                    imgFlag = 2;
                }
            }
            else if (imgFlag == 1) {
                if (speed >= 6.0) {
                    Glide.with(this).load(R.drawable.run).into(img);
                    imgFlag = 2;
                }
            }
            else {
                if (speed < 6.0) {
                    Glide.with(this).load(R.drawable.walk).into(img);
                    imgFlag = 1;
                }
            }

//            distance = Double.parseDouble(String.format("%.2f", distance));
//            speed = Double.parseDouble(String.format("%.2f", speed));

            mHandler.sendEmptyMessage(GPS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendGpsData(location.getLatitude(), location.getLongitude(), LocalDateTime.now().withNano(0));
            }
        }
        mLastLocation = location;
    }

    public void sendGpsData(double lat, double lng, LocalDateTime time) {
        int id = Integer.parseInt(chlId);
        GpsDataReq gpsDataReq = new GpsDataReq(id, time.toString(), Double.toString(lat), Double.toString(lng), startTime.toString());
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

    public String convertMtoKm(double m) {
        m /= 1000;
        return (String.format("%.2f", m));
    }

    private void updateGpsData() {
        gpsSpeed.setText(String.format("%.2f", speed) + " km/h");
        gpsDistance.setText(convertMtoKm(distance) + " km");
        if (remind > 0) {
            remind = goal - distance;
            if (remind < 0)
                remind = 0;
        }
        remindDistance.setText(convertMtoKm(remind) + " km");
    }

    private void runTimer() {
        _hour = _minute = _second = 0;

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(TIMER);
            }
        };
        timer.schedule(timerTask, 0, 1000); //Timer 실행
    }

    public void startLogic(View v) {
        if (!flag) {
            btn.setText("종료");
            runTimer();
            imgFlag = 0;
            distance = speed = 0;
            flag = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startTime = LocalDateTime.now().withNano(0);
            }
        }
        else {
            timer.cancel();

            if (mLastLocation != null) {
                JudgeReq judgeReq = new JudgeReq(Long.parseLong(chlId), String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()), null);
                Call<Boolean> call = ChallengeChkApi.getService().judgeChallenge(accessToken, judgeReq);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Toast.makeText(GpsActivity.this, "code: " + response.code() + "\nresult: " + response.body(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(name + " 결과");
                builder.setMessage("[ " + (remind > 0 ? "실패" : "성공") + " ]\n" +
                        "남은거리: " + String.format("%.2f", remind) + " km\n" + "소요시간: " +
                        (_hour <= 9 ? "0" + _hour : "" + _hour) + ":" +
                        (_minute <= 9 ? "0" + _minute : "" + _minute) + ":" +
                        (_second <= 9 ? "0" + _second : "" + _second));
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.create().show();
            }
            else {
                flag = false;
                _hour = _minute = _second = 0;
                hour.setText("00");
                minute.setText("00");
                second.setText("00");
                btn.setText("시작");
            }
        }
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
