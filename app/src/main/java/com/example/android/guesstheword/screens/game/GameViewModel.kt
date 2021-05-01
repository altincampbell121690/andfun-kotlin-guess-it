/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import androidx.lifecycle.ViewModel
import timber.log.Timber

// TODO (02) Create the GameViewModel class, extending ViewModel
class GameViewModel : ViewModel() {
    // The current word
    var word = ""

    // The current score
    var score = 0

    //list of gamewords
    private lateinit var wordList: MutableList<String>

    // TODO (03) Add init and override onCleared; Add log statements to both
    init {
        Timber.i("GameViewModel Created!!")
        resetList()
        nextWord()
    }

    // view models get cleared right before the fragment/activity it is associated with is completely destroyed
    override fun onCleared() {
        super.onCleared()
        Timber.i("GamViewModel has ben destroyed!!")
    }

    /**
     * Resets the list of words and randomizes the order
     */

    fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    fun nextWord(){
        if (wordList.isEmpty()) {
        } else {
            word = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        score--
        nextWord()
    }

    fun onCorrect() {
        score++
        nextWord()
    }




}

