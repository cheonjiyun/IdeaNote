package com.jiyun.ideanote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //저장 버튼
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener {

            val memo = findViewById<EditText>(R.id.memo)
            Toast.makeText(this@MainActivity, memo.text.toString(), Toast.LENGTH_LONG).show()

            //메모 파일 저장
            val outputFile : FileOutputStream = openFileOutput("fileName", MODE_PRIVATE)
            outputFile.write(memo.text.toString().toByteArray()) // memo: String DATA
            outputFile.flush()
            outputFile.close()

        }
    }
}