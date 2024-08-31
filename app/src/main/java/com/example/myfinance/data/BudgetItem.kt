package com.example.myfinance.data

import com.google.firebase.database.IgnoreExtraProperties
import java.text.NumberFormat

/**
 * A data class that represents an item in the budget list
 */
@IgnoreExtraProperties
data class BudgetItem(
    val name: String? = null,
    val price: Double? = null
)

fun BudgetItem.getFormattedPrice(): String =
    NumberFormat.getCurrencyInstance().format(price)