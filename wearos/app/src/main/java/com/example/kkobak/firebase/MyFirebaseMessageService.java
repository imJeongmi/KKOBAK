package com.example.kkobak.firebase;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.kkobak.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessageService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
            super.onNewToken(s);
        System.out.println("파이어베이스 토큰 호출: "+s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.println("메세지 도착했네!!");
        System.out.println("파이어베이스 메세지 제목: "+remoteMessage.getNotification().getTitle());
        System.out.println("파이어베이스 메세지 내용: "+remoteMessage.getNotification().getBody());
        System.out.println("파이어베이스 메세지 데이터: "+remoteMessage.getData());
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Toast.makeText(getApplicationContext(), "일단 파이어베이스 메세지 띄우기", Toast.LENGTH_SHORT).show();
            }
        }, 0);

    }
    private void sendNotification(String title, String body){

    }
}
