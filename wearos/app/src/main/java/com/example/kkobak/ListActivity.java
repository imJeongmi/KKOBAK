package com.example.kkobak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

        getTodoList(accessToken); // todo-list 목록 가져옴

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
//                Toast.makeText(getApplicationContext(), "임시적으로 HEART로 연결" + pos, Toast.LENGTH_SHORT).show();
                Todo clickTodo = todoList.get(pos);

                //======================================분기처리======================================

                long chlId = clickTodo.getChlId();
                Intent intent;

                if(chlId>0){ // challenge
                    long dc = clickTodo.getDetailCategoryId();
                    if(dc==1 || dc==2){ // 달리기 KM, 걷기 KM
                        intent = new Intent(ListActivity.this, RunActivity.class);
                    }
                    else if (dc==3){ // 명상 분
                        intent = new Intent(ListActivity.this, HeartrateActivity.class);
                    }
                    else if (dc==4 || dc==5 || dc==6 || dc==0){ // 물마시기 회, 영양제 먹기 회, 일어서기 회
                        // countactivity 없음 이후 변경하기 ===================================================
                        intent = new Intent(ListActivity.this, CheckActivity.class);
                    }
                    else { // dc==7, 출석
                        intent = new Intent(ListActivity.this, GpsActivity.class);
                    }
                }
                else { // todo-list
                    intent = new Intent(ListActivity.this, CheckActivity.class);
                }

                //==================================================================================

                intent.putExtra("chlId",clickTodo.getChlId());
                intent.putExtra("detailCategory",clickTodo.getDetailCategoryId());
                intent.putExtra("title",clickTodo.getTitle());
                intent.putExtra("contents",clickTodo.getContents());
                intent.putExtra("done", clickTodo.getDone());
                intent.putExtra("goal",clickTodo.getGoal());
                intent.putExtra("id",clickTodo.getId());
                startActivity(intent);
            }
        });

        todoAdapter.setOnLongItemClickListener(new TodoAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int pos) {
//                Toast.makeText(getApplicationContext(), "임시적으로 GPS로 연결", Toast.LENGTH_SHORT).show();
                Todo clickTodo = todoList.get(pos);
                Intent intent = new Intent(ListActivity.this, RunActivity.class);
                intent.putExtra("chlId",clickTodo.getChlId());
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
                        Todo todo = new Todo(
                            res.getChlId(), res.getCategoryId(), res.getDetailCategoryId(),
                                res.getContents(), res.isDone(), res.getTitle(), res.getGoal(),
                                res.getUnit());
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