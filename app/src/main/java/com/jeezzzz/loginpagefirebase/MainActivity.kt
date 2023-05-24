package com.jeezzzz.loginpagefirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var et_email: EditText
    lateinit var et_phone: EditText
    lateinit var et_username: EditText
    lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)
        val btn_signout = findViewById<Button>(R.id.btn_signout)
        et_email = findViewById(R.id.et_email)
        et_phone = findViewById(R.id.et_phone)
        et_username = findViewById(R.id.et_username)
        dbRef= FirebaseDatabase.getInstance().getReference("Users")
        val btn_saveData = findViewById<Button>(R.id.btn_saveData)
        btn_saveData.setOnClickListener {
            saveData()
        }
        btn_signout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveData() {
         val email=et_email.text.toString()
            val phone=et_phone.text.toString()
            val username=et_username.text.toString()
        if (email.isBlank()) {
            et_email.error = "Please enter name"
        }
        if (username.isBlank()) {
           et_username.error = "Please enter age"
        }
        if (phone.isBlank()) {
            et_phone.error = "Please enter salary"
        }
        val userId = dbRef.push().key!!
         val user=UserData(userId,email,phone,username)
        dbRef.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this,"Data saved successfully",Toast.LENGTH_SHORT).show()
            et_email.text.clear()
            et_phone.text.clear()
            et_username.text.clear()


        }.addOnFailureListener { err ->
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
        }

    }


    }
