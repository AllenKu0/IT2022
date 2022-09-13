package com.example.vibrator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button vibrate_btn,cancel_btn,mode1_btn,startService_btn,choose_btn;
    private Vibrator vibrator;
    private VibrationEffect vibrationEffect;
    private MediaPlayer mediaPlayer;
    private MediaService mediaService;
    private final Context mContext = this;

    private static final int ChooseRingtone = 0;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.e("onServiceConnected", "Service連線" );
            mediaService = ((MediaService.MediaBinder)binder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("onServiceConnected", "Service斷線" );
            mediaService = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrate_btn = findViewById(R.id.vibrate_btn);
        cancel_btn = findViewById(R.id.cancel_btn);
        mode1_btn = findViewById(R.id.mode1_btn);
        startService_btn = findViewById(R.id.startService_btn);
        choose_btn = findViewById(R.id.choose_btn);

//        AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                .setLegacyStreamType(AudioManager.STREAM_NOTIFICATION)
//                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .build();
        mode1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
                //一關一開，repeat:-1代表不repeat，0或其他代表repeat。
                vibrator.vibrate(new long[]{100, 500, 100, 500},0);
                //產生一次震動2秒
//                vibrator.vibrate(2000);
            }
        });
        vibrate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
                //一關一開，repeat:-1代表不repeat，0或其他代表repeat。
//                vibrator.vibrate(new long[]{100, 500, 100, 500},0 );
//                vibrator.vibrate(2000);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.e("TAG", "onClick: aa" );
                    startAlarm();
                    vibrator.vibrate( VibrationEffect.createWaveform(new long[]{500, 1000},new int[]{20,255},1));
                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.cancel();
                stopAlarm();
            }
        });
        startService_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MediaService.class);
                //開始Service，進入生命週期onCreate、onStartCommand
                startService(intent);
            }
        });

        choose_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: 開選" );

                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);

                // 列表中不显示"默认铃声"选项，默认是显示的
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT,
                        false);

                // 列表中不显示"静音"选项，默认是显示该选项，如果默认"静音"项被用户选择，
                // 则EXTRA_RINGTONE_PICKED_URI 为null
                // intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT,false);

                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_INCLUDE_DRM,
                        true);

                // 设置列表对话框的标题，不设置，默认显示"铃声"
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置来电铃声");
                startActivityForResult(intent, ChooseRingtone);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != RESULT_OK){
            Log.e("TAG", "onActivityResult:可憐 ");
            return;
        }else{
            //獲得所選的項目Uri
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            Log.e("TAG", "onActivityResult: "+uri );
            if (uri!=null){
                switch (requestCode){
                    case ChooseRingtone:
                        //修改RINGTONE至選擇的項目
                        RingtoneManager.setActualDefaultRingtoneUri(this,RingtoneManager.TYPE_RINGTONE,uri);
                        break;
                    default:
                        Log.e("TAG", "onActivityResult: "+resultCode );
                }
            }
        }
    }

    private void startAlarm() {
        //有的手機會建立失敗，從而導致mMediaPlayer為空。
        mediaPlayer = MediaPlayer.create(this, getSystemDefaultUri());
        if (mediaPlayer == null) {//有的手機鈴聲會建立失敗，如果建立失敗，播放我們自己的鈴聲
            Log.e("TAG", "startAlarm: 空 " );
//            SoundPoolUtils.playCallWaitingAudio();//自己定義的鈴音播放工具類。具體實現見下方
        } else {
            mediaPlayer.setLooping(true);// 設定迴圈
            try {
                //create()其實有做
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }
    }

    private Uri getSystemDefaultUri() {
        //預設提示聲
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //預設鈴聲
//        return RingtoneManager.getActualDefaultRingtoneUri(this,RingtoneManager.TYPE_RINGTONE);
    }
    private void stopAlarm() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                Log.e("TAG", "stopAlarm: " );
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
//        SoundPoolUtils.stopCallWaitingAudio();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(vibrator != null){
            vibrator.cancel();
        }
        stopAlarm();
    }
}