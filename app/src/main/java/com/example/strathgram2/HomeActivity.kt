package com.example.strathgram2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    private var userEmail: String = "Unknown"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userEmail = intent.getStringExtra("user_email") ?: "Unknown"

        val userTextView: TextView = findViewById(R.id.userText)
        userTextView.text = "Logged in as: $userEmail"

        val chatButton: Button = findViewById(R.id.chatBtn)
        chatButton.setOnClickListener {

            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("user", userEmail)
            }
            startActivity(intent)
        }
    }
}

