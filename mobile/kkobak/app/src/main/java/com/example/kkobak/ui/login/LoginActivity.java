package com.example.kkobak.ui.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kkobak.R;
import com.example.kkobak.data.room.dao.AccessTokenDao;
import com.example.kkobak.data.room.database.AccessTokenDatabase;
import com.example.kkobak.data.room.entity.AccessToken;
import com.example.kkobak.ui.main.MainActivity;
import com.example.kkobak.ui.register.RegisterActivity;
import com.example.kkobak.ui.test.TestActivity;

import java.util.LinkedList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    AccessTokenDatabase db;
    static List<AccessToken> list = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = AccessTokenDatabase.getAppDatabase(this);
    }

    public void moveMainPage(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void moveTestConfig(View v){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void moveRegisterPage(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

        new confirmAsyncTask(db.accessTokenDao()).execute();

        String str = "";
        for (int i = 0; i < list.size(); i++) {
            str = "" + list.get(i).getAccessToken() + "\n" + str;
        }

        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                .setTitle("Room 내용")
                .setMessage(str);
        AlertDialog msg = msgBuilder.create();
        msg.show();
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
    }
}

