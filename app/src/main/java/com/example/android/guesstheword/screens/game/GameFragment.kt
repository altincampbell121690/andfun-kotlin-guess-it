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

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
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

        // Get the viewmodel
        Log.i("GameFragment", "Called ViewModelProvider")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        //set viewModel for xml binding
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this // using this i have linked the view model directly to the view
        // TODO (04) Update these onClickListeners to refer to call methods in the ViewModel then
    /***
     * NOTE: we no longer need to set onclick listeners in the fragmentUI if we use xml binding
        // update the UI
        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }

     ***/
        // TODO (0x) Set up Oberservation Connection
// needs two things for live data observer, owner and an Observer Object
    /*****
     * NOTE: WE no longer need this if set binding lifecylce owner - view will be auto updated
      *  viewModel.score.observe(
       *         this.viewLifecycleOwner,
        *        Observer { newScore ->
         *           binding.scoreText.text = newScore.toString()
          *      })

      *viewModel.word.observe(this.viewLifecycleOwner, Observer { newWord ->
       *     binding.wordText.text = newWord
        *})
*/
        viewModel.eventGameFinish.observe(this.viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) {
                gameFinished()
                viewModel.onGameFinishedComplete()
            }
        })

        viewModel.buzzPattern.observe(this.viewLifecycleOwner, Observer { buzzType ->
            if (buzzType != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        })
        /*viewModel.currentTime.observe(this.viewLifecycleOwner, Observer { timeLeft ->
            binding.timerText.text = timeLeft
        })*/
        return binding.root

    }

    // TODO (02) Move over methods resetList, nextWord, onSkip and onCorrect to the GameViewModel

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
    }


    /** Methods for updating the UI **/

    // TODO (05) Update these methods to get word and score from the viewmodel

    fun buzz(pattern:LongArray){
        val buzzer = activity?.getSystemService<Vibrator>()

        buzzer?.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                buzzer.vibrate(VibrationEffect.createWaveform(pattern,-1))
            }else{
                buzzer.vibrate(pattern,-1)
            }
        }

    }
}
