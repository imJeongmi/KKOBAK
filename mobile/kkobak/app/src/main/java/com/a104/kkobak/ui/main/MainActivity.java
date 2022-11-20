package com.a104.kkobak.ui.main;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a104.kkobak.R;
import com.a104.kkobak.data.retrofit.api.ChallengeChkApi;
import com.a104.kkobak.data.retrofit.api.MemberInfoApi;
import com.a104.kkobak.data.retrofit.model.ChallengeAllDataRes;
import com.a104.kkobak.data.retrofit.model.MemberInfoRes;
import com.a104.kkobak.data.room.database.AccessTokenDatabase;
import com.a104.kkobak.ui.challenge.AttendActivity;
import com.a104.kkobak.ui.challenge.GpsActivity;
import com.a104.kkobak.ui.challenge.MeditationActivity;
import com.a104.kkobak.ui.challenge.PillActivity;
import com.a104.kkobak.ui.challenge.StandupActivity;
import com.a104.kkobak.ui.challenge.WaterActivity;
import com.a104.kkobak.ui.member.LoginActivity;
import com.a104.kkobak.ui.util.DatabaseAccess;
import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    AccessTokenDatabase db;
    String accessToken;

    List<ChallengeAllDataRes> allData;

    final int RUNNING = 1;
    final int WALKING = 2;
    final int MEDITATION = 3;
    final int DRINK_WATER = 4;
    final int EAT_NUTRIENTS = 5;
    final int STAND_UP = 6;
    final int ATTEND = 7;

    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;
    private TextView tv_email;
    private TextView tv_nickname;
    private ImageView iv_img;

    ProgressDialog loadingBar;

    int nowPos;
    CompositePageTransformer compositePageTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderViewPager = findViewById(R.id.sliderViewPager);
        layoutIndicator = findViewById(R.id.layoutIndicators);

        tv_email = findViewById(R.id.mainEmail);
        tv_nickname = findViewById(R.id.mainNickname);
        iv_img = findViewById(R.id.mainImg);

        db = AccessTokenDatabase.getAppDatabase(this);
        try {
            accessToken = new DatabaseAccess.getAccessTokenAsyncTask(db.accessTokenDao()).execute().get().getAccessToken();
        } catch (Exception e) {}

        float offsetBetweenPages = getResources().getDimensionPixelOffset(R.dimen.offsetBetweenPages);

        compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float myOffset = position * - (2 * offsetBetweenPages);
                if (position < - 1) {
                    page.setTranslationX(-myOffset);
                }
                else if (position <= 1) {
                    float f = 1 - Math.abs(position);
                    page.setScaleY(0.8f + f * 0.2f);
                    page.setAlpha(0.8f + f * 0.2f);
                    page.setTranslationX(myOffset);
                }
                else {
                    page.setAlpha(0f);
                    page.setTranslationX(myOffset);
                }
            }
        });

        MemberInfoApi.getService().getInfo(accessToken).enqueue(new Callback<MemberInfoRes>() {
            @Override
            public void onResponse(Call<MemberInfoRes> call, Response<MemberInfoRes> response) {
                if (response.code() == 200) {
                    tv_email.setText(response.body().getEmail());
                    tv_nickname.setText(response.body().getNickName());
                    if (response.body().getImgUrl() == null || "".equals(response.body().getImgUrl()))
                        Glide.with(getApplicationContext()).load(R.drawable.human).into(iv_img);
                    else {
                        switch (Integer.parseInt(response.body().getImgUrl())) {
                            case 1:
                                Glide.with(getApplicationContext()).load(R.drawable.avatar_1).into(iv_img);
                                break;
                            case 2:
                                Glide.with(getApplicationContext()).load(R.drawable.avatar_2).into(iv_img);
                                break;
                            case 3:
                                Glide.with(getApplicationContext()).load(R.drawable.avatar_3).into(iv_img);
                                break;
                            case 4:
                                Glide.with(getApplicationContext()).load(R.drawable.avatar_4).into(iv_img);
                                break;
                            case 5:
                                Glide.with(getApplicationContext()).load(R.drawable.avatar_5).into(iv_img);
                                break;
                            case 6:
                                Glide.with(getApplicationContext()).load(R.drawable.avatar_6).into(iv_img);
                                break;
                            case 7:
                                Glide.with(getApplicationContext()).load(R.drawable.avatar_7).into(iv_img);
                                break;
                            case 8:
                                Glide.with(getApplicationContext()).load(R.drawable.avatar_8).into(iv_img);
                                break;
                            case 9:
                                Glide.with(getApplicationContext()).load(R.drawable.avatar_9).into(iv_img);
                                break;
                            default:
                                Glide.with(getApplicationContext()).load(R.drawable.human).into(iv_img);
                                break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberInfoRes> call, Throwable t) {}
        });

        ChallengeChkApi.getService().getAllData(accessToken).enqueue(new Callback<List<ChallengeAllDataRes>>() {
            @Override
            public void onResponse(Call<List<ChallengeAllDataRes>> call, Response<List<ChallengeAllDataRes>> response) {
                if (response.code() == 200) {
                    allData = response.body();

                    Collections.sort(allData, new Comparator<ChallengeAllDataRes>() {
                        @Override
                        public int compare(ChallengeAllDataRes a, ChallengeAllDataRes b) {
                            return b.getId() - a.getId();
                        }
                    });

                    sliderViewPager.setOffscreenPageLimit(3);
                    sliderViewPager.setPageTransformer(compositePageTransformer);
                    sliderViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                    sliderViewPager.setAdapter(new ImageSliderAdapter(getApplicationContext(), allData));
                    sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            setCurrentIndicator(position);
                        }
                    });
                    setupIndicators(allData.size());
                }
            }

            @Override
            public void onFailure(Call<List<ChallengeAllDataRes>> call, Throwable t) {}
        });

        nowPos = 0;
        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                nowPos = position;
            }
        });

        loadingBar = new ProgressDialog(this, AlertDialog.THEME_HOLO_DARK);
        loadingBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingBar.setMessage("Wait a minute...");
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadingBar.show();

        ChallengeChkApi.getService().getAllData(accessToken).enqueue(new Callback<List<ChallengeAllDataRes>>() {
            @Override
            public void onResponse(Call<List<ChallengeAllDataRes>> call, Response<List<ChallengeAllDataRes>> response) {
                if (response.code() == 200) {
                    allData = response.body();

                    Collections.sort(allData, new Comparator<ChallengeAllDataRes>() {
                        @Override
                        public int compare(ChallengeAllDataRes a, ChallengeAllDataRes b) {
                            return b.getId() - a.getId();
                        }
                    });
                    sliderViewPager.setAdapter(new ImageSliderAdapter(getApplicationContext(), allData));

                    loadingBar.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<ChallengeAllDataRes>> call, Throwable t) {}
        });
    }



    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }

    public void moveDetail(View v) {
        int goal, cnt;

        goal = cnt = -1;

        goal = allData.get(nowPos).getGoal();
        cnt = allData.get(nowPos).getCnt();

        Intent intent;

        switch (allData.get(nowPos).getDetailCategoryId()) {
            case RUNNING:
                intent = new Intent(this, GpsActivity.class);
                intent.putExtra("title", "달리기");
                intent.putExtra("goal", goal);
                break;
            case WALKING:
                intent = new Intent(this, GpsActivity.class);
                intent.putExtra("title", "걷기");
                intent.putExtra("goal", goal);
                break;
            case MEDITATION:
                intent = new Intent(this, MeditationActivity.class);
                intent.putExtra("goal", goal);
                break;
            case DRINK_WATER:
                intent = new Intent(this, WaterActivity.class);
                intent.putExtra("cnt", cnt);
                intent.putExtra("goal", goal);
                break;
            case EAT_NUTRIENTS:
                intent = new Intent(this, PillActivity.class);
                intent.putExtra("cnt", cnt);
                intent.putExtra("goal", goal);
                break;
            case STAND_UP:
                intent = new Intent(this, StandupActivity.class);
                intent.putExtra("cnt", cnt);
                intent.putExtra("goal", goal);
                break;
            case ATTEND:
                intent = new Intent(this, AttendActivity.class);
                String[] location = allData.get(nowPos).getUnit().split(",");
                if (location.length == 2) {
                    intent.putExtra("lon", location[0]);
                    intent.putExtra("lat", location[1]);
                }
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                break;
        }
        intent.putExtra("chlId", String.valueOf(allData.get(nowPos).getId()));
        startActivity(intent);
    }

    public void doLogout(View v) {
        new DatabaseAccess.LogoutAsyncTask(db.accessTokenDao()).execute();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
