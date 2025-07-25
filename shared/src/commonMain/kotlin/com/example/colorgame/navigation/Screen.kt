package com.example.colorgame.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Game : Screen("game")
    data object GameOver : Screen("gameOver")
    data object Settings : Screen("settings")
    data object Start : Screen("start")
}