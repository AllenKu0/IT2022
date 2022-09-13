package com.example.mvvmjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.mvvmjava.veiwmodel.MyDataBindIngViewModel;
import com.example.mvvmjava.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {

    private ActivityDataBindingBinding activityDataBindingBinding;
    private MyDataBindIngViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data_binding);
        activityDataBindingBinding = DataBindingUtil.setContentView(this,R.layout.activity_data_binding);
        model = new ViewModelProvider(this).get(MyDataBindIngViewModel.class);
        activityDataBindingBinding.setViewModel(model);
        activityDataBindingBinding.setView(this);

    }
    public void goToBack(){
        Log.e("TAG", "goToBack: 退回去" );
        finish();
    }
}