package com.example.kkobak;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.kkobak.repository.request.GPSRequest;
import com.example.kkobak.repository.util.RetrofitClient;
import com.example.kkobak.room.dao.AccessTokenDao;
import com.example.kkobak.room.dao.TodoDao;
import com.example.kkobak.room.data.AccessToken;
import com.example.kkobak.room.db.AppDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GpsActivity extends Activity {

    //로케이션 리스너 선언 및 설정
    LocationListener locationListener;
    LocationManager locationManager;

    // 출력용 뷰
    String str;
    private TextView titleView;
    private TextView hourView, minuteView, secondView, gpsSpeedView, gpsDistanceView;

    private Button btn_gps_start;
    private Button btn_gps_stop;
    private Button btn_gps_end;

    // 리스트에서 넘어온 데이터
    Intent intent;
    private Long chlId;

    // 토큰
    private String accessToken;
    private AccessTokenDao tokenDao;
    private TodoDao todoDao;

    // 시작 시간 저장용
    LocalDateTime chk;
    Location mLastLocation;

    // 타이머
    int gps_hour, gps_minute, gps_second;
    Timer timer;

    // 속도,거리
    double speed, distance;

    // 플래그
    boolean flag;

    final int TIMER = 1;
    final int GPS = 2;

    private static final String TAG = "____GPS____";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        // 뷰 연결
        titleView = findViewById(R.id.txt_gps);
        hourView = findViewById(R.id.gps_hour);
        minuteView = findViewById(R.id.gps_minute);
        secondView = findViewById(R.id.gps_second);

        gpsSpeedView = findViewById(R.id.gps_speed);
        gpsDistanceView = findViewById(R.id.gps_distance);


        str ="";
//        textView.setMovementMethod(new ScrollingMovementMethod());

        // 토큰 세팅
        AppDatabase database = AppDatabase.getInstance(getApplicationContext());
        tokenDao = database.tokenDao();
        todoDao = database.todoDao();
        List<AccessToken> tokenList = tokenDao.getTokenAll();
        accessToken = tokenList.get(0).getAccessToken();

        //챌린지 아이디 세팅
        intent = getIntent();
        chlId = intent.getLongExtra("chlId",1);

        // 권한 확인
        checkPermission();

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                System.out.println("호출됩니다");
                System.out.println(LocalDateTime.now().withNano(0));
                updateMap(location);
//                sendOne(location.getLatitude(), location.getLongitude(), LocalDateTime.now().withNano(0));
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
        System.out.println("리스너 설정 완료");

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

        //시작 버튼
        btn_gps_start = findViewById(R.id.button_gps_start);
        btn_gps_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 확인
                checkPermission();
                // 클릭하면 센서를 로케이션 리스너에 등록
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
                // 시작 시간 등록
                chk = LocalDateTime.now(). withNano(0);
                startTimer();
            }
        });

        // 종료 버튼
        btn_gps_end = findViewById(R.id.button_gps_end);
        btn_gps_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locationManager != null){
                    locationManager.removeUpdates(locationListener);
                }
                endTimer();
            }
        });

        btn_gps_end.setClickable(false);
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
        System.out.println(str);
//        titleView.setText(str);
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
        if (mLastLocation != null) {
            double deltaTime, interval;

            deltaTime = (location.getTime() - mLastLocation.getTime()) / 1000.0;
            interval = mLastLocation.distanceTo(location);

            distance += interval;
            speed = (interval / deltaTime);

            distance = Double.parseDouble(String.format("%.2f", distance));
            speed = Double.parseDouble(String.format("%.2f", speed));

            mHandler.sendEmptyMessage(GPS);
        }
        mLastLocation = location;
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
        GPSRequest gpsRequest = new GPSRequest( chlId, time.toString(), Double.toString(lat), Double.toString(lng), chk.toString());
        Call<Boolean> call = RetrofitClient.getApiService().sendGPSOne(gpsRequest, accessToken);
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
        gps_second++;
        if (gps_second == 60) {
            gps_second = 0;
            gps_minute++;
        }
        if (gps_minute == 60) {
            gps_minute = 0;
            gps_hour++;
        }

        if (gps_second <= 9) secondView.setText("0" + gps_second);
        else        secondView.setText(Integer.toString(gps_second));
        if (gps_minute <= 9) minuteView.setText("0" + gps_minute);
        else        minuteView.setText(Integer.toString(gps_minute));
        if (gps_hour <= 9) hourView.setText("0" + gps_hour);
        else        hourView.setText(Integer.toString(gps_hour));
    }

    private void updateGpsData() {
        gpsSpeedView.setText("현재속도: "+ speed + "km/h");
        gpsDistanceView.setText("이동거리: "+ distance + "m");
    }

    private void runTimer() {
        gps_hour = gps_minute = gps_second = 0;

        btn_gps_start.setClickable(false);
        btn_gps_end.setClickable(true);

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
        btn_gps_start.setClickable(true);
        btn_gps_end.setClickable(false);

        if (timer != null)
            timer.cancel();
    }

    public void startTimer() {
        distance = speed = 0;
        runTimer();
        flag = true;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startTime = LocalDateTime.now().withNano(0);
//        }
    }

    public void endTimer() {
        destroyTimer();
        flag = false;
    }
}
