package com.example.volume;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private AudioManager audioManager;
    private SeekBar music_seekBar,voiceCall_seekBar,alarm_seekBar,ring_seekBar;
    private Button up_btn,down_btn;
    private NotificationManager notificationManager;
    //AudioManager.FLAG_SHOW_UI(顯示音量調節UI)
    //AudioManager.FLAG_PLAY_SOUND(改變音量時撥出聲音)
    private int flag = AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //取得聲音管理
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        music_seekBar = findViewById(R.id.music_seekBar);
        voiceCall_seekBar = findViewById(R.id.voiceCall_seekBar);
        alarm_seekBar = findViewById(R.id.alarm_seekBar);
        ring_seekBar = findViewById(R.id.ring_seekBar);

        up_btn = findViewById(R.id.up_btn);
        down_btn = findViewById(R.id.down_btn);

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        /**
         * STREAM_MUSIC(音樂)
         * STREAM_VOICE_CALL(電話)
         * STREAM_ALARM(鬧鐘)
         * STREAM_RING(鈴聲)
         */
        initSeekBarVolume();
        initSeekBarChangeListener();

        initButtonOnClickListener();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }


    }

    private void initButtonOnClickListener() {
        //向上調節
        up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: up_btn"+audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_PLAY_SOUND);
                initSeekBarVolume();
            }
        });
        //向下調節
        down_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: up_btn"+audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_PLAY_SOUND);
                initSeekBarVolume();
            }
        });
    }

    private void initSeekBarVolume() {
        //STREAM_MUSIC(音樂)
        //最大聲
        int mMusicMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //目前聲音
        int mMusicCurrentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        assert music_seekBar != null;
        music_seekBar.setMax(mMusicMaxVolume);
        music_seekBar.setProgress(mMusicCurrentVolume);

        //------------------------------------//
        //STREAM_VOICE_CALL(電話)
        //最大聲
        int mVoiceCallMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
        //目前聲音
        int mVoiceCallCurrentVolume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        assert voiceCall_seekBar != null;
        voiceCall_seekBar.setMax(mVoiceCallMaxVolume);
        voiceCall_seekBar.setProgress(mVoiceCallCurrentVolume);

        //------------------------------------//
        //STREAM_ALARM(鬧鐘)
        //最大聲
        int mAlarmMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        //目前聲音
        int mAlarmCurrentVolume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        assert alarm_seekBar != null;
        alarm_seekBar.setMax(mAlarmMaxVolume);
        alarm_seekBar.setProgress(mAlarmCurrentVolume);


        //------------------------------------//
        //STREAM_RING(鈴聲)
        //最大聲
        int mRingMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        //STREAM_RING
        int mRingCurrentVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        assert ring_seekBar != null;
        ring_seekBar.setMax(mRingMaxVolume);
        ring_seekBar.setProgress(mRingCurrentVolume);
    }

    private void initSeekBarChangeListener() {
        //STREAM_MUSIC(音樂)
        music_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,flag);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("TAG", "onStartTrackingTouch: 開始調節音量" );

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("TAG", "onStopTrackingTouch: 停止調節音量" );

            }
        });
        //STREAM_VOICE_CALL(電話)
        voiceCall_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,progress,flag);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //STREAM_ALARM(鬧鐘)
        alarm_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM,progress,flag);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //STREAM_RING(鈴聲)
        ring_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_RING,progress,flag);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}