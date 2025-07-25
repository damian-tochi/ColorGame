package com.example.colorgame.engine

data class GameState(
    val colors: List<Long>,
    val highlightedColor: Long,
    val timeRemaining: Int,
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val speedLevel: Int = 1
)