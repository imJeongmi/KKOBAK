package com.example.kkobak.ui.myChallengeDetail;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.kkobak.R;
import com.example.kkobak.data.retrofit.api.ChallengeChkApi;
import com.example.kkobak.data.retrofit.api.LogChkApi;
import com.example.kkobak.data.retrofit.api.MyChallengeDetailApi;
import com.example.kkobak.data.retrofit.model.CallengeChkRes;
import com.example.kkobak.data.retrofit.model.LogChkReq;
import com.example.kkobak.data.retrofit.model.MyChallengeDetailRes;
import com.example.kkobak.data.room.dao.AccessTokenDao;
import com.example.kkobak.data.room.database.AccessTokenDatabase;
import com.example.kkobak.data.room.entity.AccessToken;
import com.example.kkobak.ui.challenge.AttendActivity;
import com.example.kkobak.ui.challenge.GpsActivity;
import com.example.kkobak.ui.challenge.MeditationActivity;
import com.example.kkobak.ui.challenge.PillActivity;
import com.example.kkobak.ui.challenge.StandupActivity;
import com.example.kkobak.ui.challenge.WaterActivity;
import com.example.kkobak.ui.main.MainActivity;
import com.example.kkobak.ui.test.TestActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyChallengeDetailActivity extends AppCompatActivity {
    AccessTokenDatabase db;
    String accessToken;

    String chlId;
    int cnt;
    int goal;


    Call<MyChallengeDetailRes> callDetail;

    MyChallengeDetailRes info;

    final int RUNNING = 1;
    final int WALKING = 2;
    final int MEDITATION = 3;
    final int DRINK_WATER = 4;
    final int EAT_NUTRIENTS = 5;
    final int STAND_UP = 6;
    final int ATTEND = 7;

    ImageView iv_img;
    TextView tv_categoryId;
    TextView tv_detailCategoryId;
    TextView tv_writer;
    TextView tv_title;
    TextView tv_contents;
    TextView tv_watch;
    TextView tv_roomType;
    TextView tv_password;
    TextView tv_currentNum;
    TextView tv_alarm;
    TextView tv_goal;
    TextView tv_unit;
    TextView tv_nickname;
    TextView tv_startTime;
    TextView tv_endTime;
    TextView tv_tagList;
    TextView tv_fin;

    Button detailBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychallengedetail);

        chlId = getIntent().getStringExtra("id");
        Toast.makeText(this, "chlId: " + chlId, Toast.LENGTH_SHORT).show();

        db = AccessTokenDatabase.getAppDatabase(this);
        try {
            accessToken = new TestActivity.getAccessTokenAsyncTask(db.accessTokenDao()).execute().get().getAccessToken();
        } catch (Exception e) {
            Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show();
        }

