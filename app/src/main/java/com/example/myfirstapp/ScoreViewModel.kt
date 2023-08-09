package com.example.myfirstapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {
    private val scoreLiveData = MutableLiveData<Int>()

    fun setScore(score: Int) {
        scoreLiveData.value = score
        Log.w("debug", "setScore $score")
    }

    fun getScore() : LiveData<Int> {
        return scoreLiveData
    }

    fun addToScore(addValue: Int) {
        scoreLiveData.value = (scoreLiveData.value ?: 0) + addValue
    }
}