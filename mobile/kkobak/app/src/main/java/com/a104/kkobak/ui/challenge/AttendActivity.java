package com.a104.kkobak.ui.challenge;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.a104.kkobak.R;
import com.a104.kkobak.data.retrofit.api.ChallengeChkApi;
import com.a104.kkobak.data.retrofit.model.JudgeReq;
import com.a104.kkobak.data.room.dao.AccessTokenDao;
import com.a104.kkobak.data.room.database.AccessTokenDatabase;
import com.a104.kkobak.data.room.entity.AccessToken;
import com.a104.kkobak.ui.util.DatabaseAccess;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendActivity extends AppCompatActivity implements LocationListener {
    AccessTokenDatabase db;
    String accessToken;

    LocationManager locationManager;

    final double DISTANCE_RANGE = 100.0;

    double targetLat;
    double targetLon;
    Location targetLocation;

    Button attendBtn;
    ImageView searchIV;
    TextView searchTV;

    Location mLocation;

    String chlId;
    LocalDateTime startTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend);

        searchIV = (ImageView) findViewById(R.id.searchIV);
        Glide.with(this).load(R.drawable.search).override(1200,1200).into(searchIV);
        searchTV = findViewById(R.id.searchTV);

//        targetLat = 37.5014951;
//        targetLon = 127.0396188;

        Intent intent = getIntent();
        if (intent.getStringExtra("lat") != null)
            targetLat = Double.parseDouble(intent.getStringExtra("lat"));
        if (intent.getStringExtra("lon") != null)
            targetLon = Double.parseDouble(intent.getStringExtra("lon"));

        if (intent.getStringExtra("chlId") != null)
            chlId = intent.getStringExtra("chlId");
        else
            chlId = "-1";

        //권한 체크
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);

        db = AccessTokenDatabase.getAppDatabase(this);
        try {
            accessToken = new DatabaseAccess.getAccessTokenAsyncTask(db.accessTokenDao()).execute().get().getAccessToken();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        attendBtn = findViewById(R.id.attendBtn);
        attendBtn.setVisibility(View.INVISIBLE);

        targetLocation = new Location("target");
        targetLocation.setLatitude(targetLat);
        targetLocation.setLongitude(targetLon);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우 최초 권한 요청 또는 사용자에 의한 재요청 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 권한 재요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double distance = location.distanceTo(targetLocation);
//        Toast.makeText(this, "거리: " + distance, Toast.LENGTH_SHORT).show();

        if (distance <= DISTANCE_RANGE && attendBtn.getVisibility() == View.INVISIBLE) {
            String currentAddress = getCurrentAddress(location.getLatitude(), location.getLongitude());
            attendBtn.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.attendfinish).into(searchIV);
            searchTV.setText("[현재 위치]\n" + currentAddress);
            Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
            vib.vibrate(1000);
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
            ringtone.play();

            mLocation = location;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startTime = LocalDateTime.now().withNano(0);
            }
        }
        else if (distance > DISTANCE_RANGE && attendBtn.getVisibility() == View.VISIBLE){
            attendBtn.setVisibility(View.INVISIBLE);
            searchTV.setText("거리가 멀어 출석할 수 없습니다.");
            Glide.with(this).load(R.drawable.search).override(1200,1200).into(searchIV);
        }
        else if (distance > DISTANCE_RANGE && searchTV.getText().toString().equals("탐색 중")){
            attendBtn.setVisibility(View.INVISIBLE);
            searchTV.setText("거리가 멀어 출석할 수 없습니다.");
            Glide.with(this).load(R.drawable.search).override(1200,1200).into(searchIV);
        }
    }

    public String getCurrentAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
//            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
//            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
//            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }

    public void doAttend(View v) {
        JudgeReq judgeReq = new JudgeReq();

        judgeReq.setLat(String.valueOf(mLocation.getLatitude()));
        judgeReq.setLng(String.valueOf(mLocation.getLongitude()));
        judgeReq.setChlId(Long.valueOf(chlId));
        judgeReq.setTime(startTime.toString());

        ChallengeChkApi.getService().judgeChallenge(accessToken, judgeReq).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    showResult();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }

    public void showResult() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alter_dialog, null);
        builder.setView(view)
                .create();

        TextView title = view.findViewById(R.id.dialogTitle);
        TextView content = view.findViewById(R.id.dialogContent);
        ImageView confirm = view.findViewById(R.id.layoutConfirm);


        title.setText("출석 완료!");
        content.setText("지정한 위치의 출석을 완료했습니다.");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        builder.show();
    }
}
