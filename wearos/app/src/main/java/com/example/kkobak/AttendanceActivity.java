package com.example.kkobak;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.kkobak.repository.request.JudgeRequest;
import com.example.kkobak.repository.util.RetrofitClient;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceActivity extends Activity {

    //로케이션 리스너 선언 및 설정
    LocationListener locationListener;
    LocationManager locationManager;

    // 출력용 뷰
    TextView title, status, adjacent;
    ImageButton btn_attend_start;

    // 리스트에서 넘어온 데이터
    Intent intent;
    private Long chlId;
    private String unit;
    private String target_lng;
    private String target_lat;
    private boolean done;

    // 액세스 토큰
    private String accessToken;

    String nowLat;
    String nowLng;

    private static final String TAG = "____Attendance____";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        // 뷰  컴포넌트 연결
        title = findViewById(R.id.title_attendance);
        status = findViewById(R.id.status_attendance);
        adjacent = findViewById(R.id.adjacency_attendance);
        btn_attend_start = findViewById(R.id.btn_attend_attendance);

        // 토큰 세팅
        accessToken = ((KkobakApp)getApplication()).getAccessToken();

        //인텐트에서 값 가져오기
        intent = getIntent();
        chlId = intent.getLongExtra("chlId",0);
        done = intent.getBooleanExtra("done", true);
        title.setText(intent.getStringExtra("title"));
        unit = intent.getStringExtra("unit");

        String[] str = unit.split(",");
        target_lng = str[0];
        target_lat = str[1];

        Drawable unchk = getResources().getDrawable( R.drawable.noncheck_button );
        Drawable chk = getResources().getDrawable( R.drawable.check_button );

        adjacent.setText("근처가 아닙니다!");
        btn_attend_start.setClickable(false);
        System.out.println(">>>>>>>>>>>>> done = " + done);

        if(done){
            status.setText("성공!");
            status.setTextColor(Color.parseColor("#32CD32"));
            btn_attend_start.setVisibility(View.INVISIBLE);
            adjacent.setVisibility(View.INVISIBLE);
        }
        else {
            status.setText("미완료");
            status.setTextColor(Color.parseColor("#DB4455"));
            btn_attend_start.setImageDrawable(chk);
            btn_attend_start.setClickable(false);
        }


        // 버튼 연결 설정
        btn_attend_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendJudge(nowLat, nowLng);
                if(!adjacent.getText().equals("근처가 아닙니다!")){
                    status.setText("성공!");
                    status.setTextColor(Color.parseColor("#32CD32"));
                    btn_attend_start.setClickable(false);
                    btn_attend_start.setVisibility(View.INVISIBLE);
                    adjacent.setVisibility(View.INVISIBLE);
                    done = true;
                }
            }
        });


        if(done){ // 이미 완료 상태라면
            status.setText("완료!");
            status.setTextColor(Color.parseColor("#32CD32"));
            btn_attend_start.setClickable(false);
            btn_attend_start.setVisibility(View.INVISIBLE);
            adjacent.setVisibility(View.INVISIBLE);
            btn_attend_start.setImageDrawable(unchk);
        }
        else {
            status.setText("미완료");
            status.setTextColor(Color.parseColor("#DB4455"));
            btn_attend_start.setImageDrawable(chk);
            btn_attend_start.setClickable(false);
        }

        // 퍼미션 체크
        checkPermission();

        // 로케이션 설정
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double distance = getDistance(target_lat,target_lng,location.getLatitude(),location.getLongitude());
                nowLng = location.getLongitude()+"";
                nowLat = location.getLatitude()+"";

                if(distance<100) {
                    adjacent.setText("근처입니다!");
                    adjacent.setTextColor(Color.parseColor("#32CD32"));
                    if(!done) {
                        btn_attend_start.setImageDrawable(chk);
                        btn_attend_start.setClickable(true);
                    }
                }
                else{
                    adjacent.setText("근처가 아닙니다!");
                    adjacent.setTextColor(Color.parseColor("#808080"));
                    btn_attend_start.setImageDrawable(unchk);
                    btn_attend_start.setClickable(false);
                }
            }
            @Override
            public void onFlushComplete(int requestCode) {
                System.out.println("플러시");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                System.out.println("상태변경");
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                System.out.println("제공가능");
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                System.out.println("제공안됨");
            }
        };

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

    }
    @Override
    protected void onResume(){
        super.onResume();

    }
    @Override
    protected void onDestroy() {
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
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

    public void sendTodoChange(Long chlId){
        System.out.println("투두 호출: " +chlId);
        Call<Boolean> call = RetrofitClient.getApiService().sendTodoStatusChange(chlId, accessToken);
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

    public void sendJudge(String lat, String lng){
        JudgeRequest judgeRequest = new JudgeRequest( chlId, "", lat, lng );
        //Retrofit 호출
        Call<Void> call = RetrofitClient.getApiService().reqJudge(judgeRequest, accessToken);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    System.out.println(lat +" ------ " + lng);
                    return;
                }
                else {
//                    Log.d("연결이 성공적 : ", response.body().toString());
                    System.out.println(lat +" ------ " + lng);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("연결실패", t.getMessage());
                System.out.println(lat +" ------ " + lng);
            }
        });
    }

    private void checkPermission() { // 권한 확인 함수

        // 위치 권한 확인
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) // check runtime permission for location
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1); // If BODY_SENSORS permission has not been taken before then ask for the permission with popup
        } else {
            Log.d(TAG, "ALREADY GRANTED"); //if BODY_SENSORS is allowed for this app then print this line in log.
        }
    }

    public static double getDistance(String lat1_s, String lng1_s, double lat2_s, double lng2_s) {

        double lat1 = Double.parseDouble(lat1_s);
        double lng1 = Double.parseDouble(lng1_s);
        double lat2 = lat2_s;
        double lng2 = lng2_s;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = 6371 * c * 1000;    // 미터로 바꾸기
        return d;
    }
}
