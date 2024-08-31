package com.example.myfinance.data

import androidx.annotation.DrawableRes

/**
 * A data class to represent the information
 * on a lesson card in the search menu
 */
data class Lesson(
    @DrawableRes val imageResourceId: Int,
    val name: String
)