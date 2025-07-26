package com.example.colorgame.engine

import androidx.compose.runtime.remember


class GameEngineTwo {

    var state: GameState = generateNewRound()
        private set

    private var totalTime: Int = 15

    private var correctStreak = 0

    private var hasStarted = false

    fun onColorSelected(color: Long) {
        if (!hasStarted) {
            hasStarted = true
        }

        if (color == state.highlightedColor) {
            state = state.copy(isGameOver = true)
        } else {
            correctStreak++
            totalTime = 15
            val newSpeedLevel = 1 + (correctStreak / 20)
            state = generateNewRound().copy(
                timeRemaining = calculateTimeRemaining(newSpeedLevel),
                score = state.score + 1,
                speedLevel = newSpeedLevel
            )
        }
    }

    fun decrementTimer() {
        if (hasStarted && !state.isGameOver) {
            if (state.timeRemaining > 0) {
                state = state.copy(timeRemaining = state.timeRemaining - 1)
            } else {
                state = state.copy(isGameOver = true)
            }
        }
    }

    private fun calculateTimeRemaining(speedLevel: Int): Int {
        return (totalTime / speedLevel).coerceAtLeast(2)
    }

    private fun generateNewRound(): GameState {
        val allColors = colorGenerator()
        val highlighted = allColors.random()

        return GameState(
            colors = allColors.shuffled(),
            highlightedColor = highlighted,
            timeRemaining = totalTime
        )
    }

    private fun colorGenerator(): List<Long> {
        val colorPalette = listOf(
            0xFFE57373,
            0xFFF06292,
            0xFFBA68C8,
            0xFF9575CD,
            0xFF64B5F6,
            0xFF4FC3F7,
            0xFF4DD0E1,
            0xFF4DB6AC,
            0xFF81C784,
            0xFFAED581,
            0xFFFFD54F,
            0xFFFFB74D,
            0xFFA1887F,
            0xFFE0E0E0,
            0xFF90A4AE
        )

        val shuffledColors = colorPalette.distinct().shuffled().take(2)

        return shuffledColors

    }

}
