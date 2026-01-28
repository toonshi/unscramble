package com.example.unscramble.ui

import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import com.example.unscramble.ui.GameViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel()

    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value

        // Use the helper function to get the actual word from the scrambled one
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value

        // Assert that checkUserGuess() method updates isGuessedWordWrong correctly
        assertFalse(currentGameUiState.isGuessedWordWrong)

        // Assert that score is updated correctly
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        //for an incorrect word as input
        val incorrectPlayerWord = "lim"
        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()
        val currentGameUiState = viewModel.uiState.value

        //check if score is 0
        assertEquals(0, currentGameUiState.score)
        assertTrue(currentGameUiState.isGuessedWordWrong)

    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        //Assert that the current word is scrambled
        assertNotEquals(unScrambledWord, gameUiState.currentScrambledWord)

        //Assert that the current word count is set to 1
        assertEquals(1, gameUiState.currentWordCount)

        //Assert that initially score is set to 0
        assertEquals(0, gameUiState.score)

        //Assert that the wrong word guessed flag is false
        assertFalse(gameUiState.isGuessedWordWrong)

        //Assert that game is not over
        assertFalse(gameUiState.isGameOver)
    }
}