package com.example.daggerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements MyController.View {

    private TextView result_txt;
    private MyComponent myComponent;

    //會從引用的component中的module 有 @Provide 標籤獲得。
    @Inject
    MyServiceApi myServiceApi;

    //會從引用的component中的module 有 @Provide 標籤獲得。
    @Inject
    MyController.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result_txt = findViewById(R.id.result_txt);

        myComponent = DaggerMyComponent.builder()
                    .myModule(new MyModule(this))
                    .build();
        myComponent.inject(this);
        presenter.getAllAlbums();

    }

    @Override
    public void showAlbums(String result) {
        result_txt.setText(result);
    }
}