package com.example.kkobak.ui.dashboard;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.viewpager.widget.ViewPager;

import com.example.kkobak.R;
import com.example.kkobak.data.retrofit.api.ChallengeChkApi;
import com.example.kkobak.data.retrofit.api.MyChallengeApi;
import com.example.kkobak.data.retrofit.api.MyChallengeDetailApi;
import com.example.kkobak.data.retrofit.model.CallengeChkRes;
import com.example.kkobak.data.retrofit.model.MyChallengeDetailRes;
import com.example.kkobak.data.retrofit.model.MyChallengeRes;
import com.example.kkobak.data.room.dao.AccessTokenDao;
import com.example.kkobak.data.room.database.AccessTokenDatabase;
import com.example.kkobak.data.room.entity.AccessToken;
import com.example.kkobak.ui.dashboard.fragments.ExpandingFragment;
import com.example.kkobak.ui.test.TestActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity implements ExpandingFragment.OnExpandingClickListener {
    AccessTokenDatabase db;
    String accessToken;

    ViewPager viewPager;
    ViewGroup back;

    List<MyChallengeRes> challengeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        viewPager = findViewById(R.id.viewPager);
        back = findViewById(R.id.back);

        ButterKnife.bind(this);
        setupWindowAnimations();

        db = AccessTokenDatabase.getAppDatabase(this);
        try {
            accessToken = new TestActivity.getAccessTokenAsyncTask(db.accessTokenDao()).execute().get().getAccessToken();
        } catch (Exception e) {
            Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show();
        }

//        challengeList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Call<List<MyChallengeRes>> call = MyChallengeApi.getMyChallengeService().getMyChallenge(accessToken);
                    call.enqueue(new Callback<List<MyChallengeRes>>() {
                        @Override
                        public void onResponse(Call<List<MyChallengeRes>> call, Response<List<MyChallengeRes>> response) {
                            if (response.code() == 200) {
                                challengeList = response.body();
                            }
                            else {
                                challengeList = new ArrayList<>();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<MyChallengeRes>> call, Throwable t) {
                            challengeList = new ArrayList<>();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//
//
//        ExpandingPagerFactory.setupViewPager(viewPager);
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                ExpandingFragment expandingFragment = ExpandingPagerFactory.getCurrentFragment(viewPager);
//                if(expandingFragment != null && expandingFragment.isOpenend()){
//                    expandingFragment.close();
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChallengeViewPagerAdapter adapter = new ChallengeViewPagerAdapter(getSupportFragmentManager());
        adapter.addAll(generateTravelList());
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if(!ExpandingPagerFactory.onBackPressed(viewPager)){
            super.onBackPressed();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Explode slideTransition = new Explode();
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }

    private List<ChallengeInfo> generateTravelList(){

        Toast.makeText(this, "size: " + challengeList.size(), Toast.LENGTH_SHORT);
        List<ChallengeInfo> info = new ArrayList<>();
//        for(int i = 0; i < challengeList.size(); ++i) {
//            ChallengeInfo data = new ChallengeInfo();
//            int id = challengeList.get(i).getId();
//
//            //detailCatergoryId
//            //goal
//            //contents
//            Call<MyChallengeDetailRes> detail = MyChallengeDetailApi.getDetailInfo().getInfo(accessToken, String.valueOf(id));
//            detail.enqueue(new Callback<MyChallengeDetailRes>() {
//                @Override
//                public void onResponse(Call<MyChallengeDetailRes> call, Response<MyChallengeDetailRes> response) {
//                    data.setGoal(response.body().getGoal());
//                    data.setDetailCategoryId(response.body().getDetailCategoryId());
//                    data.setContents(response.body().getContents());
//                }
//
//                @Override
//                public void onFailure(Call<MyChallengeDetailRes> call, Throwable t) {
//                    data.setGoal(-1);
//                    data.setDetailCategoryId(-1);
//                    data.setContents("");
//                }
//            });
//
//            //done
//            Call<Boolean> confirm = ChallengeChkApi.getService().getFinStatus(accessToken, id);
//            confirm.enqueue(new Callback<Boolean>() {
//                @Override
//                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                    data.setDone(response.body());
//                }
//
//                @Override
//                public void onFailure(Call<Boolean> call, Throwable t) {
//                    data.setDone(false);
//                }
//            });
//
//            data.setName((i + 1) + " 번째");
//            data.setImage(R.drawable.newyork);
//
//            data.setImgUrl(challengeList.get(i).getImgUrl());
//            data.setTitle(challengeList.get(i).getTitle());
//            data.setEndTime(challengeList.get(i).getEndTime());
//            data.setWatch(challengeList.get(i).isWatch());
//            data.setId(id);
//
//            info.add(data);
////            info.add(new ChallengeInfo("1번도시", R.drawable.seychelles));
////            info.add(new ChallengeInfo("2번도시", R.drawable.shh));
////            info.add(new ChallengeInfo("3번도시", R.drawable.newyork));
////            info.add(new ChallengeInfo("4번도시", R.drawable.p1));
//        }
            info.add(new ChallengeInfo("1번도시", R.drawable.seychelles));
            info.add(new ChallengeInfo("2번도시", R.drawable.shh));
            info.add(new ChallengeInfo("3번도시", R.drawable.newyork));
            info.add(new ChallengeInfo("4번도시", R.drawable.p1));
        return (info);
    }
    @SuppressWarnings("unchecked")
    private void startInfoActivity(View view, ChallengeInfo travel) {
        Activity activity = this;
        ActivityCompat.startActivity(activity,
                InfoActivity.newInstance(activity, travel),
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                new Pair<>(view, getString(R.string.transition_image)))
                        .toBundle());
    }

    @Override
    public void onExpandingClick(View v) {
        //v is expandingfragment layout
        View view = v.findViewById(R.id.image);
        ChallengeInfo challengeInfo = generateTravelList().get(viewPager.getCurrentItem());
        startInfoActivity(view, challengeInfo);
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
