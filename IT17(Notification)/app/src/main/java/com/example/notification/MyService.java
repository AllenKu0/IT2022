package com.example.notification;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyService extends Service {
    private String CHANNEL_ID = "Coder";
    private MyBinder myBinder = new MyBinder();
    private Context context;
    private final long[] vibration = new long[]{100l,100l};
    public class MyBinder extends Binder{
        MyService getInstance(){
            return MyService.this;
        }
    }

    public void sendNotification(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("TAG", "run: " );
                    Thread.sleep(5000);
                    Intent intent = new Intent(context,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    //將要發的廣播
                    PendingIntent pendingIntent = PendingIntent.getActivity
                            (context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder
                            = new NotificationCompat.Builder(context,CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_baseline_arrow_back_24) //.setContent(views)
                            .setContentTitle("哈囉你好!")
                            .setContentText("打聲招呼吧")
                            .setVibrate(vibration)
                            .setWhen(System.currentTimeMillis())
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setContentIntent(pendingIntent);

//                    .setFullScreenIntent(pendingIntent,true);
                    /**發出通知*/
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                    notificationManagerCompat.notify(1,builder.build());
                    Log.e("TAG", "run: 發廣播" );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


}
