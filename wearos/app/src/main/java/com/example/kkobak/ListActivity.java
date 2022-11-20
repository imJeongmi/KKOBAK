package com.example.kkobak;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
    private TextView text_todo;
    TodoAdapter todoAdapter;
    List<TodoListResponse> todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        text_todo=findViewById(R.id.text_todo);
//        text_todo.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        accessToken = ((KkobakApp)getApplication()).getAccessToken();

        rv = (WearableRecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);


        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);
    }

    // Resume을 하는 이유는 챌린지 or Todo에서 뒤로가기를 눌렀을때 목록을 업데이트하기 위해서
    @Override
    protected void onResume(){
        super.onResume();
        linearLayoutManager = new LinearLayoutManager(this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);
        getTodoList2(accessToken);
    }

    public void getTodoList2(String accessToken){
        Call<List<TodoListResponse>> call = RetrofitClient.getApiService().getTodoList(accessToken);
        call.enqueue(new Callback<List<TodoListResponse>>() {
            @Override
            public void onResponse(Call<List<TodoListResponse>> call, Response<List<TodoListResponse>> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else{
                    todoList = response.body();
                    for (TodoListResponse todoListResponse : todoList) {
                        System.out.println("ㅁㄴㅇㄹ"+todoListResponse.getGoal());
                    }
                    todoAdapter = new TodoAdapter(getApplication(), todoList);
                    todoAdapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            TodoListResponse clickTodo = todoList.get(pos);

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
                                    intent = new Intent(ListActivity.this, CountActivity.class);
                                }
                                else { // dc==7, 출석
                                    intent = new Intent(ListActivity.this, AttendanceActivity.class);
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
                            intent.putExtra("done", clickTodo.isDone());
                            intent.putExtra("goal",clickTodo.getGoal());
                            intent.putExtra("unit",clickTodo.getUnit());
                            intent.putExtra("cnt",clickTodo.getCnt());
                            startActivity(intent);
                        }
                    });
                    rv.setAdapter(todoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<TodoListResponse>> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

}