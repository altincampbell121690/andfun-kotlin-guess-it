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

import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding
import timber.log.Timber

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {
    private lateinit var gameViewModel: GameViewModel


    // The list of words - the front of the list is the next word to guess


    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        // TODO (04) Create and initialize a GameViewModel, using ViewModelProvider; Add a log
        // it takes in the owner of the viewModel which is the gragment
        Timber.i("Called viewModelProviders!!!")
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        // statement

        resetList()
        nextWord()

        binding.correctButton.setOnClickListener { gameViewModel.onCorrect() }
        binding.skipButton.setOnClickListener { gameViewModel.onSkip() }
        updateScoreText()
        updateWordText()
        return binding.root

    }

    private fun resetList() {
        gameViewModel.resetList()
    }


    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(gameViewModel.score)
        findNavController(this).navigate(action)
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (gameViewModel.nextWord()) {
            updateWordText()
            updateScoreText()
        } else {
            gameFinished()
        }

    }

    private fun updateWordText() {
        binding.wordText.text = gameViewModel.word

    }

    private fun updateScoreText() {
        binding.scoreText.text = gameViewModel.score.toString()
    }
}
