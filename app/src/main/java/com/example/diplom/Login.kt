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

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = FirebaseAuth.getInstance()

        val emailEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val submitButton: Button = findViewById(R.id.loginButton)
        val signupText: TextView = findViewById(R.id.signupText)

        submitButton.setOnClickListener {
            val enteredEmail = emailEditText.text.toString().trim()
            val enteredPassword = passwordEditText.text.toString().trim()
            if (enteredEmail.isNotEmpty() && enteredPassword.isNotEmpty()) {
                login(enteredEmail, enteredPassword)
            }
        }
        signupText.setOnClickListener {
            navigateToRegister()
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToFirst()
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { loginTask ->
                if (loginTask.isSuccessful) {
                    navigateToFirst()
                } else {
                    Toast.makeText(this,"Пользователь не найден",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToFirst() {
        val intent = Intent(this, FirstScreen::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }
}
