package com.example.myfinance.ui.tools

import androidx.lifecycle.ViewModel
import com.example.myfinance.data.DataSource
import com.example.myfinance.data.Tool

class ToolsViewModel : ViewModel() {
    val allTools: List<Tool> = DataSource.tools
}