package com.example.myfinance

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.data.User
import com.example.myfinance.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var signInIntent: Intent
    private lateinit var mainIntent: Intent

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            launchMainActivity()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        database = Firebase.database.reference

        binding.registerButton.setOnClickListener{
            registerNewUser()
        }

        binding.signInNow.setOnClickListener{
            launchSignInActivity()
        }

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
    }

    private fun registerNewUser(){
        binding.registerProgressBar.visibility = View.VISIBLE
        val username = binding.registerUsernameEditText.text.toString()
        val email = binding.registerEmailEditText.text.toString()
        val password = binding.registerPasswordEditText.text.toString()

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
            binding.registerProgressBar.visibility = View.GONE
            return
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
            binding.registerProgressBar.visibility = View.GONE
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.registerProgressBar.visibility = View.GONE

                    auth.currentUser?.let {
                        val user = User(username, email)
                        database.child("users").child(it.uid).setValue(user)
                    }

                    launchSignInActivity()
                } else {
                    binding.registerProgressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
    private fun launchSignInActivity(){
        signInIntent = Intent(this, SignInActivity::class.java)
        startActivity(signInIntent)
        finish()
    }

    private fun launchMainActivity(){
        mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
}