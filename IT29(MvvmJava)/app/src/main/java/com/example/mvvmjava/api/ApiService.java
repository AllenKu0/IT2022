package com.example.mvvmjava.api;

import com.example.mvvmjava.pojo.Post;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("posts/1")
    Observable<Post> getPost();
}
