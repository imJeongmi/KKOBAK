package com.example.kkobak.ui.challenge;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kkobak.R;

public class WaterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
    }

    public void drinkWater(View v) {
        Toast.makeText(this, "물 마시기 완료", Toast.LENGTH_SHORT).show();
        ImageView outer = findViewById(R.id.beforeOutterWater);
        ImageView inner = findViewById(R.id.beforeInnerWater);
        outer.setVisibility(View.INVISIBLE);

        inner.setImageResource(R.drawable.fullwater);
    }
}
