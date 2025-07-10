package com.example.strathgram2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity() {
    private var userEmail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userEmail = intent.getStringExtra("user") ?: "Unknown"


        findViewById<TextView>(R.id.userText).text = "Logged in as: $userEmail"


        findViewById<Button>(R.id.chatBtn).setOnClickListener {

            startActivity(
                Intent(this, ChatActivity::class.java).putExtra("user", userEmail)
            )
        }
    }
}