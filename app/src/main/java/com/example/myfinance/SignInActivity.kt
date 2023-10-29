package com.example.myfinance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.myfinance.databinding.ActivitySignInBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var mainIntent: Intent
    private lateinit var registerIntent: Intent
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            launchMainActivity()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signInButton.setOnClickListener{
            signInUser()
        }

        binding.registerNow.setOnClickListener {
            launchRegisterActivity()
        }

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
    }

    private fun signInUser(){
        binding.signInProgressBar.visibility = View.VISIBLE
        val email = binding.loginEmailEditText.text.toString()
        val password = binding.loginPasswordEditText.text.toString()

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
            binding.signInProgressBar.visibility = View.GONE
            return
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
            binding.signInProgressBar.visibility = View.GONE
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.signInProgressBar.visibility = View.GONE
                    Toast.makeText(this, "Sign In Successful.", Toast.LENGTH_SHORT).show()
                    launchMainActivity()
                } else {
                    binding.signInProgressBar.visibility = View.GONE
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun launchMainActivity(){
        mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun launchRegisterActivity(){
        registerIntent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)
        finish()
    }
}