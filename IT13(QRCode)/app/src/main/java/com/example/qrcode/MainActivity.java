package com.example.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private SurfaceView qrCode_view;
    private TextView result_txt;
    private Button encoder_btn,goTo_btn,constructQrCode_btn;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private Boolean isScan = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrCode_view = findViewById(R.id.qrCode_view);
        result_txt = findViewById(R.id.result_txt);
        encoder_btn = findViewById(R.id.encoder_btn);
        goTo_btn = findViewById(R.id.goTo_btn);
        constructQrCode_btn = findViewById(R.id.constructQrCode_btn);

        constructQrCode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,BarCodeEncoderActivity.class);
                startActivity(intent);
            }
        });

        goTo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //將結果轉化成Uri
                Uri uri = Uri.parse(result_txt.getText().toString());
                //設定跳轉的網頁
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //開始跳轉
                startActivity(intent);
            }
        });
        encoder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isScan){
                    encoder_btn.setText("開始掃描");
                    isScan = false;
                    qrCode_view.setVisibility(View.GONE);

                }
                else {
                    encoder_btn.setText("停止掃描");
                    isScan = true;
                    qrCode_view.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //條碼感應器
        barcodeDetector = new BarcodeDetector.Builder(this)
                //設定感測模式(QRCode)
                .setBarcodeFormats(Barcode.QR_CODE).build();
        //關於相機內容設定
        cameraSource = new CameraSource.Builder(this,barcodeDetector)
                .setAutoFocusEnabled(true).build();
        //新增SurfaceHolder的CallBack
        qrCode_view.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.e("流程", "surfaceCreated:沒權限" );
                    return;
                }
                try{
                    Log.e("流程", "surfaceCreated:開啟相機");
                    cameraSource.start(surfaceHolder);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                //相機狀態改變
                Log.e("流程", "surfaceChanged:");
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                Log.e("流程", "surfaceDestroyed:");
                //相機暫停
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){
            @Override
            public void release() {

            }
            //產生結果時
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                //獲取偵測結果
                final SparseArray<Barcode> result=detections.getDetectedItems();
                if(result.size()!=0){
                    //回到UI線程，更新UI
                    runOnUiThread(()->{
                        result_txt.setText(result.valueAt(0).displayValue);
                        goTo_btn.setEnabled(true);
                    });
                }
            }
        });
    }

    //    result_txt.post(new Runnable() {
//        @Override
//        public void run() {
//            result_txt.setText(domain.valueAt(0).displayValue);
//        }
//    });
    @Override
    protected void onStop() {
        super.onStop();
        //相機資源釋放
        cameraSource.release();
    }
}