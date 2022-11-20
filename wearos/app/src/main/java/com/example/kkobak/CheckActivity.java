package com.example.kkobak;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kkobak.repository.util.RetrofitClient;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckActivity extends Activity {

    // 출력용 뷰
    TextView title, status;
//    Button btn_check_start;
//    Button btn_check_end;
    ImageButton btn_check;

    // 리스트에서 넘어온 데이터
    Intent intent;
    private Long chlId;
    boolean done;

    // 액세스 토큰
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        // 뷰  컴포넌트 연결
        title = findViewById(R.id.title_check);
        status = findViewById(R.id.status_check);
//        btn_check_start = findViewById(R.id.btn_check_start);
//        btn_check_end = findViewById(R.id.btn_check_end);
        btn_check = findViewById(R.id.btn_check);

        // 토큰 세팅
        accessToken = ((KkobakApp)getApplication()).getAccessToken();

        //인텐트에서 값 가져오기
        intent = getIntent();
        chlId = intent.getLongExtra("chlId",0);

        done = intent.getBooleanExtra("done", true);
        title.setText(intent.getStringExtra("title"));

        Drawable unchk = getResources().getDrawable( R.drawable.noncheck_button );
        Drawable chk = getResources().getDrawable( R.drawable.check_button );


        if(done){
            status.setText("성공!");
            btn_check.setImageDrawable(unchk);
            status.setTextColor(Color.parseColor("#32CD32"));
        }
        else {
            status.setText("미완료");
            btn_check.setImageDrawable(chk);
            status.setTextColor(Color.parseColor("#DB4455"));
        }

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!done){
                    if(chlId>0){// 챌린지
                        sendChlCheck(chlId,1);
                    }
                    else if(chlId<0){ // todoList
                        sendTodoChange(-1L*chlId);
                    }
                    status.setText("성공!");
                    btn_check.setImageDrawable(unchk);
                    done=true;
                    status.setTextColor(Color.parseColor("#32CD32"));

                }
                else {
                    if(chlId>0){ // 챌린지
                        sendChlCheck(chlId,2);
                    }
                    else if(chlId<0){ // todoList
                        sendTodoChange(-1L*chlId);
                    }
                    done=false;
                    status.setText("미완료");
                    btn_check.setImageDrawable(chk);
                    status.setTextColor(Color.parseColor("#DB4455"));
                }
            }
        });

    }
    @Override
    protected void onResume(){
        super.onResume();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void sendChlCheck(Long chlId, int type){
        Call<Boolean> call = RetrofitClient.getApiService().sendChlCheckChange(chlId, type, accessToken);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    public void sendTodoChange(Long chlId){
        System.out.println("투두 호출: " +chlId);
        Call<Boolean> call = RetrofitClient.getApiService().sendTodoStatusChange(chlId, accessToken);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
