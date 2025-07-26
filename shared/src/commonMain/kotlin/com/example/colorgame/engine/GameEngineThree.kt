package com.example.colorgame.engine


class GameEngineThree {

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
            0xFF000080,
            0xFF008000,
            0xFFFDB515,
            0xFFFF0000,
            0xFF1E90FF,
            0xFF32CD32,
            0xFFFFA500,
            0xFF8B0000,
            0xFF00CED1,
            0xFFDC143C,
            0xFFFF1493,
            0xFF4169E1,
            0xFF00FF00,
            0xFFFF4500,
            0xFFFFFF00,
            0xFF7CFC00,
            0xFF00BFFF,
            0xFFDA70D6,
            0xFF00FF7F,
            0xFF9932CC,
            0xFFADFF2F,
            0xFFFF69B4,
            0xFFB22222,
            0xFF40E0D0,
            0xFFFF6347 
        )
        val selectedColor = colorPalette.shuffled().take(1)
        val shuffledColors = generateColorShades(selectedColor[0])

        return shuffledColors

    }

    fun generateColorShades(baseColor: Long, shades: Int = 4): List<Long> {
        val factors = listOf(0.5f, 0.75f, 1.25f, 1.5f)
        return factors.take(shades).map { factor ->
            adjustColorShade(baseColor, factor)
        }
    }

    private fun adjustColorShade(color: Long, factor: Float): Long {
        val alpha = (color shr 24 and 0xFF).toInt()
        val red = ((color shr 16 and 0xFF) * factor).coerceIn(0f, 255f).toInt()
        val green = ((color shr 8 and 0xFF) * factor).coerceIn(0f, 255f).toInt()
        val blue = ((color and 0xFF) * factor).coerceIn(0f, 255f).toInt()
        return (alpha.toLong() shl 24) or
                (red.toLong() shl 16) or
                (green.toLong() shl 8) or
                blue.toLong()
    }

}
