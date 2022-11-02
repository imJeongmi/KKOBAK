package com.example.kkobak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kkobak.repository.request.LoginRequest;
import com.example.kkobak.repository.response.TokenResponse;
import com.example.kkobak.repository.util.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private EditText et_email;
    private EditText et_pw;
    private Button btn_loginchk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.et_email);
        et_pw = findViewById(R.id.et_pw);
        btn_loginchk = findViewById(R.id.btn_loginchk);



        btn_loginchk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(et_email.getText());
                System.out.println(et_pw.getText());

                callPhoneAlreadyCheck(et_email.getText().toString(),et_pw.getText().toString());
            }
        });
    }

    public void callPhoneAlreadyCheck(String email, String pw){

        //Retrofit 호출
        LoginRequest loginRequest = new LoginRequest(et_email.getText().toString(),et_pw.getText().toString());
        Call<TokenResponse> call = RetrofitClient.getApiService().login(loginRequest);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else{
                    TokenResponse token = response.body();
                    System.out.println(token.getAccessToken());
                    Log.d("연결이 성공적 : ", response.body().toString());

                    Intent intent = new Intent(LoginActivity.this, HeartrateActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}