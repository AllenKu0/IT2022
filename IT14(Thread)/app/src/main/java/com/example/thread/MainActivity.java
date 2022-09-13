package com.example.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Thread thread;
    private TextView ming_txt;
    private Button start_btn;
    private final Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ming_txt = findViewById(R.id.ming_txt);
        start_btn = findViewById(R.id.start_btn);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //開始前往登機
                ming_txt.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.move));
                //派人幫忙託運
                startTranslate();
                thread.start();
            }
        });
    }

    private void startTranslate(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(()->{
                    Toast.makeText(mContext,"託運開始",Toast.LENGTH_SHORT).show();
                });
                try {
                    thread.sleep(4000);
                    runOnUiThread(()->{
                        Toast.makeText(mContext,"託運完成",Toast.LENGTH_SHORT).show();
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}