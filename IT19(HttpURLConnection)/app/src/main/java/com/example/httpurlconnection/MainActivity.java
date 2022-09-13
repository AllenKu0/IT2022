package com.example.httpurlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.PhantomReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private HttpURLConnection httpURLConnection;
    private Thread thread;
    private TextView result_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result_txt = findViewById(R.id.result_txt);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //傳入api網址
                    getPost("https://jsonplaceholder.typicode.com/posts/1");
                } catch (IOException e) {
                    Log.e("error", "ErrorMsg: "+e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void getPost(String path) throws IOException {
        //API網址
        URL url = new URL(path);
        StringBuilder result = new StringBuilder();
        //獲取與URL的連線
        httpURLConnection = (HttpURLConnection) url.openConnection();
        //設定連線超時時間
        httpURLConnection.setConnectTimeout(6000);
        //設定請求類型
        httpURLConnection.setRequestMethod("GET");
        //判斷如果請求結果不為成功的話
        if(httpURLConnection.getResponseCode()!=200){
            Toast.makeText(this,"連線失敗",Toast.LENGTH_SHORT).show();
        }
        //獲取連線的輸入流(讀檔)
        InputStream inputStream = httpURLConnection.getInputStream();
        //使用BufferReader讀出
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        //用來暫時存取結果
        String resultString;
        //當還有資料時繼續讀取
        while ((resultString = bufferedReader.readLine()) != null){
            result.append("\n"+resultString);
        }
        Log.d("回傳結果", "getPost: "+result);
        //因為這為ui操作，需回到UI線程。
        runOnUiThread(()->{
            result_txt.setText(result.toString());
        });
        //結束時斷線
        httpURLConnection.disconnect();
    }
}