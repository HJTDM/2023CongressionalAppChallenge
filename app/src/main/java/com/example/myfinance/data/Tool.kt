package com.example.myfinance.data

import androidx.annotation.DrawableRes

/**
 * A data class to represent the information
 * on a tool card in the tools menu
 */
data class Tool(
    @DrawableRes val imageResourceId: Int,
    val name: String
)
