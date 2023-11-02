package com.example.myfinance.ui.lessons.unit2.lesson5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myfinance.R

class Unit2Lesson5Activity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit2_lesson5)

        val navHostFragment = supportFragmentManager
            .findFragmentById(com.example.myfinance.R.id.nav_host_fragment_activity_unit2_lesson5)
                as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}