//        Toast.makeText(this, "" + new LogChkReq().makeDate(), Toast.LENGTH_SHORT).show();
        Call<Boolean> call = LogChkApi.getLogChkService().isValid(accessToken, new LogChkReq(Long.parseLong(chlId), LogChkReq.makeDate()));
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MyChallengeDetailActivity.this, "에러: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
//                    Toast.makeText(MyChallengeDetailActivity.this, "성공: " + response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        iv_img = findViewById(R.id.detailImg);
        tv_categoryId = findViewById(R.id.detialCategoryId);
        tv_detailCategoryId = findViewById(R.id.detialDetailCategoryId);
        tv_writer = findViewById(R.id.detailWriter);
        tv_title = findViewById(R.id.detailTitle);
        tv_contents = findViewById(R.id.detailContents);
        tv_watch = findViewById(R.id.detailWatch);
        tv_roomType = findViewById(R.id.detailRoomType);
        tv_password = findViewById(R.id.detailPassword);
        tv_currentNum = findViewById(R.id.detailCurrentNum);
        tv_alarm = findViewById(R.id.detailAlarm);
        tv_goal = findViewById(R.id.detailGoal);
        tv_unit = findViewById(R.id.detailUnit);
        tv_nickname = findViewById(R.id.detailNickName);
        tv_startTime = findViewById(R.id.detailStartTIme);
        tv_endTime = findViewById(R.id.detailEndTime);
        tv_tagList = findViewById(R.id.detailTagList);
        tv_fin = findViewById(R.id.detailFin);

        detailBtn = findViewById(R.id.challengeDetailBtn);

        callDetail = MyChallengeDetailApi.getDetailInfo().getInfo(accessToken, chlId);
        callDetail.enqueue(new Callback<MyChallengeDetailRes>() {
            @Override
            public void onResponse(Call<MyChallengeDetailRes> call, Response<MyChallengeDetailRes> response) {
                info = response.body();

                String testImgUrl = "https://static.wikia.nocookie.net/pokemon/images/3/3c/%EB%9D%BC%EC%9D%B4%EC%B8%84_%EA%B3%B5%EC%8B%9D_%EC%9D%BC%EB%9F%AC%EC%8A%A4%ED%8A%B8.png/revision/latest?cb=20170405000124&path-prefix=ko";
                String imgUrl = info.getImgUrl();
                if ("".equals(imgUrl) || imgUrl == null || "string".equals(imgUrl))    imgUrl = testImgUrl;
                else if (imgUrl.contains("google")) imgUrl = testImgUrl;
                Glide.with(getApplicationContext()).load(imgUrl).into(iv_img);

                tv_categoryId.setText("categoryId: " + info.getCategoryId());
                tv_detailCategoryId.setText("detailCategoryId: " + info.getDetailCategoryId());
                tv_writer.setText("writer:"  + info.getWriter());
                tv_title.setText("title: " + info.getTitle());
                tv_contents.setText("contents: " + info.getTitle());
                tv_watch.setText("watch: " + (info.isWatch() ? "사용" : "사용x"));
                tv_roomType.setText("roomType: " + info.getRoomType());
                tv_password.setText("password: " + info.getPassword());
                tv_currentNum.setText("currentNum: " + info.getCurrentNum());
                tv_alarm.setText("alarm: " + info.getAlarm());
                tv_goal.setText("goal: " + info.getGoal());
                tv_unit.setText("unit: " + info.getUnit());
                tv_nickname.setText("nickname: " + info.getNickName());
                tv_startTime.setText("startTime: " + info.getStartTime());
                tv_endTime.setText("endTime: " + info.getEndTime());
                tv_tagList.setText("tagList: " + info.getTagList().toString());
                tv_fin.setText("fin: " + (info.isFin() ? "완료" : "미완료"));
            }

            @Override
            public void onFailure(Call<MyChallengeDetailRes> call, Throwable t) {

            }
        });

        cnt = goal = -1;
        changeBtnStatus();
    }

    public void changeBtnStatus() {
        Call<CallengeChkRes> call = ChallengeChkApi.getService(). getStatusInfo(accessToken, Integer.parseInt(chlId));
        call.enqueue(new Callback<CallengeChkRes>() {
            @Override
            public void onResponse(Call<CallengeChkRes> call, Response<CallengeChkRes> response) {
                if (response.body().isDone()) {
                    detailBtn.setText("이미 했음");
//                    detailBtn.setClickable(false);
                }

                goal = response.body().getGoal();
                cnt = response.body().getCnt();
            }

            @Override
            public void onFailure(Call<CallengeChkRes> call, Throwable t) {}
        });
    }

    public void doChallenge(View v) {
        Intent intent;

        switch (info.getDetailCategoryId()) {
            case RUNNING:
                Toast.makeText(this, "Running", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, GpsActivity.class);
                intent.putExtra("title", "달리기");
                if (goal != -1) intent.putExtra("goal", goal);
                break;
            case WALKING:
                Toast.makeText(this, "Walking", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, GpsActivity.class);
                intent.putExtra("title", "걷기");
                if (goal != -1) intent.putExtra("goal", goal);
                break;
            case MEDITATION:
                Toast.makeText(this, "Meditation", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, MeditationActivity.class);
                if (goal != -1) intent.putExtra("goal", goal);
                break;
            case DRINK_WATER:
                Toast.makeText(this, "Drink Water", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, WaterActivity.class);
                if (cnt != -1)  intent.putExtra("cnt", cnt);
                if (goal != -1) intent.putExtra("goal", goal);
                break;
            case EAT_NUTRIENTS:
                Toast.makeText(this, "Nutrients", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, PillActivity.class);
                if (cnt != -1)  intent.putExtra("cnt", cnt);
                if (goal != -1) intent.putExtra("goal", goal);
                break;
            case STAND_UP:
                Toast.makeText(this, "Stand up", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, StandupActivity.class);
                if (cnt != -1)  intent.putExtra("cnt", cnt);
                if (goal != -1) intent.putExtra("goal", goal);
                break;
            case ATTEND:
                Toast.makeText(this, "Attend", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, AttendActivity.class);
                String[] location = info.getUnit().split(",");
                if (location.length == 2) {
                    intent.putExtra("lon", location[0]);
                    intent.putExtra("lat", location[1]);
                }
                break;
            default:
                Toast.makeText(this, "기타", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, MainActivity.class);
                break;
        }
        intent.putExtra("chlId", chlId);
        startActivity(intent);
        finish();
    }

    public static class getAccessTokenAsyncTask extends AsyncTask<Void, Void, AccessToken> {
        private final AccessTokenDao accessTokenDao;

        public getAccessTokenAsyncTask(AccessTokenDao accessTokenDao) {
            this.accessTokenDao = accessTokenDao;
        }

        @Override
        protected AccessToken doInBackground(Void... voids) {
            List<AccessToken> tokens = accessTokenDao.getAll();
            if (tokens == null || tokens.size() == 0)
                return (null);
            else
                return (tokens.get(0));
        }
    }
}
