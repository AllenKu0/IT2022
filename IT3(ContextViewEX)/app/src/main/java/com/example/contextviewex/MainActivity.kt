package com.example.contextviewex

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var tx1: TextView;
    val name:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tx1 = findViewById(R.id.tx1);
        registerForContextMenu(tx1);
//        var t : Char = 'a'
//        Log.e(TAG, "onCreate: " + t.code);
//        var ans : String = "1234"
//        Log.e(TAG, "onCreate: " + Integer.parseInt("1"))
//        for(i in ans.indices){
//            Log.e("ans", "ans: $i:" +""+ (ans[i] - '1'))
//        }
//        when(ans[0] - '1'){
//            is Int -> {
//                Log.e("TAG", "onCreate: i am Int", )
//            }else ->{
//                Log.e("TAG", "onCreate: ", )
//            }
//        }
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?) {
        var inflater = MenuInflater(this);
        inflater.inflate(R.menu.cont_menu,menu)
        if (menu != null) {
            menu.setHeaderTitle("請選擇")
        }
        //menu?.setHeaderTitle("請選擇")
//        menu?.let {
//            menu.setHeaderTitle("請選擇")
//        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.confirm -> Toast.makeText(this,"確認",Toast.LENGTH_SHORT).show()
            R.id.cancel -> Toast.makeText(this,"取消",Toast.LENGTH_SHORT).show()
            else -> finish()
        }
        return super.onContextItemSelected(item)

    }
}