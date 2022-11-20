package com.a104.kkobak.ui.util;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import lombok.NonNull;

public class FirebaseMsgService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

    }

    @Override
    public void onMessageReceived(@androidx.annotation.NonNull RemoteMessage message) {
        super.onMessageReceived(message);

//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
//
//        NotificationCompat.Builder builder = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            if (notificationManager.getNotificationChannel("kkobak-426c1") == null) {
//                NotificationChannel channel= new NotificationChannel("kkobak-426c1", "kkobak", NotificationManager.IMPORTANCE_DEFAULT);
//                notificationManager.createNotificationChannel(channel);
//            }
//            builder = new NotificationCompat.Builder(getApplicationContext(), "kkobak-426c1");
//        }
//        else {
//            builder = new NotificationCompat.Builder(getApplicationContext());
//        }
//
//        String title = message.getNotification().getTitle();
//        String body = message.getNotification().getBody();
//
//        builder.setContentTitle(title)
//                .setContentText(body);
//
//        Notification notification = builder.build();
//        notificationManager.notify(1, notification);

        System.out.println("파이어베이스 메세지 제목: "+message.getNotification().getTitle());
        System.out.println("파이어베이스 메세지 내용: "+message.getNotification().getBody());
        System.out.println("파이어베이스 메세지 데이터: "+message.getData());
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Toast.makeText(getApplicationContext(), message.getNotification().getBody(), Toast.LENGTH_SHORT).show();
            }
        }, 0);


    }
}
