package com.example.kkobak;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.kkobak.repository.response.TodoListResponse;
import com.example.kkobak.repository.util.RetrofitClient;
import com.example.kkobak.room.dao.AccessTokenDao;
import com.example.kkobak.room.dao.TodoDao;
import com.example.kkobak.room.data.Todo;
import com.example.kkobak.room.data.AccessToken;
import com.example.kkobak.room.db.AppDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends Activity {

    private AccessTokenDao tokenDao;
    private TodoDao todoDao;
    private String accessToken;

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

                    System.out.println("TODO SIZE: "+todoList.size());
                }
            }

            @Override
            public void onFailure(Call<List<TodoListResponse>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}