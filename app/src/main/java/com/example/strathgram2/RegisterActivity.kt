package com.example.strathgram2


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val db = AppDatabase(this)
        val email = findViewById<EditText>(R.id.regEmail)
        val pass  = findViewById<EditText>(/* id = */ R.id.regPass)
        val btn   = findViewById<Button>(/* id = */ R.id.regBtn)

        btn.setOnClickListener {
            val e = email.text.toString().trim()
            val p = pass.text.toString()

            if (!e.endsWith("@strathmore.edu")) {
                toast("Use strathmore email")
            } else if (e.isEmpty() || p.isEmpty()) {
                toast("Fill all fields")
            } else if (db.addUser(e, p)) {
                toast("Account created ✔")
                finish()                    // back to Login
            } else toast("Email already used")
        }
    }
    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
