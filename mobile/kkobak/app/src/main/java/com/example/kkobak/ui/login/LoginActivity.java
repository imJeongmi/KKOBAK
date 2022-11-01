package com.example.kkobak.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kkobak.ui.main.MainActivity;
import com.example.kkobak.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void moveMainPage(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

