package com.example.pauchat

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
class StartActivity : AppCompatActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: Button
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
        setContentView(R.layout.activity_start)
        usernameInput = findViewById(R.id.username)
        passwordInput = findViewById(R.id.password)
        loginButton = findViewById(R.id.btn_login)
        signupButton = findViewById(R.id.btn_signup)

        loginButton.setOnClickListener {
            val usernameInputTxt = usernameInput.text.toString()
            val passwordInputTxt  = passwordInput.text.toString()
            if (isValidEmail(usernameInputTxt) && isPassNotEmpty(passwordInputTxt)) {
                println("Email: $usernameInputTxt")
                println("Password: $passwordInputTxt")
                Log.i("Test Credentials",
                    "Email: $usernameInputTxt Password $passwordInputTxt")
                logIn(usernameInputTxt, passwordInputTxt)
            }
        }

        signupButton.setOnClickListener {
            val usernameInputTxt = usernameInput.text.toString()
            val passwordInputTxt = passwordInput.text.toString()
            if (isValidEmail(usernameInputTxt) && isPassNotEmpty(passwordInputTxt)) {
                println("Email: $usernameInputTxt")
                println("Password: $passwordInputTxt")
                Log.i("Test Credentials",
                    "Email: $usernameInputTxt Password $passwordInputTxt")
                signUp(usernameInputTxt, passwordInputTxt)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        if (email == "") {
            return false
        }
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (email.matches(emailPattern.toRegex())) {
            return true
        }
        showError("Incorrect email format.")
        return false
    }

    private fun isPassNotEmpty(password: String): Boolean {
        return password != ""
    }

    private fun showError(message: String) {
        val toast = Toast.makeText(this, message,
            Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }

    private fun reload() {
    }

    private fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showError("Authentication was successful.")
                } else {
                    showError("User not registered.")
                }
            }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showError("New user created.")
                    reload()
                } else {
                    showError("User already registered.")
                }
            }
    }
}