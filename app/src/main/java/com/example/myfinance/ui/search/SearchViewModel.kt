package com.example.myfinance.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinance.data.DataSource
import com.example.myfinance.data.Lesson

class SearchViewModel : ViewModel() {

    private val _unit1Lessons = MutableLiveData<List<Lesson>>(
        DataSource.unit1Lessons
    )
    val unit1Lessons: LiveData<List<Lesson>> = _unit1Lessons

    fun filterLessons(word: String){
        _unit1Lessons.value = DataSource.unit1Lessons.filter {
            it.name.contains(word, true)
        }
    }

    fun resetLessons(){
        _unit1Lessons.value = DataSource.unit1Lessons
    }
}