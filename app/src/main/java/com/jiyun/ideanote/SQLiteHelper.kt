package com.jiyun.ideanote

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(
    context: Context?,
    name: String?,
    factory : SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version){
    override fun onCreate(db: SQLiteDatabase) {
        var sql : String = "CREATE TABLE if not exists memoTable(_id, integer primary key autoincrement, txt text);"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "Drop Table if exists memoTable"

        db.execSQL(sql)
        onCreate(db)
    }

}