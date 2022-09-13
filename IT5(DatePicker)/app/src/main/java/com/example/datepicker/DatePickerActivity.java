package com.example.datepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

public class DatePickerActivity extends AppCompatActivity {

    private DatePicker datePicker_calender,datePicker_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        datePicker_calender = findViewById(R.id.datePicker_calender);
        datePicker_spinner = findViewById(R.id.datePicker_spinner);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker_calender.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int monthOfYear, int dayOfMonth) {
                    Log.e("TAG", monthOfYear+"/"+dayOfMonth );
                }
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker_spinner.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int monthOfYear, int dayOfMonth) {
                    Log.e("TAG", monthOfYear+"/"+dayOfMonth );
                }
            });
        }
    }
}