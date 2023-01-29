package com.jiyun.ideanote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths


class MemoActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        val items = mutableListOf<String>() //메모 내용이 담길 변수
        val itemNames = mutableListOf<String>()

        File("data/data/com.jiyun.ideanote/files").walk().forEach {
            if(it.isFile) { //파일만 읽기

                itemNames.add(it.toString())
                val reader = it.bufferedReader()
                val iterator = reader.lineSequence().iterator()

                val content = StringBuffer()
                while(iterator.hasNext()){
                    content.append(iterator.next())
                }
                reader.close()

                items.add(content.toString()) //메모 리스트에 추가
            }
        }

        val rv = findViewById<RecyclerView>(R.id.rv)
        val rvAdapter = MemoAdapter(items)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this)

        //새 메모 버튼
        val newMemoBtn = findViewById<Button>(R.id.newMemoBtn)
        newMemoBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


//        //삭제 버튼
//        val memoDeleteBtn = findViewById<Button>(R.id.memoDelete)
//        memoDeleteBtn.setOnClickListener {
//            val path = Paths.get("data/data/com.jiyun.ideanote/files"+itemNames)
//            Files.deleteIfExists(path)
//        }
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