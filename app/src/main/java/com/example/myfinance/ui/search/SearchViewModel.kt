package com.example.myfinance.ui.search

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinance.data.DataSource
import com.example.myfinance.data.Lesson

class SearchViewModel : ViewModel() {

    private val _unit1Lessons = MutableLiveData<List<Lesson>>(DataSource.unit1Lessons)
    val unit1Lessons: LiveData<List<Lesson>> = _unit1Lessons
    val unit1TitleVisibility = MutableLiveData<Int>(0)

    private val _unit2Lessons = MutableLiveData<List<Lesson>>(DataSource.unit2Lessons)
    val unit2Lessons: LiveData<List<Lesson>> = _unit2Lessons
    val unit2TitleVisibility = MutableLiveData<Int>(0)

    private val _unit3Lessons = MutableLiveData<List<Lesson>>(DataSource.unit3Lessons)
    val unit3Lessons: LiveData<List<Lesson>> = _unit3Lessons
    val unit3TitleVisibility = MutableLiveData<Int>(0)

    private val _unit4Lessons = MutableLiveData<List<Lesson>>(DataSource.unit4Lessons)
    val unit4Lessons: LiveData<List<Lesson>> = _unit4Lessons
    val unit4TitleVisibility = MutableLiveData<Int>(0)

    private val _unit5Lessons = MutableLiveData<List<Lesson>>(DataSource.unit5Lessons)
    val unit5Lessons: LiveData<List<Lesson>> = _unit5Lessons
    val unit5TitleVisibility = MutableLiveData<Int>(0)

    private val _unit6Lessons = MutableLiveData<List<Lesson>>(DataSource.unit6Lessons)
    val unit6Lessons: LiveData<List<Lesson>> = _unit6Lessons
    val unit6TitleVisibility = MutableLiveData<Int>(0)

    // Update lesson list for each unit based on search keyword
    fun filterLessons(word: String){
        _unit1Lessons.value = DataSource.unit1Lessons.filter {
            it.name.contains(word, true)
        }

        _unit2Lessons.value = DataSource.unit2Lessons.filter {
            it.name.contains(word, true)
        }

        _unit3Lessons.value = DataSource.unit3Lessons.filter {
            it.name.contains(word, true)
        }

        _unit4Lessons.value = DataSource.unit4Lessons.filter {
            it.name.contains(word, true)
        }

        _unit5Lessons.value = DataSource.unit5Lessons.filter {
            it.name.contains(word, true)
        }

        _unit6Lessons.value = DataSource.unit6Lessons.filter {
            it.name.contains(word, true)
        }

        toggleTitleVisibility()
    }

    // Reset lesson lists back to all lessons
    fun resetLessons(){
        _unit1Lessons.value = DataSource.unit1Lessons
        _unit2Lessons.value = DataSource.unit2Lessons
        _unit3Lessons.value = DataSource.unit3Lessons
        _unit4Lessons.value = DataSource.unit4Lessons
        _unit5Lessons.value = DataSource.unit5Lessons
        _unit6Lessons.value = DataSource.unit6Lessons
        toggleTitleVisibility()
    }

    // Hide unit titles if lesson list is empty
    private fun toggleTitleVisibility(){
        unit1TitleVisibility.value = if (_unit1Lessons.value.isNullOrEmpty()) View.GONE else View.VISIBLE
        unit2TitleVisibility.value = if (_unit2Lessons.value.isNullOrEmpty()) View.GONE else View.VISIBLE
        unit3TitleVisibility.value = if (_unit3Lessons.value.isNullOrEmpty()) View.GONE else View.VISIBLE
        unit4TitleVisibility.value = if (_unit4Lessons.value.isNullOrEmpty()) View.GONE else View.VISIBLE
        unit5TitleVisibility.value = if (_unit5Lessons.value.isNullOrEmpty()) View.GONE else View.VISIBLE
        unit6TitleVisibility.value = if (_unit6Lessons.value.isNullOrEmpty()) View.GONE else View.VISIBLE
    }
}