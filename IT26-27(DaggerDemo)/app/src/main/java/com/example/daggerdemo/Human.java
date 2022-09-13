package com.example.daggerdemo;

import android.util.Log;

public class Human {
    private Work work;
    public Human(Work work) {
        this.work = work;
    }

    private void printMyWork(){
        Log.d("Human", "我的工作: "+work.getWorkName());
    }

}
