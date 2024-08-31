package com.example.myfinance.ui.tools.budget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController


class BudgetActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.myfinance.R.layout.activity_budget)

        // Configure fragment container and navigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(com.example.myfinance.R.id.nav_host_fragment_activity_budget) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    // Allows for backwards navigation
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}