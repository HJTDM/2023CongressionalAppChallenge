package com.example.myfinance.data

import com.google.firebase.database.IgnoreExtraProperties

/**
 * A data class to represent a user and its credentials
 */
@IgnoreExtraProperties
data class User(val username: String? = null, val email: String? = null)