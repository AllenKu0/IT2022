package com.example.mvvmjava.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
    private ApiService apiService;

    private static ApiBuilder instance;
    public ApiBuilder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static ApiBuilder getInstance(){
        if(instance == null){
            instance = new ApiBuilder();
        }
        return instance;
    }

    public ApiService getApiService (){
        return apiService;
    }
}
