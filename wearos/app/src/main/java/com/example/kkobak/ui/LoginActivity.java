package com.example.kkobak.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kkobak.R;
import com.example.kkobak.retrofit.repository.request.LoginRequest;

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

                LoginRequest post = new LoginRequest(et_email.getText().toString(),et_pw.getText().toString());
            }
        });
    }
}