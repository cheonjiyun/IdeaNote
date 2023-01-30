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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class MemoActivity : AppCompatActivity() {

    //익명 로그인
    private lateinit var auth: FirebaseAuth

    private val memoModels = mutableListOf<MemoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        //recyclerview
        val rv = findViewById<RecyclerView>(R.id.rv) //recylerview
        val rvAdapter = MemoAdapter(memoModels) //adpter
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this)

        //firebase에 있는 메모를 가져온다.
        auth = Firebase.auth

        val database = Firebase.database
        val myRef = database.getReference("memo")

        myRef
            .child(auth.currentUser?.uid.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //snapshot에 데이터모두가 담긴다.
                    for (memoModel in snapshot.children) {
                        memoModels.add(memoModel.getValue(MemoModel::class.java)!!)
                    }
                    rvAdapter.notifyDataSetChanged() //동기화
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("memo", "memo 불러오기 fail")
                }

            })

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