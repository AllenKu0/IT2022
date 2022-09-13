package com.example.itfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button bt1;
    private Button bt2;
    private AFragment aFragment;
    private BFragment bFragment;
    private FragmentManager fragmentManager ;
    private FragmentTransaction fragmentTransaction ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);
//        fragmentManager = getSupportFragmentManager();
//        不能先new 每次用就要先開啟一次新的
//        fragmentTransaction = fragmentManager.beginTransaction();
        aFragment = new AFragment();
        bFragment = new BFragment();
//        CFragment cFragment = CFragment.newInstance();
        //透過getSupportFragmentManager()，獲取FragmentManager並使用beginTransaction()開啟一個事務。最後將Fragmnet加入容器內的方法，可以使用add。
//        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,aFragment,"A").commit();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //這裡要做到替換的效果，所以我們用replace做到取代。
                if(aFragment.isAdded()){
                    Log.e("FLOW", "a加過" );
                    if(!bFragment.isHidden()){
                        Log.e("FLOW", "b被隱藏 a replace 出現" );
//                        getSupportFragmentManager().beginTransaction().hide(bFragment);
//                        getSupportFragmentManager().beginTransaction().addToBackStack("bFragment");
//                        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,aFragment).commit();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,aFragment).show(aFragment).commit();
                        getSupportFragmentManager().beginTransaction().hide(bFragment).show(aFragment).commit();
                    }
                }else{
                    Log.e("FLOW", "a沒加過" );
                    getSupportFragmentManager().beginTransaction().hide(bFragment).add(R.id.fl_container,aFragment,"A").commit();
                }
//                fragmentTransaction.commit();
//                getSupportFragmentManager().beginTransaction().hide(bFragment).commit();
//                getSupportFragmentManager().beginTransaction().show(aFragment).commit();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bFragment.isAdded()){
                    Log.e("FLOW", " b加過" );
                    if(!aFragment.isHidden()){
                        Log.e("FLOW", " a被隱藏 b顯示" );
                        getSupportFragmentManager().beginTransaction().hide(aFragment).show(bFragment).commit();
                    }else{
                        Log.e("FLOW", " a沒顯示 b顯示" );
                        getSupportFragmentManager().beginTransaction().show(bFragment).commit();
                    }
                }else{
                    Log.e("FLOW", "b沒被加過" );
                    getSupportFragmentManager().beginTransaction().hide(aFragment).add(R.id.fl_container,bFragment,"B").show(bFragment).commit();
                }

            }
        });
    }
}