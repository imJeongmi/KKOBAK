package com.example.kkobak.ui.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kkobak.R;
import com.example.kkobak.data.retrofit.api.LoginApi;
import com.example.kkobak.data.retrofit.model.LoginReq;
import com.example.kkobak.data.retrofit.model.LoginRes;
import com.example.kkobak.data.room.dao.AccessTokenDao;
import com.example.kkobak.data.room.database.AccessTokenDatabase;
import com.example.kkobak.data.room.entity.AccessToken;
import com.example.kkobak.ui.main.MainActivity;
import com.example.kkobak.ui.register.RegisterActivity;
import com.example.kkobak.ui.test.TestActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    AccessTokenDatabase db;
    Call<LoginRes> call;
    static List<AccessToken> list = new LinkedList<>();
    static final int REGISTER_PAGE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = AccessTokenDatabase.getAppDatabase(this);
        new confirmAsyncTask(db.accessTokenDao()).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (list.size() == 1) { //토큰 값을 가지고 있으니 로그인 완료
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void tryLogin(View v) {
        String email = ((EditText) findViewById(R.id.loginEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.loginPassword)).getText().toString();

        call = LoginApi.doLoginService().doLogin(new LoginReq(email, password));
        call.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                if (response.code() == 200) {
                   new LoginAsyncTask(db.accessTokenDao()).execute(new AccessToken(response.body().getAccessToken()));

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {

            }
        });
    }

    public void moveTestConfig(View v){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void moveRegisterPage(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, REGISTER_PAGE);
    }

    public static class confirmAsyncTask extends AsyncTask<Void, Void, Void> {
        private final AccessTokenDao accessTokenDao;

        public confirmAsyncTask(AccessTokenDao accessTokenDao) {
            this.accessTokenDao = accessTokenDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            list = accessTokenDao.getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }

    public static class LoginAsyncTask extends AsyncTask<AccessToken, Void, Void> {
        private final AccessTokenDao accessTokenDao;

        public LoginAsyncTask(AccessTokenDao accessTokenDao) {
            this.accessTokenDao = accessTokenDao;
        }

        @Override
        protected Void doInBackground(AccessToken... accessTokens) {
            accessTokenDao.deleteAll();
            accessTokenDao.insert(accessTokens[0]);

            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTER_PAGE) {
            if (resultCode == RESULT_OK) {

                String email = data.getStringExtra("email");
                String password = data.getStringExtra("password");

                EditText etEmail = (EditText) findViewById(R.id.loginEmail);
                EditText etPassword = (EditText) findViewById(R.id.loginPassword);

                etEmail.setText(email);
                etPassword.setText(password);

//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);

//                finish();
            }
            else {
//                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

