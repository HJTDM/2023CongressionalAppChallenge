package com.example.myfinance.data

import androidx.annotation.DrawableRes

/**
 * A data class to represent the information presented in the dog card
 */
data class Lesson(
    @DrawableRes val imageResourceId: Int,
    val name: String
)
