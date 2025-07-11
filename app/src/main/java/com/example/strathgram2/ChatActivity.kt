package com.example.strathgram2

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {


    private lateinit var db: AppDatabase
    private lateinit var container: LinearLayout
    private lateinit var msgInput: EditText
    private lateinit var sendBtn: Button
    private lateinit var chatScrollView: ScrollView

    private var currentUserEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        db = AppDatabase(this)


        currentUserEmail = intent.getStringExtra("user") ?: "Anon"


        container = findViewById(R.id.chatContainer)
        msgInput = findViewById(R.id.msgInput)
        sendBtn = findViewById(R.id.sendBtn)
        chatScrollView = findViewById(R.id.chatScroll)


        loadMessages()


        sendBtn.setOnClickListener {

            val messageText = msgInput.text.toString().trim()


            if (messageText.isNotBlank()) {

                db.addMessage(currentUserEmail, messageText)

                addBubble(currentUserEmail, messageText)


                msgInput.text.clear()

                chatScrollView.post {
                    chatScrollView.fullScroll(ScrollView.FOCUS_DOWN)
                }
            }
        }
    }


    private fun loadMessages() {

        container.removeAllViews()


        db.getAllMessages().forEach { message ->
            addBubble(message.author, message.body)
        }


        chatScrollView.post {
            chatScrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }


    private fun addBubble(author: String, body: String) {

        val messageBubble = TextView(this).apply {
            text = "$author: $body"
            textSize = 16f
            setPadding(24, 12, 24, 12)

            if (author == currentUserEmail) {

                setBackgroundResource(R.drawable.bubble_right)
                setTextColor(Color.WHITE)
            } else {

                setBackgroundResource(R.drawable.bubble_left)
                setTextColor(Color.BLACK)
            }
        }


        val bubbleWrapperLayout = LinearLayout(this).apply {

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {

                setMargins(0, 8, 0, 8)
            }

            addView(messageBubble)


            if (author == currentUserEmail) {
                gravity = Gravity.END
            } else {
                gravity = Gravity.START
            }
        }

        container.addView(bubbleWrapperLayout)
    }
}