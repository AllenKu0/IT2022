package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {
    private final String TAG = "Service";
    public class MyBinder extends Binder //宣告一個繼承 Binder 的類別 LocalBinder
    {
        MyService getService()
        {
            return  MyService.this;
        }
    }

    public void test(){
        Log.e(TAG, "Service 測試方法");
    }

    private MyBinder myBinder = new MyBinder();

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.e(TAG, "Service Create" );
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // TODO Auto-generated method stub
        Log.e("onStartCommand", "Service onStartCommand" );
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.e(TAG, "Service Unbind" );
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.e(TAG, "Service Destroy" );
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "Service Bind" );
        return myBinder;
    }
}
