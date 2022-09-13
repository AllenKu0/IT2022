package com.example.datepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

public class TimePickerActivity extends AppCompatActivity {

    private TimePicker clock_timepicker,spinner_timepicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        clock_timepicker = findViewById(R.id.clock_timepicker);
        spinner_timepicker = findViewById(R.id.spinner_timepicker);

        //設定24小時制
        spinner_timepicker.setIs24HourView(true);
        clock_timepicker.setIs24HourView(true);
        //時鐘樣式
        clock_timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                Log.e("TAG", hour+":"+minute);
            }
        });
        //Spinner樣式
        spinner_timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                Log.e("TAG", hour+":"+minute);
            }
        });
    }
}