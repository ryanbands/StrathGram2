package com.example.strathgram2

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val db = AppDatabase(this)

        emailEditText = findViewById(R.id.regEmail)
        passwordEditText = findViewById(R.id.regPass)
        registerButton = findViewById(R.id.regBtn)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                toast("Please fill all fields")
            } else if (!email.endsWith("@strathmore.edu")) {
                toast("Please use a @strathmore.edu email address")
            } else {
                // Attempt to add the user to the database
                if (db.addUser(email, password)) {
                    toast("Account created successfully! You can now log in.")
                    finish() // Go back to the LoginActivity
                } else {
                    toast("Email already registered. Please use a different email.")
                }
            }
        }
    }

    // Helper function to show a Toast message
    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}