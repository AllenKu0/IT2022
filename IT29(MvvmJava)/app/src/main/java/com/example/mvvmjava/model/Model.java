package com.example.mvvmjava.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mvvmjava.api.ApiBuilder;
import com.example.mvvmjava.pojo.Post;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Model {

    //處理邏輯運算，拿資料打api等
    public void getPost(ModelCallBack callBack) {
        ApiBuilder.getInstance().getApiService().getPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("TAG", "onSubscribe: ");
                        callBack.getPostProgress();
                    }

                    @Override
                    public void onNext(@NonNull Post post) {
                        Log.e("TAG", "onNext: ");
                        callBack.getPostCallBack(post);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("TAG", "onError: "+e.getMessage());
                        callBack.getPostError();
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG", "onComplete: ");
                        callBack.getPostComplete();
                    }
                });
    }
    // CallBack
    public interface ModelCallBack{
        //getPost()
        void getPostProgress();
        void getPostCallBack(Post post);
        void getPostComplete();
        void getPostError();
        //
    }
}
