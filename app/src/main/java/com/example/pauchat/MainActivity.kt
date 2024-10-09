package com.example.pauchat

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity: AppCompatActivity() {
    private lateinit var signOutButton: Button
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        signOutButton = findViewById(R.id.btn_signout)

        signOutButton.setOnClickListener {
            signOut()
        }
    }

    private fun reload() {
    }

    private fun showError(message: String) {
        val toast = Toast.makeText(this, message,
            Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }

    private fun signOut() {
        auth.signOut()
        showError("Logged out.")
    }
}