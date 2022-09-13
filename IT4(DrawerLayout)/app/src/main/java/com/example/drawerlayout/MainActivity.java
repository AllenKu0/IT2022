package com.example.drawerlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView display_nvg;
    private final Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        display_nvg = findViewById(R.id.display_nvg);
        Log.e("TAG", "onCreate: "+drawerLayout);
        //設置toolbar
        setSupportActionBar(toolbar);
        //設置切換按鈕
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                        R.string.drawer_open,R.string.drawer_close);
        //與Drawer綁定
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        display_nvg.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_confirm:
                        Toast.makeText(mContext,"確認",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_cancel:
                        Toast.makeText(mContext,"取消",Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
    }
}