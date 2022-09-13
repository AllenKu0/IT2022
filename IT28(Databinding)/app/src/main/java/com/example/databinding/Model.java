package com.example.databinding;

import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableField;

public class Model {
    private String name;
    private String phone;
//    public ObservableField<String> name = new ObservableField<>();
//    public ObservableField<String> phone = new ObservableField<>();
    public Model(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void click(View view){
        Toast.makeText(view.getContext(),"send",Toast.LENGTH_SHORT).show();
    }
}
