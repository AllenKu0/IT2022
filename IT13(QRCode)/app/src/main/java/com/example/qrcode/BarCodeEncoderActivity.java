package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarCodeEncoderActivity extends AppCompatActivity {
    private ImageView result_img;
    private EditText content_edt;
    private Button transform_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_encoder);

        result_img=(ImageView)findViewById(R.id.result_img);
        content_edt=(EditText)findViewById(R.id.content_edt);
        transform_btn = (Button)findViewById(R.id.transform_btn);

        transform_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //BarcodeEncoder (將資料負責轉換成Barcode)
                BarcodeEncoder encoder = new BarcodeEncoder();
                try{
                    //將輸入的內容轉換，大小不宜太小會模糊
                    Bitmap bit = encoder.encodeBitmap(content_edt.getText()
                            .toString(), BarcodeFormat.QR_CODE,250,250);
                    //設定至imageView顯示
                    result_img.setImageBitmap(bit);
                }catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });
    }
}