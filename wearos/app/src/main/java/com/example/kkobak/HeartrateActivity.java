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
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.kkobak.repository.request.HeartRequest;
import com.example.kkobak.repository.request.JudgeRequest;
import com.example.kkobak.repository.util.RetrofitClient;
import com.example.kkobak.room.dao.AccessTokenDao;
import com.example.kkobak.room.dao.TodoDao;
import com.example.kkobak.room.data.AccessToken;
import com.example.kkobak.room.db.AppDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeartrateActivity extends Activity {

    //심박수 센서 리스너 선언 및 설정
    private SensorEventListener sensorEventListener;
    private SensorManager sensorManager;
    private Sensor hr;

    private TextView textView;
    private TextView hourView, minuteView, secondView;
    private TextView heartView, goalView;

    private ImageButton btn_play;
    private Boolean btn_flag=false;

    // 리스트에서 넘어온 데이터
    private Intent intent;
    private long chlId;

    // startTime 저장용
    private LocalDateTime chk;
    // 토큰
    private String accessToken;

    // 타이머
    int heart_hour, heart_minute, heart_second;
    Timer timer;

    //심박수
    int heartRate=80;

    // 출력 플래그
    boolean flag;

    final int TIMER = 1;

    private static final String TAG = "____Heart___";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartrate);
        // 뷰 컴포넌트 연결
        hourView = findViewById(R.id.heart_hour);
        minuteView = findViewById(R.id.heart_minute);
        secondView = findViewById(R.id.heart_second);
        textView = findViewById(R.id.txt_heartrate);
        heartView = findViewById(R.id.now_heartrate);
        goalView = findViewById(R.id.goal_minute);
        //(토큰 세팅)
        accessToken = ((KkobakApp)getApplication()).getAccessToken();

        //인덴트 값 가져오기
        intent = getIntent();
        chlId = intent.getLongExtra("chlId",1);
        goalView.setText(intent.getIntExtra("goal",1)+"분");

        checkPermission();

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
                        heartView.setText(heartRate+"bpm");
                        sendOne(heartRate, LocalDateTime.now().withNano(0));
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

        btn_play=findViewById(R.id.btn_play);
        btn_flag=false;
        Drawable img = getResources().getDrawable( R.drawable.stop_button );

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_flag){ // 정지
                    flag = false;
                    endTimer();
                    sendJudge();
                    btn_flag=false;
                    btn_play.setVisibility(View.INVISIBLE);
                }
                else { // 시작
                    checkPermission();
                    flag = true;

                    chk = LocalDateTime.now().withNano(0);
                    startTimer();
                    btn_flag=true;
                    btn_play.setImageDrawable(img);
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        System.out.println("종료!!");
        destroyTimer();
        sensorManager.unregisterListener(sensorEventListener);
        super.onDestroy();
    }

    private void checkPermission() { // step 3 started (according to content detail)

        // Runtime permission ------------
        if (checkSelfPermission(Manifest.permission.BODY_SENSORS) // check runtime permission for BODY_SENSORS
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.BODY_SENSORS}, 1); // If BODY_SENSORS permission has not been taken before then ask for the permission with popup
        } else {
            Log.d(TAG, "ALREADY GRANTED"); //if BODY_SENSORS is allowed for this app then print this line in log.
        }
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case TIMER:
                    updateTimer();

                    break;

                default:
                    System.out.println("Run Default Handler");
                    break;
            }
        }
    };

    public void startTimer() {
        runTimer();
    }

    public void endTimer() {
        destroyTimer();
    }

    private void updateTimer() {
        heart_second++;
        if (heart_second == 60) {
            heart_second = 0;
            heart_minute++;
        }
        if (heart_minute == 60) {
            heart_minute = 0;
            heart_hour++;
        }

        if (heart_second <= 9) secondView.setText("0" + heart_second);
        else        secondView.setText(Integer.toString(heart_second));
        if (heart_minute <= 9) minuteView.setText("0" + heart_minute);
        else        minuteView.setText(Integer.toString(heart_minute));
        if (heart_hour <= 9) hourView.setText("0" + heart_hour);
        else        hourView.setText(Integer.toString(heart_hour));
    }

    private void runTimer() {
        heart_hour = heart_minute = heart_second = 0;

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

        if (timer != null) {
            timer.cancel();
            System.out.println("타이머 종료");
        }
    }


    public void sendOne(int heartValue, LocalDateTime time) {
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
        System.out.println("출력: "+chlId+" "+chk.toString());
        //Retrofit 호출
        Call<Void> call = RetrofitClient.getApiService().reqJudge(judgeRequest, accessToken);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else {
//                    Log.d("연결이 성공적 : ", response.body().toString());
                    System.out.println("연결이 성공적 : "+ chk.toString());
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