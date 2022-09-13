package com.example.daggerdemo;

import com.google.gson.annotations.SerializedName;

public class Albums {
    private int userId;
    private int id;
    private String title;

    @SerializedName("body")
    private String text;

    public Albums(int userId, int id , String title){
        this.userId=userId;
        this.id = id;
        this.title= title;
    }
    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
