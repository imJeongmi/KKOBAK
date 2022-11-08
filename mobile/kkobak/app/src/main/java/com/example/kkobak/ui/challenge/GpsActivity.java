package com.example.kkobak.ui.challenge;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kkobak.R;

import java.util.Timer;
import java.util.TimerTask;

public class GpsActivity extends AppCompatActivity {
    TextView hour, minute, second;
    Button startBtn, stopBtn;
    int h, m, s;
    Timer timer;

    final int TIMER = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        hour = findViewById(R.id.gpsHour);
        minute = findViewById(R.id.gpsMinute);
        second = findViewById(R.id.gpsSecond);

        startBtn = findViewById(R.id.gpsStartBtn);
        stopBtn = findViewById(R.id.gpsStopBtn);

        stopBtn.setClickable(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private void updateTimer() {
        s++;
        if (s == 60) {
            s = 0;
            m++;
        }
        if (m == 60) {
            m = 0;
            h++;
        }

        if (s <= 9) second.setText("0" + s);
        else        second.setText(Integer.toString(s));
        if (m <= 9) minute.setText("0" + m);
        else        minute.setText(Integer.toString(m));
        if (h <= 9) hour.setText("0" + h);
        else        hour.setText(Integer.toString(h));
    }

    private void runTimer() {
        h = m = s = 0;

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

        if (timer != null) {
            timer.cancel();
        }
    }

    public void startLogic(View v) {
        runTimer();
    }

    public void endLogic(View v) {
        destroyTimer();
    }

}
