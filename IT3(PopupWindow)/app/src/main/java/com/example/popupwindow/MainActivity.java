package com.example.popupwindow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    private TextView popView_txt,contentView_txt;
    private Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popView_txt = findViewById(R.id.popView_txt);
        contentView_txt = findViewById(R.id.contentView_txt);

        //PopView
        popView_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPopupWindow(view);
            }
        });
        //ContextMenu
        registerForContextMenu(contentView_txt);
    }

    private void initPopupWindow(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_layout,null,false);
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        popWindow.setAnimationStyle(R.anim.anim_pop);
//        popWindow.showAtLocation(v, Gravity.NO_GRAVITY,0,0);
        popWindow.showAsDropDown(v,0,0);
//        popWindow.setFocusable(true);
//        content.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                popWindow.dismiss();
//            }
//        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        //將menu渲染
        inflater.inflate(R.menu.cont_view,menu);
        View view = LayoutInflater.from(this).inflate(R.layout.context_view, null);
        if (menu != null){
            //設定標題
            menu.setHeaderTitle("我是ContextMenu");
            //            menu.setHeaderView(view);
            //設定小圖
//            menu.setHeaderIcon(R.drawable.ic_baseline_done_24);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.confirm:
                Toast.makeText(this,"確認",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancel:
                Toast.makeText(this,"取消",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}