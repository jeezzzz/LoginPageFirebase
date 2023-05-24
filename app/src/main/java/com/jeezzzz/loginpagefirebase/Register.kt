package com.jeezzzz.loginpagefirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var et_email: EditText
    lateinit var et_password: EditText
    lateinit var et_username: EditText
    lateinit var et_confirmPassword: EditText
    lateinit var et_phone: EditText
    lateinit var btn_register: AppCompatButton
    lateinit var progressBar : ProgressBar

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = ProgressBar.GONE
        auth = FirebaseAuth.getInstance()
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        et_username = findViewById(R.id.et_username)
        et_confirmPassword = findViewById(R.id.et_confirmPassword)
        et_phone = findViewById(R.id.et_phone)
        btn_register = findViewById(R.id.btn_register)


        btn_register.setOnClickListener {
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            val username = et_username.text.toString()
            val confirmPassword = et_confirmPassword.text.toString()
            val phone = et_phone.text.toString()

            if (username.isEmpty()) {
                et_username.error = "Please enter username"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                et_email.error = "Please enter email"
                return@setOnClickListener
            }
            if (phone.isEmpty()) {
                et_phone.error = "Please enter phone"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                et_password.error = "Please enter password"
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                et_confirmPassword.error = "Please enter confirm password"
                return@setOnClickListener
            }


            progressBar.visibility = ProgressBar.VISIBLE
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        progressBar.visibility = ProgressBar.GONE
                       Toast.makeText( baseContext, "Authentication Successful.",
                        Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        progressBar.visibility = ProgressBar.GONE
                    }
                }
        }

    }
}