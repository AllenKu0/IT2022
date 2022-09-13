package com.example.daggerdemo;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Qualifier;

import dagger.Provides;
import dagger.Module;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MyModule {
    private MainActivity activity;
    public MyModule(MainActivity activity){
        this.activity =activity;
    }

    @Provides
    static MyServiceApi provideApiService(@Named("Retrofit") Retrofit retrofit){
        return retrofit.create(MyServiceApi.class);
    }

    @Named("Retrofit")
    @Provides
    static Retrofit provideRetrofit(@Named("testUrl_1") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }
    @Named("testUrl_1")
    @Provides
    static String getBaseUrl_1(){
        return "https://jsonplaceholder.typicode.com/";
    }

    @Named("testUrl_2")
    @Provides
    static String getBaseUrl_2(){
        return "https://reqres.in/api/";
    }

    @Named("okHttp")
    @Qualifier
    @Provides
    static OkHttpClient provideOkhttp(HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
    }
    //new 物件回傳
    @Provides
    static HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    //獲取new 好的 MyPresenter，代表MyPresenter有Function@Provide或MyPresenter的建構元有下@Inject
    @Provides
    static MyController.Presenter providePresenter(MyPresenter presenter){
        return presenter;
    }

    @Provides
    MyController.View provideView(){
        return activity;
    }



}
