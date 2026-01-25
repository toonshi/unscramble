package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel: ViewModel() {
private val _uiState = MutableStateFlow(GameUIState())
    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()
    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()

    private fun pickCurrentWordAndShuffle(): String {
        val currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickCurrentWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }
    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(word) ) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }
    private fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUIState(currentScrambledWord = pickCurrentWordAndShuffle())
    }
    init {
        resetGame()
    }
}
