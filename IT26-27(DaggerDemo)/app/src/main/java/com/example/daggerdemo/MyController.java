package com.example.daggerdemo;

public interface MyController {
    interface View{
        void showAlbums(String result);
    }

    interface Presenter{
        void getAllAlbums();
    }

}
