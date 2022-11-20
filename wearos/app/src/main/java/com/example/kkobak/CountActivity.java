package com.example.kkobak;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kkobak.repository.util.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountActivity extends Activity {

    // 출력용 뷰
    TextView title, status, maxView, counter;
    ImageButton btn_count_up,btn_count_down;

    // 리스트에서 넘어온 데이터
    Intent intent;
    private Long chlId;

    // 액세스 토큰
    private String accessToken;
    //
    int max = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        // 뷰  컴포넌트 연결
        title = findViewById(R.id.title_count);
        maxView = findViewById(R.id.max_count);
        counter = findViewById(R.id.counter_count);
        status = findViewById(R.id.status_count);
        btn_count_up = findViewById(R.id.btn_count_up);
        btn_count_down = findViewById(R.id.btn_count_down);

        // 토큰 세팅
        accessToken = ((KkobakApp)getApplication()).getAccessToken();

        //인텐트에서 값 가져오기
        intent = getIntent();
        chlId = intent.getLongExtra("chlId",0);
        max = intent.getIntExtra("goal",-1);
        System.out.println(max);
        count = intent.getIntExtra("cnt",0);
        boolean done = intent.getBooleanExtra("done", true);
        title.setText(intent.getStringExtra("title"));
        maxView.setText(max+"");
        counter.setText(count+"");

        if(max<=count){
            status.setText("성공!");
            status.setTextColor(Color.parseColor("#32CD32"));
//            btn_count_up.setClickable(false);
        }

        // 버튼 연결 설정
        btn_count_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChlCheck(chlId,1);// 값 올리기
                count++;
                counter.setText(count+"");
                if(max<=count){
                    status.setText("성공!");
                    status.setTextColor(Color.parseColor("#32CD32"));
//                    btn_count_up.setClickable(false);
                }

            }
        });

        btn_count_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChlCheck(chlId,2);// 값 내리기
                count--;
                counter.setText(count+"");
                if(max<=count){
                    status.setText("성공!");
                    status.setTextColor(Color.parseColor("#32CD32"));
//                    btn_count_up.setClickable(false);
                }
                else{
                    status.setText("미완료");
                    status.setTextColor(Color.parseColor("#DB4455"));
                    btn_count_up.setClickable(true);
                }
                if(count==0){
                    btn_count_down.setClickable(false);
                }
            }
        });
        if(done){ // 이미 완료 상태라면
            status.setText("성공!");
            btn_count_up.setClickable(false);
        }
        else {
            status.setText("미완료");
            if(count == 0){
            btn_count_down.setClickable(false);
            }
        }
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
}
