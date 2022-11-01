package com.example.kkobak.ui.member;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.example.kkobak.R;
import com.example.kkobak.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        binding.setMember(new Member("testEmail", "010-1234-1234", "testNick", "testPwd"));

        binding.getMember().setNickname(new ObservableField<String>( "나는바뀐 닉네임"));
    }
}
