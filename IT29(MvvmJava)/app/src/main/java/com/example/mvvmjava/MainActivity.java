package com.example.mvvmjava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mvvmjava.veiwmodel.MyViewModel;
import com.example.mvvmjava.pojo.Post;

public class MainActivity extends AppCompatActivity {

    private TextView userId_txt,id_txt,title_txt,body_txt;
    private Button getPost_btn,intent_btn;
    private MyViewModel myViewModel;
    private final Context mContext = this;
    //ProgressDialog
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createProgressDialog();
        userId_txt = findViewById(R.id.userId_txt);
        id_txt = findViewById(R.id.id_txt);
        title_txt = findViewById(R.id.title_txt);
        body_txt = findViewById(R.id.body_txt);
        getPost_btn = findViewById(R.id.getPost_btn);
        intent_btn = findViewById(R.id.intent_btn);
        // ViewModelStoreOwner 有被Activity 和 Fragment 實做，所以ViewModelStoreOwner可以為當前Activity、Fragment。
        //獲取ViewModel
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        myViewModel.postMutableLiveData.observe(this,new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                //觀察到變化後，更新UI
                userId_txt.setText(String.valueOf(post.getUserId()));
                id_txt.setText(String.valueOf(post.getId()));
                title_txt.setText(post.getTitle());
                body_txt.setText(post.getText());
            }
        });

        myViewModel.isShowDialog.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //觀察到變化後，更新UI
                if(aBoolean){
                    alertDialog.show();
                }else{
                    alertDialog.dismiss();
                }
            }
        });
        //拿資料
        getPost_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewModel.goToViewModelGetPost();
            }
        });
        //跳頁
        intent_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this,DataBindingActivity.class);
            startActivity(intent);
        });
    }
    //創ProgressDialog
    private void createProgressDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final View view = LayoutInflater.from(mContext).inflate(R.layout.progress_dialog,null);
        builder.setView(view);
        alertDialog = builder.create();
    }
}