package com.example.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.sql.ConnectionEvent;

public class MainActivity extends AppCompatActivity {

    private Button send_btn;
    private TextView result_txt;
    private final MyReceiver myReceiver = new MyReceiver();
    private final Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send_btn = findViewById(R.id.send_btn);
        result_txt = findViewById(R.id.result_txt);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //設定Action
                intent.setAction(Utilities.SEND_BROADCAST);
                //傳送Data
                intent.putExtra("message","廣播發送完成");
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        //新增Action
        intentFilter.addAction(Utilities.SEND_BROADCAST);
        //註冊
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,intentFilter);
        this.registerReceiver(mConnReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        //解除註冊
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        this.unregisterReceiver(mConnReceiver);

    }
    private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(isNetworkAvailable()){
                Toast.makeText(MainActivity.this,"有網路",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"沒網路",Toast.LENGTH_SHORT).show();
            }

        }
    };
    public boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        //確認是否有網路連線
        return networkInfo!= null && networkInfo.isConnected();
        //確認是否為wifi
        //return networkInfo!= null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }


    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Utilities.SEND_BROADCAST:
                    //接收到廣播時的行為
                    String result = intent.getStringExtra("message");
                    result_txt.setText(result);
                    break;
            }
        }
    }
}