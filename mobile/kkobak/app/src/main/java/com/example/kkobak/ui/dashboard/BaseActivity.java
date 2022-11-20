package com.example.kkobak.ui.dashboard;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.kkobak.R;
import com.example.kkobak.data.retrofit.api.ChallengeChkApi;
import com.example.kkobak.data.retrofit.model.ChallengeAllDataRes;
import com.example.kkobak.data.room.dao.AccessTokenDao;
import com.example.kkobak.data.room.database.AccessTokenDatabase;
import com.example.kkobak.data.room.entity.AccessToken;
import com.example.kkobak.ui.challenge.AttendActivity;
import com.example.kkobak.ui.challenge.GpsActivity;
import com.example.kkobak.ui.challenge.MeditationActivity;
import com.example.kkobak.ui.challenge.PillActivity;
import com.example.kkobak.ui.challenge.StandupActivity;
import com.example.kkobak.ui.challenge.WaterActivity;
import com.example.kkobak.ui.dashboard.fragments.ExpandingFragment;
import com.example.kkobak.ui.main.MainActivity;
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

    List<ChallengeAllDataRes> allData;

    final int RUNNING = 1;
    final int WALKING = 2;
    final int MEDITATION = 3;
    final int DRINK_WATER = 4;
    final int EAT_NUTRIENTS = 5;
    final int STAND_UP = 6;
    final int ATTEND = 7;

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

        Call<List<ChallengeAllDataRes>> call = ChallengeChkApi.getService().getAllData(accessToken);
        call.enqueue(new Callback<List<ChallengeAllDataRes>>() {
            @Override
            public void onResponse(Call<List<ChallengeAllDataRes>> call, Response<List<ChallengeAllDataRes>> response) {
                allData = response.body();
                Toast.makeText(BaseActivity.this, "호출 성공: " + allData.size() , Toast.LENGTH_SHORT).show();

                for (int i = 0; i < allData.size(); i++){
                    System.out.println(">>>1 " + (i + 1) + " : " + allData.get(i));
                }

                ChallengeViewPagerAdapter adapter = new ChallengeViewPagerAdapter(getSupportFragmentManager());
                adapter.addAll(generateTravelList());

//                Toast.makeText(BaseActivity.this, "size: " + adapter.getCount(), Toast.LENGTH_SHORT).show();

//                ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
//                layoutParams.width = ((Activity) viewPager.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 7 * 5;
//                layoutParams.height = (int) ((layoutParams.width / 0.75));
//                viewPager.setOffscreenPageLimit(2);
//                viewPager.setCurrentItem(4);
//                if (viewPager.getParent() instanceof ViewGroup) {
//                    ViewGroup viewParent = ((ViewGroup) viewPager.getParent());
//                    viewParent.setClipChildren(false);
//                    viewPager.setClipChildren(false);
//                }
//                viewPager.setPageTransformer(true, new ExpandingViewPagerTransformer());

//                viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
                viewPager.setAdapter(adapter);

                ExpandingPagerFactory.setupViewPager(viewPager);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                        Toast.makeText(BaseActivity.this, "변경 감지감지감지: " + position, Toast.LENGTH_SHORT).show();
                        ExpandingFragment expandingFragment = ExpandingPagerFactory.getCurrentFragment(viewPager);
                        if(expandingFragment != null && expandingFragment.isOpenend()){
//                            expandingFragment.close();
                        }
                    }

                    @Override
                    public void onPageSelected(int position) {
//                        Toast.makeText(BaseActivity.this, "onPageSelected", Toast.LENGTH_SHORT).show();
                        Toast.makeText(BaseActivity.this, "?: " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
                        TextView tv = findViewById(R.id.title);
                        tv.setText("이거 되냐");
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
//                        Toast.makeText(BaseActivity.this, "onPageScrollStateChanged", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ChallengeAllDataRes>> call, Throwable t) {
                allData = new ArrayList<>();
            }
        });

//        challengeList = new ArrayList<>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Call<List<MyChallengeRes>> call = MyChallengeApi.getMyChallengeService().getMyChallenge(accessToken);
//                    call.enqueue(new Callback<List<MyChallengeRes>>() {
//                        @Override
//                        public void onResponse(Call<List<MyChallengeRes>> call, Response<List<MyChallengeRes>> response) {
//                            if (response.code() == 200) {
//                                challengeList = response.body();
//                            }
//                            else {
//                                challengeList = new ArrayList<>();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<MyChallengeRes>> call, Throwable t) {
//                            challengeList = new ArrayList<>();
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        try {
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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

        List<ChallengeInfo> info = new ArrayList<>();
        for(int i = 0; i < allData.size(); ++i) {
            ChallengeInfo data = new ChallengeInfo();

            data.setName((i + 1) + " 번째");
            data.setImage(R.drawable.newyork);

            data.setImgUrl(allData.get(i).getImgUrl());
            data.setDone(allData.get(i).isDone());
            data.setId(allData.get(i).getId());
            data.setDetailCategoryId(allData.get(i).getDetailCategoryId());
            data.setGoal(allData.get(i).getGoal());
            data.setTitle(allData.get(i).getTitle());
            data.setEndTime(allData.get(i).getEndTime());
            data.setContents(allData.get(i).getContents());
            data.setWatch(allData.get(i).isWatch());

            info.add(data);
        }
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
//            info.add(new ChallengeInfo("1번도시", R.drawable.seychelles));
//            info.add(new ChallengeInfo("2번도시", R.drawable.shh));
//            info.add(new ChallengeInfo("3번도시", R.drawable.newyork));
//            info.add(new ChallengeInfo("4번도시", R.drawable.p1));
        return (info);
    }

    @SuppressWarnings("unchecked")
    private void startInfoActivity(View view, ChallengeInfo travel) {
        Intent intent;

        //여기 손봐야함
        //추가 데이터들 넣어야하기 때문
        int cnt = 0;
        String unit = "123,123";

        switch (travel.getDetailCategoryId()) {
            case RUNNING:
                intent = new Intent(this, GpsActivity.class);
                intent.putExtra("title", "달리기");
                intent.putExtra("goal", travel.getGoal());
                break;
            case WALKING:
                intent = new Intent(this, GpsActivity.class);
                intent.putExtra("title", "걷기");
                intent.putExtra("goal", travel.getGoal());
                break;
            case MEDITATION:
                intent = new Intent(this, MeditationActivity.class);
                intent.putExtra("goal", travel.getGoal());
                break;
            case DRINK_WATER:
                intent = new Intent(this, WaterActivity.class);
                intent.putExtra("cnt", cnt);
                intent.putExtra("goal", travel.getGoal());
                break;
            case EAT_NUTRIENTS:
                intent = new Intent(this, PillActivity.class);
                intent.putExtra("cnt", cnt);
                intent.putExtra("goal", travel.getGoal());
                break;
            case STAND_UP:
                intent = new Intent(this, StandupActivity.class);
                intent.putExtra("cnt", cnt);
                intent.putExtra("goal", travel.getGoal());
                break;
            case ATTEND:
                intent = new Intent(this, AttendActivity.class);
                String[] location = unit.split(",");
                if (location.length == 2) {
                    intent.putExtra("lon", location[0]);
                    intent.putExtra("lat", location[1]);
                }
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                break;
        }

//        Toast.makeText(this,"설마 널이냐?" + travel.getDetailCategoryId(), Toast.LENGTH_SHORT);
//        Log.d("널이냐", "" + travel.getDetailCategoryId());
//        Intent it = new Intent(this, SpeedActivity.class);
        intent.putExtra("chlId", String.valueOf(travel.getId()));
        startActivity(intent);

//        Activity activity = this;
//        ActivityCompat.startActivity(activity,
//                InfoActivity.newInstance(activity, travel),
//                ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                activity,
//                                new Pair<>(view, getString(R.string.transition_image)))
//                        .toBundle());
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
