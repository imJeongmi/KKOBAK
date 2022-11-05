package com.example.kkobak.ui.myChallengeDetail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.kkobak.R;
import com.example.kkobak.data.retrofit.api.MyChallengeDetailApi;
import com.example.kkobak.data.retrofit.model.MyChallengeDetailRes;
import com.example.kkobak.data.room.dao.AccessTokenDao;
import com.example.kkobak.data.room.database.AccessTokenDatabase;
import com.example.kkobak.data.room.entity.AccessToken;
import com.example.kkobak.ui.test.TestActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyChallengeDetailActivity extends AppCompatActivity {
    AccessTokenDatabase db;
    String accessToken;
    String chlId;
    Call<MyChallengeDetailRes> callDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychallengedetail);

        chlId = getIntent().getStringExtra("id");
        db = AccessTokenDatabase.getAppDatabase(this);
        try {
            accessToken = new TestActivity.getAccessTokenAsyncTask(db.accessTokenDao()).execute().get().getAccessToken();
        } catch (Exception e) {
            Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ImageView iv_img = findViewById(R.id.detailImg);
        TextView tv_categoryId = findViewById(R.id.detialCategoryId);
        TextView tv_detailCategoryId = findViewById(R.id.detialDetailCategoryId);
        TextView tv_writer = findViewById(R.id.detailWriter);
        TextView tv_title = findViewById(R.id.detailTitle);
        TextView tv_contents = findViewById(R.id.detailContents);
        TextView tv_watch = findViewById(R.id.detailWatch);
        TextView tv_roomType = findViewById(R.id.detailRoomType);
        TextView tv_password = findViewById(R.id.detailPassword);
        TextView tv_currentNum = findViewById(R.id.detailCurrentNum);
        TextView tv_alarm = findViewById(R.id.detailAlarm);
        TextView tv_goal = findViewById(R.id.detailGoal);
        TextView tv_unit = findViewById(R.id.detailUnit);
        TextView tv_nickname = findViewById(R.id.detailNickName);
        TextView tv_startTime = findViewById(R.id.detailStartTIme);
        TextView tv_endTime = findViewById(R.id.detailEndTime);
        TextView tv_tagList = findViewById(R.id.detailTagList);
        TextView tv_fin = findViewById(R.id.detailFin);

        callDetail = MyChallengeDetailApi.getDetailInfo().getInfo(accessToken, chlId);
        callDetail.enqueue(new Callback<MyChallengeDetailRes>() {
            @Override
            public void onResponse(Call<MyChallengeDetailRes> call, Response<MyChallengeDetailRes> response) {
                MyChallengeDetailRes info = response.body();

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
