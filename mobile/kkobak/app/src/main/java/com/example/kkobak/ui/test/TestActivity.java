package com.example.kkobak.ui.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kkobak.R;
import com.example.kkobak.data.retrofit.api.test_api;
import com.example.kkobak.data.retrofit.model.test_model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {
    Call<test_model> call;
    TextView tv;
    int id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        tv = findViewById(R.id.tv_test1);
        id = 1;
    }



    public void runTest(View v) {
        call = test_api.getApiService().test_api_get("" + (id++));
        call.enqueue(new Callback<test_model>() {
            @Override
            public void onResponse(Call<test_model> call, Response<test_model> response) {
                test_model result = response.body();
                String str;

                str = "유저 아이디: " +  result.getUserId() + "\n" +
                        "아이디: " + result.getID() + "\n" +
                        "타이틀: " + result.getTitle() + "\n" +
                        "바디: " + result.getBody() + "\n\n" +
                        tv.getText();
                tv.setText(str);
            }

            @Override
            public void onFailure(Call<test_model> call, Throwable t) {
            }
        });
    }
}
