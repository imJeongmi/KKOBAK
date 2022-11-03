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
import com.example.kkobak.databinding.ActivityMainBinding;
import com.example.kkobak.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    AccessTokenDatabase db;

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

    }

    public void doLogout(View v) {
        Toast.makeText(this, "do Logout", Toast.LENGTH_SHORT).show();

        new LogoutAsyncTask(db.accessTokenDao()).execute();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
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

//    private class ImgAsyncTask extends AsyncTask<String, Void, Bitmap> {
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            Bitmap bmp = null;
//
//            String imgUrl = strings[0];
//            try {
//                URL url = new URL(imgUrl);
//                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return (bmp);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            iv.setImageBitmap(bitmap);
//        }
//    }
}