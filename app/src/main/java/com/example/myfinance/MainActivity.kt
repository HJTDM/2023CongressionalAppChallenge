package com.example.myfinance

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myfinance.databinding.ActivityMainBinding
import com.example.myfinance.ui.profile.OnDataPass
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class MainActivity : AppCompatActivity(), OnDataPass {

    private lateinit var binding: ActivityMainBinding
    private lateinit var signInIntent: Intent
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_activity_main
        ) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_tools, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser == null) {
            launchSignInActivity()
        }

    }

    override fun onDataPass(data: String) {
        Firebase.auth.signOut()
        launchSignInActivity()
    }

    private fun launchSignInActivity() {
        signInIntent = Intent(this, SignInActivity::class.java)
        startActivity(signInIntent)
        finish()
    }


}