package com.example.kkobak;

import android.app.Activity;
import android.os.Bundle;

import com.example.kkobak.room.dao.AccessTokenDao;
import com.example.kkobak.room.db.AccessToken;
import com.example.kkobak.room.db.AccessTokenDatabase;

import java.util.List;

public class ListActivity extends Activity {

    private AccessTokenDao tokenDao;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        AccessTokenDatabase database = AccessTokenDatabase.getInstance(getApplicationContext());

        tokenDao = database.tokenDao();
        List<AccessToken> tokenList = tokenDao.getTokenAll();
        accessToken = tokenList.get(0).getAccessToken();

        System.out.println("accessToken: "+accessToken);

        

    }
}