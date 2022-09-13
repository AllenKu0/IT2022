package com.example.daggerdemo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyServiceApi {
    @GET("albums")
    Single<List<Albums>> getPosts();
    @POST("albums/1")
    Completable postAlbums(@Body Albums albums);
}
