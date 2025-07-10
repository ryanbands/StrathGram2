package com.example.strathgram2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class ChatMessage(val author: String, val body: String, val time: Long)

class AppDatabase(context: Context) :
    SQLiteOpenHelper(context, "StrathGram2.db", null, 1) {


    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            "CREATE TABLE users(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "email TEXT UNIQUE," +
                    "password TEXT)"
        )


        db.execSQL(
            "CREATE TABLE messages(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "author TEXT," +
                    "body TEXT," +
                    "time INTEGER)"
        )
    }


    override fun onUpgrade(db: SQLiteDatabase, oldV: Int, newV: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS messages")
        onCreate(db)
    }


    fun addUser(email: String, pass: String): Boolean {
        val cv = ContentValues()
        cv.put("email", email)
        cv.put("password", pass)

        return writableDatabase.insert("users", null, cv) > 0
    }


    fun checkUser(email: String, pass: String): Boolean {

        val c: Cursor = readableDatabase.rawQuery(
            "SELECT * FROM users WHERE email=? AND password=?",
            arrayOf(email, pass)
        )

        val exists = c.count > 0
        c.close()
        return exists
    }


    fun addMessage(author: String, body: String) {
        val cv = ContentValues()
        cv.put("author", author)
        cv.put("body", body)
        cv.put("time", System.currentTimeMillis())
        writableDatabase.insert("messages", null, cv)
    }


    fun getAllMessages(): List<ChatMessage> {
        val list = mutableListOf<ChatMessage>()

        val c = readableDatabase.rawQuery(
            "SELECT * FROM messages ORDER BY time ASC", null
        )

        while (c.moveToNext()) {
            val author = c.getString(c.getColumnIndexOrThrow("author"))
            val body = c.getString(c.getColumnIndexOrThrow("body"))
            val time = c.getLong(c.getColumnIndexOrThrow("time")) // Retrieve time as Long
            list.add(ChatMessage(author, body, time))
        }
        c.close()
        return list
    }


}

