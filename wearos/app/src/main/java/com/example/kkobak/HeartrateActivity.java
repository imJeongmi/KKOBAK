package com.example.kkobak;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeartrateActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor hr;

    private TextView textView;

    private Button btn_start;
    private Button btn_end;
    private SensorEventListener registerListener;
    private Intent intent;

    // intent로 넘어온 chlId 저장 (Create 시)
    private long chlId;
    // startTime 저장용
    private LocalDateTime chk;
    // 토큰
    private String accessToken;

    private static final String TAG = "____Main___";

    private List<Integer> heartRate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartrate);
        registerListener = this;
        intent = getIntent();
        chlId = intent.getLongExtra("chlId",1);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        hr = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        textView = findViewById(R.id.txt_heartrate);

        //(토큰 세팅)
        accessToken = ((KkobakApp)getApplication()).getAccessToken();

        checkPermission();

        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.registerListener(registerListener, hr, SensorManager.SENSOR_DELAY_NORMAL);
                chk = LocalDateTime.now().withNano(0);
            }
        });
        btn_end = findViewById(R.id.btn_end);
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.unregisterListener(registerListener);

                // 여기 입력하면 될듯
                sendJudge();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
//        sensorManager.registerListener(this, hr, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) { // 시작
        System.out.println("센서 이벤트 발생");
        if (sensorEvent.sensor.getType()== Sensor.TYPE_HEART_RATE){
            int heartValue = (int)sensorEvent.values[0];
//            System.out.println(LocalDateTime.now());
            textView.setText(heartValue+"");
//            System.out.println(sensorEvent.values[0]);
            heartRate.add(heartValue);
            if(heartValue!=0) {
                sendOne(heartValue, LocalDateTime.now().withNano(0));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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

    @Override
    protected void onDestroy() {
        System.out.println("========================================심박수 데이터 입니다!!");
//        System.out.println(heartRate.size());
//        for (Integer integer : heartRate) {
//            System.out.println(integer);
//        }




//        sendData();

//        sensorManager.unregisterListener(this);
        super.onDestroy();
    }
    //리스트로 보내는 함수 (안씀)
    public void sendData() {
        //Retrofit 호출
        Call<Boolean> call = RetrofitClient.getApiService().sendHeartList(heartRate);
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
        //Retrofit 호출
        Call call = RetrofitClient.getApiService().reqJudge(judgeRequest, accessToken);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    System.out.println(chk.toString());
                    return;
                }
                else {
                    Log.d("연결이 성공적 : ", response.body().toString());
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