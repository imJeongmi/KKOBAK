package com.example.kkobak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import com.example.kkobak.repository.response.TodoListResponse;
import com.example.kkobak.repository.util.RetrofitClient;
import com.example.kkobak.room.adapter.TodoAdapter;
import com.example.kkobak.room.dao.AccessTokenDao;
import com.example.kkobak.room.dao.TodoDao;
import com.example.kkobak.room.data.AccessToken;
import com.example.kkobak.room.data.Todo;
import com.example.kkobak.room.db.AppDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends Activity {

    private AccessTokenDao tokenDao;
    private TodoDao todoDao;
    private String accessToken;
    private WearableRecyclerView rv;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        AppDatabase database = AppDatabase.getInstance(getApplicationContext());

        tokenDao = database.tokenDao();
        todoDao = database.todoDao();
        List<AccessToken> tokenList = tokenDao.getTokenAll();
        accessToken = tokenList.get(0).getAccessToken();

        getTodoList(accessToken);

        // WearableRecyclerView ------------------------------------

        rv = (WearableRecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);

        List<Todo> todoList = todoDao.getListAll();

        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);

        TodoAdapter todoAdapter = new TodoAdapter(getApplication(), todoList);


        todoAdapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(getApplicationContext(), "임시적으로 HEART로 연결" + pos, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListActivity.this, HeartrateActivity.class);
//                intent.putExtra("chlId",)
                startActivity(intent);
            }
        });

        todoAdapter.setOnLongItemClickListener(new TodoAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int pos) {
                Toast.makeText(getApplicationContext(), "임시적으로 GPS로 연결", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListActivity.this, GpsActivity.class);
                startActivity(intent);
            }
        });


        rv.setAdapter(todoAdapter);

        // ---------------------------------------------------------
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

                    List<Todo> todoDataList = todoDao.getListAll();
                    if(todoList.size()!=0){
                        for(int i=todoDataList.size();i>0;i--){
                            Todo todoRes = todoDataList.get(i-1);
                            todoDao.deleteTodo(todoRes.getId());
                        }
                    }

                    for(TodoListResponse res : todoList){
                        Todo todo = new Todo(res.getChlId(), res.isDone(), res.getTitle());
                        todoDao.setInsertTodo(todo);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TodoListResponse>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}