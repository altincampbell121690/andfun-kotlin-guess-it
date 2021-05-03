package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException


/***
 * Note that the constructor of your view model
 * factory should take any parameters
 * you want to pass into your ScoreViewModel.
 * In this case, it takes in the final score.
 * */
@Suppress("UNCHECKED_CAST")
class ScoreViewModelFactory(private val finalScore:Int):ViewModelProvider.Factory {
    /***
     * The create method's purpose is to create and return your view model.
     * So you should construct a new ScoreViewModel and return it.
     * You'll also need to deal with the generics, so the statement will be:
     * */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)){
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}