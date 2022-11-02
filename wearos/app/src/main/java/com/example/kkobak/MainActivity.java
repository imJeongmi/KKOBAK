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
import android.widget.TextView;

import com.example.kkobak.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {

    private TextView mTextView;
    private ActivityMainBinding binding;
    private SensorManager sensorManager;
    private Sensor hr;
    private static final String TAG = "____Main___";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTextView = binding.text;

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        hr = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        checkPermission();
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, hr, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        System.out.println("센서 이벤트 발생");
        if (sensorEvent.sensor.getType()== Sensor.TYPE_HEART_RATE){
//            mTextView.setText((int)sensorEvent.values[0]);
            System.out.println(sensorEvent.values[0]);
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
}