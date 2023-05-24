package com.jeezzzz.loginpagefirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var btn_login: AppCompatButton
    private lateinit var btn_register: Button
    private lateinit var progressBar : ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = ProgressBar.GONE
        auth = FirebaseAuth.getInstance()
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        btn_login = findViewById(R.id.btn_login)
        btn_register = findViewById(R.id.btn_register)



        btn_login.setOnClickListener {
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            if (email.isEmpty()) {
                et_email.error = "Please enter email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                et_password.error = "Please enter password"
                return@setOnClickListener
            }
            progressBar.visibility = ProgressBar.VISIBLE // Update visibility to VISIBLE here

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        progressBar.visibility = ProgressBar.GONE
                        Toast.makeText(baseContext, "Login successful.",
                            Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        progressBar.visibility = ProgressBar.GONE
                        Toast.makeText(baseContext, "Login failed.make sure you have an Account",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }

        btn_register.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }


    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}