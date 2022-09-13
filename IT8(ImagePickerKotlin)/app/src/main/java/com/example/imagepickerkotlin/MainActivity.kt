package com.example.imagepickerkotlin

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var imagePicker_btn:Button
    lateinit var camera_btn:Button
    lateinit var positive_spin_btn:Button
    lateinit var negative_spin_btn:Button
    lateinit var save_btn:Button
    lateinit var picture_img:ImageView

    val mContext:Context = this

    private var clockWiseAngle = 0f

    private lateinit var bitmap:Bitmap
    //圖片選擇的回調
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (RESULT_OK == activityResult.resultCode) {
            val resolver = this.contentResolver
            //將拍攝的照片轉成Bitmap
            bitmap = MediaStore.Images.Media.getBitmap(resolver, activityResult.data?.data)
            //imageView顯示結果
            picture_img.setImageBitmap(bitmap)
        }
    }
    //照相機拍照後的回調
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(RESULT_OK == result.resultCode) {
            //將拍攝的照片轉成Bitmap
            bitmap = result.data?.extras?.get("data") as Bitmap
            //imageView顯示結果
            picture_img.setImageBitmap(bitmap)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imagePicker_btn = findViewById(R.id.imagePicker_btn)
        camera_btn = findViewById(R.id.camera_btn)
        positive_spin_btn = findViewById(R.id.positive_spin_btn)
        negative_spin_btn = findViewById(R.id.negative_spin_btn)
        save_btn = findViewById(R.id.save_btn)
        picture_img = findViewById(R.id.picture_img)

        //開啟檔案管理Intent.ACTION_PICK
        imagePicker_btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePickerLauncher.launch(intent)

        })
        //開啟相機
        camera_btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        })

        positive_spin_btn.setOnClickListener(View.OnClickListener {
            clockWiseAngle += 90f
            picture_img.animate().rotation(clockWiseAngle);
        })

        negative_spin_btn.setOnClickListener(View.OnClickListener {
            clockWiseAngle -=90f
            picture_img.animate().rotation(clockWiseAngle)
        })
        //儲存按鈕
        save_btn.setOnClickListener(View.OnClickListener {
            try {
                //檔名
                val fileName:String = System.currentTimeMillis().toString()+".jpg"
                Log.e("TAG", "onCreate: fileName : $fileName")
                // depreciate
                val file =File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path+"/Camera/",fileName)
                // 存在此APP資料夾內
//                val file =File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),fileName)
                Log.e("TAG", "onCreate:A "+getExternalFilesDir(Environment.DIRECTORY_PICTURES) )
                Log.e("TAG", "onCreate:B "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) )

                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,out)
                out.flush()
                out.close()

                //通知其掃描新建的圖片檔案，這樣才找的到
                val uri: Uri = Uri.fromFile(file)
                sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
                Toast.makeText(mContext,"儲存成功",Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(mContext,"儲存失敗",Toast.LENGTH_SHORT).show()

            }
//            if(!file.exists()){
//                file.createNewFile()
//            }
        })
    }

}