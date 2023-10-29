package com.example.myfinance.ui.tools

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.R

class BudgetActivity : AppCompatActivity() {

    companion object {
        const val TOOL = "tool"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Budget Planner"
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}