package com.example.kkobak;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.kkobak.repository.request.GPSRequest;
import com.example.kkobak.repository.request.HeartRequest;
import com.example.kkobak.repository.request.JudgeRequest;
import com.example.kkobak.repository.util.RetrofitClient;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RunActivity extends Activity {

    //로케이션 리스너 선언 및 설정
    LocationListener locationListener;
    LocationManager locationManager;

    //심박수 센서 리스너 선언 및 설정
    SensorEventListener sensorEventListener;
    SensorManager sensorManager;
    Sensor hr;

    // 출력용 뷰
    String str;
    private TextView titleView;
    private TextView hourView, minuteView, secondView, runHeartView, runSpeedView, runDistanceView,goalDistanceView;

//    private Button btn_run_start;
//    private Button btn_run_stop;
//    private Button btn_run_end;
    private ImageButton btn_play;
    private Boolean btn_flag=false;

    // 리스트에서 넘어온 데이터
    Intent intent;
    private Long chlId;

    // 액세스 토큰
    private String accessToken;

    // 시작 시간 저장용
    LocalDateTime chk;
    // 마지막 장소 저장용
    Location mLastLocation;

    // 타이머
    int gps_hour, gps_minute, gps_second;
    Timer timer;

    //심박수
    int heartRate=80;

    // 속도,거리
    double speed, distance;
    // 0km/h 처리용
    long now = System.currentTimeMillis();;

    // 출력 플래그
    boolean flag;

    final int TIMER = 1;
    final int GPS = 2;

    private static final String TAG = "____RUN____";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        // 뷰 컴포넌트 연결
        titleView = findViewById(R.id.title_run);
        hourView = findViewById(R.id.run_hour);
        minuteView = findViewById(R.id.run_minute);
        secondView = findViewById(R.id.run_second);
        runSpeedView = findViewById(R.id.run_speed);
        runDistanceView = findViewById(R.id.run_distance);
        runHeartView = findViewById(R.id.run_heartRate);
        goalDistanceView = findViewById(R.id.goal_distance);

        // 토큰 세팅
        accessToken = ((KkobakApp)getApplication()).getAccessToken();


        //챌린지 아이디 가져오기
        intent = getIntent();
        chlId = intent.getLongExtra("chlId",-1);
        goalDistanceView.setText(intent.getIntExtra("goal",1000)+"m");
//        String title = intent.getStringExtra("title");
//        titleView.setText(title);

        // 장소, 심박수 권한 확인
        checkPermission();

        // 로케이션 설정
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                now = System.currentTimeMillis();
                System.out.println("GPS 호출됩니다");
                System.out.println(LocalDateTime.now().withNano(0));
//                Toast.makeText(getApplicationContext(), "GPS: " + location.getLatitude() +"\n"+ location.getLongitude(), Toast.LENGTH_LONG).show();
                updateMap(location);
                if (flag) {
                    runDistanceView.setText("이동거리: "+distance+"m" );
                    runSpeedView.setText("현재 속력: "+speed+"km/h" );
                    sendGPSOne(location.getLatitude(), location.getLongitude(), LocalDateTime.now().withNano(0));
                }
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
        System.out.println("위치 리스너 설정 완료");
        
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        System.out.println("위치 매니저 설정 완료");

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        System.out.println("위치 연결");

        // 심박수 설정
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        hr = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                System.out.println("심박수 센서 이벤트 발생");
                if (sensorEvent.sensor.getType()== Sensor.TYPE_HEART_RATE){
                    int heartValue = (int)sensorEvent.values[0];
                    if(heartValue!=0) {
                        heartRate = heartValue;
                    }
                    System.out.println(LocalDateTime.now().withNano(0));
                    System.out.println("센서가 인식한 심박수: "+heartValue);


                    if(flag) {
                        runHeartView.setText("심박수: " + heartRate);
//                        Toast.makeText(getApplicationContext(), "심박수: " + heartRate, Toast.LENGTH_LONG).show();
                        sendHeartOne(heartRate, LocalDateTime.now().withNano(0));
                        System.out.println("심박수 보냈습니다");
                    }

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        
        sensorManager.registerListener(sensorEventListener, hr, SensorManager.SENSOR_DELAY_NORMAL);
        System.out.println("심박수 등록 완료");

        //시작 버튼
//        btn_run_start = findViewById(R.id.button_run_start);
//        btn_run_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 권한 확인
//                checkPermission();
//                flag = true;
//                // 클릭하면 센서를 로케이션 리스너에 등록
//
//                // 시작 시간 등록
//                chk = LocalDateTime.now(). withNano(0);
//                startTimer();
//            }
//        });
//
//        // 종료 버튼
//        btn_run_end = findViewById(R.id.button_run_end);
//        btn_run_end.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                flag = false;
//                endTimer();
//                // 여기 입력하면 될듯
//                sendJudge();
//            }
//        });

//        btn_run_end.setClickable(false);

        btn_play=findViewById(R.id.btn_play);
        btn_flag=false;
        Drawable img = getResources().getDrawable( R.drawable.stop_button );

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_flag){ // 정지
                    flag = false;
                    endTimer();
                    // 여기 입력하면 될듯
                    sendJudge();
                    btn_flag=false;
                    btn_play.setVisibility(View.INVISIBLE);
                }
                else { // 시작
                    // 권한 확인
                    checkPermission();
                    flag = true;
                    // 클릭하면 센서를 로케이션 리스너에 등록

                    // 시작 시간 등록
                    chk = LocalDateTime.now(). withNano(0);
                    startTimer();
                    btn_flag=true;
                    btn_play.setImageDrawable(img);
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        System.out.println("Run 종료 선언");
        destroyTimer();
        sensorManager.unregisterListener(sensorEventListener);
        super.onDestroy();
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
        System.out.println("종료되었습니다");
    }

    private void checkPermission() { // 권한 확인 함수

        // 위치 권한 확인
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) // check runtime permission for location
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1); // If BODY_SENSORS permission has not been taken before then ask for the permission with popup
        } else {
            Log.d(TAG, "ALREADY GRANTED"); //if BODY_SENSORS is allowed for this app then print this line in log.
        }

        // 바디 센서 권한 확인
        if (checkSelfPermission(Manifest.permission.BODY_SENSORS) // check runtime permission for BODY_SENSORS
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.BODY_SENSORS}, 1); // If BODY_SENSORS permission has not been taken before then ask for the permission with popup
        } else {
            Log.d(TAG, "ALREADY GRANTED"); //if BODY_SENSORS is allowed for this app then print this line in log.
        }
    }
    
    public void startTimer() {
        distance = speed = 0;
//        얘가 심박수 문제인듯?
//        heartRate = 0;
        runHeartView.setText("심박수: 0");
        runDistanceView.setText("이동거리: 0m" );
        runSpeedView.setText("현재 속력: 0km/h" );
        runTimer();
    }

    public void endTimer() {
        destroyTimer();
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


    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case TIMER:
                    updateTimer();
                    Long next = System.currentTimeMillis();
                    if(next-now>10000) runSpeedView.setText("현재 속력: 0km/h");
                    break;
//                case GPS:
//                    if (flag)
//                        updateGpsData();
//                    break;
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

    private void runTimer() {
        gps_hour = gps_minute = gps_second = 0;

//        btn_run_start.setClickable(false);
//        btn_run_end.setClickable(true);

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
//        btn_run_start.setClickable(true);
//        btn_run_end.setClickable(false);

        if (timer != null) {
            timer.cancel();
            System.out.println("타이머 종료");
        }
    }

    // GPS 데이터 보내는 함수
    public void sendGPSOne(double lat, double lng, LocalDateTime time) {
        //Retrofit 호출
        GPSRequest gpsRequest = new GPSRequest( chlId, time.toString(), Double.toString(lat), Double.toString(lng), chk.toString());
//        System.out.println(chlId+" "+ time.toString() +" "+ Double.toString(lat) +" "+ Double.toString(lng) +" "+ chk.toString());
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
    // 심박수 데이터 보내는 함수
    public void sendHeartOne(int heartValue, LocalDateTime time) {
        HeartRequest heartRequest = new HeartRequest(chlId, time.withNano(0).toString(), heartValue, chk.toString());
        //Retrofit 호출
        Call<Boolean> call = RetrofitClient.getApiService().sendHeartOne(heartRequest, accessToken);
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

    public void sendJudge(){
        JudgeRequest judgeRequest = new JudgeRequest( chlId, chk.toString(), "","");
        //Retrofit 호출
        Call<Void> call = RetrofitClient.getApiService().reqJudge(judgeRequest, accessToken);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    System.out.println(chk.toString());
                    return;
                }
                else {
//                    Log.d("연결이 성공적 : ", response.body().toString());
                    System.out.println(chk.toString());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("연결실패", t.getMessage());
                System.out.println(chk.toString());
            }
        });
    }
}
