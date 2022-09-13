package com.example.mvcmvpex.mvc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mvcmvpex.R;

public class MvcControllerActivity extends AppCompatActivity {
    //邏輯處理，包含如api、資料儲存等行為
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}