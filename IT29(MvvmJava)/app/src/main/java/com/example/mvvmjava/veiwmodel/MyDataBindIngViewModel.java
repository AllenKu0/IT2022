package com.example.mvvmjava.veiwmodel;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.example.mvvmjava.model.Model;
import com.example.mvvmjava.pojo.Post;

public class MyDataBindIngViewModel extends ViewModel {
    private Model model = new Model();

    public final ObservableField<Post> postObservableField = new ObservableField<>();

    public final ObservableField<Boolean> isLoading = new ObservableField<>();

    public void goToViewModelGetPost(){
        model.getPost(new Model.ModelCallBack() {
            @Override
            public void getPostProgress() {
                isLoading.set(true);
            }

            @Override
            public void getPostCallBack(Post post) {
                Log.e("MyDataBindIngViewModel", "getPostCallBack: "+post.getId() );
                postObservableField.set(post);
            }

            @Override
            public void getPostComplete() {
                isLoading.set(false);
            }

            @Override
            public void getPostError() {
                isLoading.set(false);
            }
        });
    }
}
