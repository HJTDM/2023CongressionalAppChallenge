package com.example.myfinance.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import java.util.Calendar

class HomeViewModel : ViewModel() {

    private val _username = MutableLiveData<String>()

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun setUsername(username: String){
        _username.value = username
    }

    fun setWelcomeText(){
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        // Adjust text by time of day
        _text.value = when(hour){
            in 6..< 12 -> "Good Morning, ${_username.value}"
            in 12..< 18 -> "Good Afternoon, ${_username.value}"
            else -> "Good Evening, ${_username.value}"
        }
    }
}