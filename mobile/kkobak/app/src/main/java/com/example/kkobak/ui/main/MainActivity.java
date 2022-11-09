package com.example.kkobak.ui.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.kkobak.R;
import com.example.kkobak.data.room.dao.AccessTokenDao;
import com.example.kkobak.data.room.database.AccessTokenDatabase;
import com.example.kkobak.data.room.entity.AccessToken;
import com.example.kkobak.databinding.ActivityMainBinding;
import com.example.kkobak.ui.challenge.AttendActivity;
import com.example.kkobak.ui.challenge.GpsActivity;
import com.example.kkobak.ui.challenge.MeditationActivity;
import com.example.kkobak.ui.challenge.WaterActivity;
import com.example.kkobak.ui.login.LoginActivity;
import com.example.kkobak.ui.test.SpeedActivity;
import com.example.kkobak.ui.test.TestActivity;
import com.example.kkobak.ui.test.TimerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    AccessTokenDatabase db;
    String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_home, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        db = AccessTokenDatabase.getAppDatabase(this);
        try {
            accessToken = new TestActivity.getAccessTokenAsyncTask(db.accessTokenDao()).execute().get().getAccessToken();
       } catch (Exception e) {
            Toast.makeText(this, "에러 발생", Toast.LENGTH_SHORT).show();
        }

    }

    public static class LogoutAsyncTask extends AsyncTask<Void, Void, Void> {
        private final AccessTokenDao accessTokenDao;

        public LogoutAsyncTask(AccessTokenDao accessTokenDao) {
            this.accessTokenDao = accessTokenDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            accessTokenDao.deleteAll();

            return null;
        }
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

    public void doLogout(View v) {
        Toast.makeText(this, "do Logout", Toast.LENGTH_SHORT).show();

        new LogoutAsyncTask(db.accessTokenDao()).execute();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void moveMyInfo(View v) {
        Toast.makeText(this, "내 정보 클릭", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void moveSpeed(View v) {
        Intent intent = new Intent(this, SpeedActivity.class);
        startActivity(intent);
    }

    public void moveTimer(View v) {
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }

    public void moveWater(View v) {
        Intent intent = new Intent(this, WaterActivity.class);
        startActivity(intent);
    }

    public void moveGps(View v) {
        Intent intent = new Intent(this, GpsActivity.class);
        startActivity(intent);
    }

    public void moveMeditation(View v) {
        Intent intent = new Intent(this, MeditationActivity.class);
        startActivity(intent);
    }

    public void moveAttend(View v) {
        Intent intent = new Intent(this, AttendActivity.class);
        startActivity(intent);
    }
}