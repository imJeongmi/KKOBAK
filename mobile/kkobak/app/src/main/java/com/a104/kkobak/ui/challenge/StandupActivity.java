package com.a104.kkobak.ui.challenge;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.a104.kkobak.R;
import com.a104.kkobak.data.retrofit.api.ChallengeChkApi;
import com.a104.kkobak.data.room.dao.AccessTokenDao;
import com.a104.kkobak.data.room.database.AccessTokenDatabase;
import com.a104.kkobak.data.room.entity.AccessToken;
import com.a104.kkobak.ui.util.DatabaseAccess;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StandupActivity extends AppCompatActivity {
    AccessTokenDatabase db;
    String accessToken;

    String chlId;
    int cnt, goal;

    TextView tv_cnt;
    TextView tv_goal;
    ImageView iv_standup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standup);

        Intent intent = getIntent();
        if (intent.getStringExtra("chlId") != null)
            chlId = intent.getStringExtra("chlId");
        else
            chlId = "-1";
        cnt = goal = -1;
        if (intent.getIntExtra("cnt", 0) != 0)   cnt = intent.getIntExtra("cnt", 0);
        if (intent.getIntExtra("goal", 0) != 0)  goal = intent.getIntExtra("goal", 0);
        if (cnt < 0)    cnt = 0;
        if (goal < 0)   goal = 0;
        if (cnt > goal) cnt = goal;


        tv_cnt = findViewById(R.id.standupCnt);
        tv_goal = findViewById(R.id.standupGoal);
        iv_standup = findViewById(R.id.standupImg);

        tv_cnt.setText("현재 횟수: " + cnt);
        tv_goal.setText("목표 횟수: " + goal);

        changeImgBaseStatus();

        db = AccessTokenDatabase.getAppDatabase(this);
        try {
            accessToken = new DatabaseAccess.getAccessTokenAsyncTask(db.accessTokenDao()).execute().get().getAccessToken();
        } catch (Exception e) {
//            Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Toast.makeText(this, "cnt: " + cnt , Toast.LENGTH_SHORT).show();

        Call<Boolean> call = ChallengeChkApi.getService().setCnt(accessToken, Integer.parseInt(chlId), cnt);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                if (response.code() != 200)
//                    Toast.makeText(StandupActivity.this, "이상함", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(StandupActivity.this, "성공", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void changeImgBaseStatus() {
        if (cnt == -1 || goal == -1 || goal == 0)    return;
        int quot = (int)((double)cnt / goal * 100);

//        Toast.makeText(this, "" + quot, Toast.LENGTH_SHORT).show();
        if (quot >= 100)
            Glide.with(this).load(R.drawable.stand_upfinish).into(iv_standup);
        else if (quot >= 75)
            Glide.with(this).load(R.drawable.stand_up75).into(iv_standup);
        else if (quot >= 50)
            Glide.with(this).load(R.drawable.stand_up50).into(iv_standup);
        else if (quot >= 25)
            Glide.with(this).load(R.drawable.stand_up25).into(iv_standup);
        else
            Glide.with(this).load(R.drawable.stand_up0).into(iv_standup);
    }

    public void standupPlus(View v) {
        if (cnt >= goal)    return;
        ++cnt;
        if (cnt == goal) {
            Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
            vib.vibrate(1000);
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
            ringtone.play();

            showResult();
        }
        tv_cnt.setText("현재 횟수: " + cnt);
        changeImgBaseStatus();
    }

    public void standupMinus(View v) {
        if (cnt <= 0)   return;
        --cnt;
        tv_cnt.setText("현재 횟수: " + cnt);
        changeImgBaseStatus();
    }

    public void showResult() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alter_dialog, null);
        builder.setView(view)
                .create();

        TextView title = view.findViewById(R.id.dialogTitle);
        TextView content = view.findViewById(R.id.dialogContent);
        ImageView confirm = view.findViewById(R.id.layoutConfirm);


        title.setText("축하합니다!");
        content.setText("오늘의 일어서기 목표를 달성했습니다.");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        builder.show();
    }
}
