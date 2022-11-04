package com.example.kkobak;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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

import com.example.kkobak.repository.util.RetrofitClient;
import com.example.kkobak.room.db.AppDatabase;

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

    private static final String TAG = "____Main___";

    private List<Integer> heartRate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartrate);
        registerListener = this;

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        hr = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        textView = findViewById(R.id.txt_heartrate);

        // Room 관련 코드
        AppDatabase database = AppDatabase.getInstance(getApplicationContext());

        checkPermission();

        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.registerListener(registerListener, hr, SensorManager.SENSOR_DELAY_NORMAL);
            }
        });
        btn_end = findViewById(R.id.btn_end);
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.unregisterListener(registerListener);
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
            sendOne(heartValue);
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

    public void sendOne(int heartValue) {
        //Retrofit 호출
        Call<Boolean> call = RetrofitClient.getApiService().sendHeartOne(heartValue);
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