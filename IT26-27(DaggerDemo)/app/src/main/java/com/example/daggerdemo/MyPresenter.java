package com.example.daggerdemo;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyPresenter implements MyController.Presenter{
    private MyServiceApi myServiceApi;
    private MyController.View myView;
    @Inject
    public MyPresenter(MyServiceApi myServiceApi,MyController.View myView) {
        this.myServiceApi = myServiceApi;
        this.myView = myView;
    }

    @Override
    public void getAllAlbums(){
        myServiceApi.getPosts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Albums>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("TAG", "onSubscribe: ");
                    }

                    @Override
                    public void onSuccess(@NonNull List<Albums> albums) {
                        Log.d("TAG", "onSuccess: ");
                        StringBuilder result = new StringBuilder();
                        for(Albums album:albums){
                            result.append("{").append("\n");
                            //獲取資料，並整理
                            result.append("ID: ").append(album.getId()).append("\n") ;
                            result.append("User ID: ").append(album.getUserId()).append("\n") ;
                            result.append("Title: ").append(album.getTitle()).append("\n") ;
                            result.append("Text: ").append(album.getText()).append("\n") ;

                            //將結果接續補上
                            result.append("},").append("\n");
                        }
                        myView.showAlbums(result.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onError: ");
                    }
                });
    }
}
