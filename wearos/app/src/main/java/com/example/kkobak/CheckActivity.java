package com.example.kkobak;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CheckActivity extends Activity {

    TextView counter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        // 뷰 연결
        counter = findViewById(R.id.counter);


    }
    @Override
    protected void onResume(){
        super.onResume();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
