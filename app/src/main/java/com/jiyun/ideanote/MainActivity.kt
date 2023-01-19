package com.jiyun.ideanote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //저장 버튼
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener {

            val memo = findViewById<EditText>(R.id.memo)
            Toast.makeText(this@MainActivity, memo.text.toString(), Toast.LENGTH_LONG).show()

        }
    }
}