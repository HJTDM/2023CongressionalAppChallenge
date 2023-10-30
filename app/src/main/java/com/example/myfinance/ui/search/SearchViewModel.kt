package com.example.myfinance.ui.search

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinance.data.DataSource
import com.example.myfinance.data.Lesson

class SearchViewModel : ViewModel() {

    private var searchFragment: SearchFragment = SearchFragment()

    private val _unit1Lessons = MutableLiveData<List<Lesson>>(
        DataSource.unit1Lessons
    )
    val unit1Lessons: LiveData<List<Lesson>> = _unit1Lessons

    private val _unit2Lessons = MutableLiveData<List<Lesson>>(
        DataSource.unit2Lessons
    )
    val unit2Lessons: LiveData<List<Lesson>> = _unit2Lessons

    private val _unit3Lessons = MutableLiveData<List<Lesson>>(
        DataSource.unit3Lessons
    )
    val unit3Lessons: LiveData<List<Lesson>> = _unit3Lessons

    private val _unit4Lessons = MutableLiveData<List<Lesson>>(
        DataSource.unit4Lessons
    )
    val unit4Lessons: LiveData<List<Lesson>> = _unit4Lessons

    private val _unit5Lessons = MutableLiveData<List<Lesson>>(
        DataSource.unit5Lessons
    )
    val unit5Lessons: LiveData<List<Lesson>> = _unit5Lessons

    private val _unit6Lessons = MutableLiveData<List<Lesson>>(
        DataSource.unit6Lessons
    )
    val unit6Lessons: LiveData<List<Lesson>> = _unit6Lessons

    fun filterLessons(word: String){

        val list: MutableList<Boolean> = mutableListOf()
        _unit1Lessons.value = DataSource.unit1Lessons.filter {
            it.name.contains(word, true)
        }
        if(_unit1Lessons.value!!.isEmpty()){
            searchFragment.hideText(1)
        }else{
            searchFragment.showText(1)
        }
        _unit2Lessons.value = DataSource.unit2Lessons.filter {
            it.name.contains(word, true)
        }
        if(_unit2Lessons.value!!.isEmpty()){
            searchFragment.hideText(2)
        }else{
            searchFragment.showText(2)
        }
        _unit3Lessons.value = DataSource.unit3Lessons.filter {
            it.name.contains(word, true)
        }
        if(_unit3Lessons.value!!.isEmpty()){
            searchFragment.hideText(3)
        }else{
            searchFragment.showText(3)
        }
        _unit4Lessons.value = DataSource.unit4Lessons.filter {
            it.name.contains(word, true)
        }
        if(_unit4Lessons.value!!.isEmpty()){
            searchFragment.hideText(4)
        }else{
            searchFragment.showText(4)
        }
        _unit5Lessons.value = DataSource.unit5Lessons.filter {
            it.name.contains(word, true)
        }
        if(_unit5Lessons.value!!.isEmpty()){
            searchFragment.hideText(5)
        }else{
            searchFragment.showText(5)
        }
        _unit6Lessons.value = DataSource.unit6Lessons.filter {
            it.name.contains(word, true)
        }
        if(_unit6Lessons.value!!.isEmpty()){
            searchFragment.hideText(6)
        }else{
            searchFragment.showText(6)
        }
    }

    fun resetLessons(){
        _unit1Lessons.value = DataSource.unit1Lessons
        _unit2Lessons.value = DataSource.unit2Lessons
        _unit3Lessons.value = DataSource.unit3Lessons
        _unit4Lessons.value = DataSource.unit4Lessons
        _unit5Lessons.value = DataSource.unit5Lessons
        _unit6Lessons.value = DataSource.unit6Lessons
        searchFragment.showText(1)
        searchFragment.showText(2)
        searchFragment.showText(3)
        searchFragment.showText(4)
        searchFragment.showText(5)
        searchFragment.showText(6)

    }
}