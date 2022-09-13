package com.example.mvvmjava.veiwmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmjava.model.Model;
import com.example.mvvmjava.pojo.Post;

public class MyViewModel extends androidx.lifecycle.ViewModel {

    private Model model = new Model();

    public final MutableLiveData<Post> postMutableLiveData = new MutableLiveData<>();

    public final MutableLiveData<Boolean> isShowDialog= new MutableLiveData<>();
    public void goToViewModelGetPost(){
        model.getPost(new Model.ModelCallBack() {
            @Override
            public void getPostProgress() {
                //MutableLiveData設值
                isShowDialog.setValue(true);
            }

            @Override
            public void getPostCallBack(Post post) {
                Log.e("MyViewModel", "getPostCallBack: "+post.getId() );
                //backGround
//                postMutableLiveData.postValue(post);
                //mainThread
                //MutableLiveData設值
                postMutableLiveData.setValue(post);
            }

            @Override
            public void getPostComplete() {
                //MutableLiveData設值
                isShowDialog.setValue(false);
            }

            @Override
            public void getPostError() {
                //MutableLiveData設值
                isShowDialog.setValue(false);
            }
        });
    }
}
