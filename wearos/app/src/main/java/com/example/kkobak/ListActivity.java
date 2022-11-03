package com.example.kkobak;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.kkobak.repository.response.TodoListResponse;
import com.example.kkobak.repository.util.RetrofitClient;
import com.example.kkobak.room.dao.AccessTokenDao;
import com.example.kkobak.room.db.AccessToken;
import com.example.kkobak.room.db.AccessTokenDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        getTodoList(accessToken);

    }

    public void getTodoList(String accessToken){
        Call<List<TodoListResponse>> call = RetrofitClient.getApiService().getTodoList(accessToken);
        call.enqueue(new Callback<List<TodoListResponse>>() {
            @Override
            public void onResponse(Call<List<TodoListResponse>> call, Response<List<TodoListResponse>> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else{
                    List<TodoListResponse> todoList = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<TodoListResponse>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}