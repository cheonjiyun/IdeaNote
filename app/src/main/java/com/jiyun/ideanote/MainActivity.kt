package com.jiyun.ideanote

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    //익명 로그인
    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //익명 로그인
        // Initialize Firebase Auth
        auth = Firebase.auth

        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("login", "signInAnonymously:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("login", "signInAnonymously:failure", task.exception)
                    Toast.makeText(baseContext, "로그인을 실패했습니다. 관리자세에 문의하세요.",
                        Toast.LENGTH_SHORT).show()
                }
            }

//        //키보드 바로 올리기
//        val editMemo = findViewById<EditText>(R.id.memo)
//        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.showSoftInput(editMemo!!.rootView, InputMethodManager.SHOW_IMPLICIT)
//        editMemo.requestFocus()
////        showKeyBoard(editMemo)


        //저장 버튼
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener {

            //메모를 firebase에 저장한다.
            // Write a message to the database
            val database = Firebase.database
            val myRef = database.getReference("memo")

            val memoContext = findViewById<EditText>(R.id.memo).text.toString() //메모 내용
            val dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) //날짜

            val model = MemoModel(memoContext, dateNow.toString())
            myRef
                .child(auth.currentUser!!.uid)
                .push()
                .setValue(model)

            //목록 Activity로 넘어간다.
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

//    private fun showKeyBoard(editMemo: EditText?) {
//        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.showSoftInput(editMemo!!.rootView, InputMethodManager.SHOW_IMPLICIT)
//        editMemo.requestFocus()
//    }

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