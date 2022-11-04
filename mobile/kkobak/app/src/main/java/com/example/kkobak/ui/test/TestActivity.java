package com.example.kkobak.ui.test;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kkobak.R;
import com.example.kkobak.data.retrofit.api.MyChallengeApi;
import com.example.kkobak.data.retrofit.api.test_api;
import com.example.kkobak.data.retrofit.model.MyChallengeRes;
import com.example.kkobak.data.retrofit.model.test_model;
import com.example.kkobak.data.room.dao.AccessTokenDao;
import com.example.kkobak.data.room.database.AccessTokenDatabase;
import com.example.kkobak.data.room.entity.AccessToken;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {
    Call<test_model> call;
    Call<List<MyChallengeRes>> call2;
    TextView tv;
    int id;
    AccessTokenDatabase db;
    String accessToken;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        tv = findViewById(R.id.tv_test1);
        id = 1;

        db = AccessTokenDatabase.getAppDatabase(this);
        try {
            accessToken = new getAccessTokenAsyncTask(db.accessTokenDao()).execute().get().getAccessToken();
        } catch (Exception e) {
            Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show();
        }
    }

    public void runTest2(View v) {
        call2 = MyChallengeApi.getMyChallengeService().getMyChallenge("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZW5yeTEzMDJAbmF2ZXIuY29tIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY2ODA1NzUzMX0.pL14MfFpZeNRQU-DY-pbxyDsaAgKCdLCcjYtFfwI7DY");
        call2.enqueue(new Callback<List<MyChallengeRes>>() {
            @Override
            public void onResponse(Call<List<MyChallengeRes>> call, Response<List<MyChallengeRes>> response) {
                String str = "";
                List<MyChallengeRes> result = response.body();

                for (int i = 0; i < result.size(); i++){
                    str = result.get(i).toString() + "\n" + str;
                }

                Toast.makeText(TestActivity.this, "일단 성공", Toast.LENGTH_SHORT).show();

                tv.setText(str);
            }

            @Override
            public void onFailure(Call<List<MyChallengeRes>> call, Throwable t) {
                Toast.makeText(TestActivity.this, "일단 실패", Toast.LENGTH_SHORT).show();
            }
        });
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

    public static class getAccessTokenAsyncTask extends AsyncTask<Void, Void, AccessToken> {
        private final AccessTokenDao accessTokenDao;

        public getAccessTokenAsyncTask(AccessTokenDao accessTokenDao) {
            this.accessTokenDao = accessTokenDao;
        }

        @Override
        protected AccessToken doInBackground(Void... voids) {
            List<AccessToken> tokens = accessTokenDao.getAll();
            if (tokens == null || tokens.size() == 0)
                return (null);
            else
                return (tokens.get(0));
        }
    }
}
