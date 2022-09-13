package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String CHANNEL_ID = "MyChannel";
    private Button bt1,bt2,service_btn;
    private NotificationManager manager;
    private MyService service;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("TAG", "onServiceConnected: " );
             service = ((MyService.MyBinder)iBinder).getInstance();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("tag", "onServiceDisconnected: " );
             service = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**檢查手機版本是否支援通知；若支援則新增"頻道"*/
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
              CHANNEL_ID,"DemoCode",NotificationManager.IMPORTANCE_DEFAULT);
            //致能震動
            channel.enableVibration(true);
            //設定震動模式
            channel.setVibrationPattern(new long[]{500, 500});
            //致能閃燈
            channel.enableLights(true);
            //獲取NotificationManager
            manager = getSystemService(NotificationManager.class);
            assert manager != null;
            //創建新頻道，並結合channel
            manager.createNotificationChannel(channel);
        }
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        service_btn = findViewById(R.id.service_btn);
        bt1.setOnClickListener(onDefaultClick);
        bt2.setOnClickListener(onCustomClick);
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
        bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);

        service_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.sendNotification();
            }
        });

    }
    private View.OnClickListener onDefaultClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //RemoteViews  views = new RemoteViews(getPackageName(),R.layout.custom_notification);
            //通知欄位的Intent事件
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            //設置Action
            intent.setAction("Notification");
            //將要發的廣播的點擊事件
            PendingIntent pendingIntent = PendingIntent.getActivity
                    (MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            //建立關於Notification的詳情
            NotificationCompat.Builder builder
                    = new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                    //提示欄的小圖示
                    .setSmallIcon(R.drawable.ic_baseline_arrow_back_24) //.setContent(views)
                    //標題
                    .setContentTitle("你的廣播!")
                    //內文
                    .setContentText("it幫幫忙Notification")
                    //發出時間
                    .setWhen(System.currentTimeMillis())
                    //設定鈴聲、震動等為預設值
                    .setDefaults(Notification.DEFAULT_ALL)
                    //設定震動
                    .setVibrate(new long[]{500l,1000l})
                    //設置被點擊後自動取消
                    .setAutoCancel(true)
                    //設置優先權
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    //設置通知類型
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    //設置此Notification Intend
//                    .setContentIntent(pendingIntent);
                    //會有popup效果
                    .setFullScreenIntent(pendingIntent,true);
            //可能醫療用通知等
            //.setFullScreenIntent(pendingIntent,true)

            //發出廣播
//            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
//            notificationManagerCompat.notify(1,builder.build());

            //將NotificationCompat.Builder build()完，並notify
            manager.notify(1,builder.build());
        }
    };
    private View.OnClickListener onCustomClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RemoteViews views = new RemoteViews(getPackageName(),R.layout.custom_notification);
            Intent intent = new Intent(MainActivity.this,NotificationReceiver.class);
            intent.setAction("HI");
            //將要發的通知點擊事件
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (MainActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

            intent.setAction("Close");
            //將要發的通知點擊事件
            PendingIntent close = PendingIntent.getBroadcast
                    (MainActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

            views.setTextViewText(R.id.title,"這是標題");
            views.setImageViewResource(R.id.imageView,R.drawable.ic_baseline_access_alarm_24);
            views.setOnClickPendingIntent(R.id.hi_btn,pendingIntent);
            views.setOnClickPendingIntent(R.id.cancel_btn,close);

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
                    .setContent(views)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE);

            NotificationManagerCompat notificationManagerCompat
                    = NotificationManagerCompat.from(MainActivity.this);
            notificationManagerCompat.notify(1,builder.build());
        }
    };
}