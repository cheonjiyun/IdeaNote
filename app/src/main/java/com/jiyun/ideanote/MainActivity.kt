package com.jiyun.ideanote

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDateTime



class MainActivity : AppCompatActivity() {



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //저장 버튼
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener {

            val memo = findViewById<EditText>(R.id.memo)

            //메모를 내부 저장소에 텍스트 파일로 저장한다.
            val dateNow : LocalDateTime = LocalDateTime.now()
            val fileName = memo.text.toString() + dateNow // 파일 이름 : 메모 내용 + 현재 시간 -> 겹치지 않는 이름으로 하기 위해 이렇게 만들었다.
            val outputFile : FileOutputStream = openFileOutput(fileName, MODE_PRIVATE)
            outputFile.write(memo.text.toString().toByteArray())
            outputFile.flush()
            outputFile.close()

            val intent = Intent(this, MemoActivity::class.java)
            startActivity(intent)
            finish()
        }

        //목록 버튼
        val listBtn = findViewById<Button>(R.id.listBtn)
        listBtn.setOnClickListener {
            val intent = Intent(this, MemoActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    //뒤로가기 두번해야
    private var isDouble = false

    override fun onBackPressed() {
        if(isDouble == true){
            finish()
        }

        isDouble = true
        Toast.makeText(this, "뒤로가기를 한 번도 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            isDouble = false
        }, 4000)
    }


}