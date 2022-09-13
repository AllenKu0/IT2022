package com.example.vibrator;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MediaService extends android.app.Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener{
    private final String TAG = this.getClass().getName();
    private MediaPlayer mediaPlayer;
    private MediaBinder mediaBinder = new MediaBinder();
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() throws IOException {
        Log.e(TAG, "init: ");
        String url = "https://www.youtube.com/watch?v=qVlSdYs4zAU&t=1s"; // your URL here
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(true);
        //接收prepareAsync的CallBack
        mediaPlayer.setOnPreparedListener(this);
        //接收prepareAsync的Error CallBack
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        mediaPlayer.setDataSource(this,getSystemDefultRingtoneUri());
//        mediaPlayer.setDataSource(url);
        mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)

    }

    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,RingtoneManager.TYPE_RINGTONE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mediaBinder;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: " );
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.e(TAG, "onPrepared: ");
        mp.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(TAG, "onError: " );
        return false;
    }

    public class MediaBinder extends Binder{
        MediaService getService(){
            return MediaService.this;
        }
    }
}
