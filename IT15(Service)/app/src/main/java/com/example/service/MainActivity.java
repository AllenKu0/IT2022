package com.example.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MyService myService;
    private MainActivity.ServiceReceiver _serviceReceiver =new ServiceReceiver();
    private Button startService_btn,stopService_btn,bindService_btn,unBindService_btn,serviceFunction_btn;
    private final Context mContext = this;

    public ServiceConnection serviceConnection = new ServiceConnection() {
        //IBinder 由Service中onBind直接回傳
        public void onServiceConnected(ComponentName className, IBinder binder) {
            Log.e("onServiceConnected", "Service連線" );
            myService = ((MyService.MyBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.e("onServiceConnected", "Service斷線" );
            myService = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButton();
        registerFilter();
    }

    private void initButton() {
        startService_btn = findViewById(R.id.startService_btn);
        stopService_btn = findViewById(R.id.stopService_btn);
        bindService_btn = findViewById(R.id.bindService_btn);
        unBindService_btn = findViewById(R.id.unBindService_btn);
        serviceFunction_btn = findViewById(R.id.serviceFunction_btn);

        startService_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MyService.class);
                //開始Service，進入生命週期onCreate、onStartCommand
                startService(intent);
            }
        });

        stopService_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MyService.class);
                //停止Service，進入生命週期onDestroy
                stopService(intent);
            }
        });

        bindService_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MyService.class);
                //綁定才能呼叫Service的Function，進入生命週期onCreate、onBind
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        unBindService_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //解除Service綁定，進入生命週期onUnbind、onDestroy
                unbindService(serviceConnection);
            }
        });

        serviceFunction_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //呼叫Service 方法
                myService.test();
            }
        });
    }

    private void registerFilter() {
        final IntentFilter cloudIntentFilter = new IntentFilter();
        cloudIntentFilter.addAction(Utility.TEST);
        LocalBroadcastManager.getInstance(this).registerReceiver(_serviceReceiver, cloudIntentFilter);
    }


    private class ServiceReceiver extends BroadcastReceiver {
        //接收到廣播做相對應的事情
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Utility.TEST)) {
            }
        }
    }

    
}