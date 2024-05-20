package com.example.diplom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        auth = FirebaseAuth.getInstance()

        val emailEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val confpasswordEditText: EditText = findViewById(R.id.confpassword)
        val submitButton: Button = findViewById(R.id.loginButton)
        val signinText: TextView = findViewById(R.id.signupText)

        submitButton.setOnClickListener {
            val enteredEmail = emailEditText.text.toString().trim()
            val enteredPassword = passwordEditText.text.toString().trim()
            val enteredConfPassword = confpasswordEditText.text.toString().trim()
            if (enteredEmail.isNotEmpty() && enteredPassword.isNotEmpty() && enteredPassword==enteredConfPassword) {
                register(enteredEmail, enteredPassword)
            }
        }
        signinText.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { registerTask ->
                if (registerTask.isSuccessful) {
                    navigateToFirst()
                } else {
                    Toast.makeText(this,"Ошибка", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToFirst() {
        val intent = Intent(this, FirstScreen::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}
