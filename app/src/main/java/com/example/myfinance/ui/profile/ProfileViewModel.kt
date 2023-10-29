package com.example.myfinance.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class ProfileViewModel : ViewModel() {

    private val auth = Firebase.auth

    private val _userEmail = MutableLiveData<String?>(auth.currentUser?.email)
    val userEmail: LiveData<String?> = _userEmail
}