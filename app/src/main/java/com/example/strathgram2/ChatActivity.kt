package com.example.strathgram2

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity 
import android.graphics.Color
import androidx.compose.foundation.layout.Box

class ChatActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase // Instance of your SQLite database helper
    private lateinit var container: LinearLayout // The LinearLayout to hold chat bubbles
    private var user: String = "" // The current logged-in user's identifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat) // Set the layout for this activity

        // Initialize the database instance
        db = AppDatabase(this)
        // Retrieve the user identifier passed from the previous activity (e.g., HomeActivity)
        // Default to "Anon" if no user is provided.
        user = intent.getStringExtra("user") ?: "Anon"

        // Initialize UI elements by finding them from the layout
        container = findViewById(R.id.chatContainer) // The container for chat bubbles
        val input = findViewById<EditText>(R.id.msgInput) // Input field for new messages
        val sendBtn = findViewById<Button>(R.id.sendBtn) // Button to send messages


        Box {
            loadMessages()
        }

        
            val text = input.text.toString() // Get the text from the input field
            if (text.isNotBlank()) { // Check if the message is not empty or just whitespace
                db.addMessage(user, text) // Add the message to the database
                addBubble(user, text) // Display the message as a chat bubble
                input.text.clear() // Clear the input field after sending
            }
        }
    }

    // Loads all messages from the database and displays them as chat bubbles
    private fun loadMessages() {
        container.removeAllViews() // Clear any existing views in the container
        db.getAllMessages().forEach { m ->
            // For each ChatMessage object retrieved, add a chat bubble
            addBubble(m.author, m.body)
        }
    }

    // Adds a new chat bubble (TextView) to the chat container
    private fun addBubble(author: String, body: String) {
        val tv = TextView(this).apply {
            text = "$author: $body" // Set the text of the bubble
            textSize = 16f // Set text size
            setPadding(24, 12, 24, 12) // Add padding inside the bubble
            // Set background and text color based on whether the message is from the current user
            if (author == user) {
                setBackgroundResource(R.drawable.bubble_right) // Custom drawable for sender
                setTextColor(Color.WHITE) // White text for sender's bubble
                gravity = Gravity.END // Align sender's bubble to the right
            } else {
                setBackgroundResource(R.drawable.bubble_left) // Custom drawable for receiver
                setTextColor(Color.BLACK) // Black text for receiver's bubble
                gravity = Gravity.START // Align receiver's bubble to the left
            }
        }

        // Create a LinearLayout to hold the TextView and control its margins
        val bubbleLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                // Add margins to separate bubbles
                setMargins(0, 8, 0, 8)
            }
            // Add the TextView (chat bubble) to this layout
            addView(tv)
            // Set gravity for the bubble within its parent layout
            if (author == user) {
                gravity = Gravity.END // Align the entire bubble layout to the right
            } else {
                gravity = Gravity.START // Align the entire bubble layout to the left
            }
        }

        container.addView(bubbleLayout) // Add the bubble layout to the main chat container

        // Scroll to the bottom of the ScrollView to show the latest message
        findViewById<ScrollView>(R.id.chatScroll).post {
            it.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }
}