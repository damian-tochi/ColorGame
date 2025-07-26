package com.example.colorgame.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Game : Screen("game")
    data object GameTwo : Screen("game_two")
    data object GameThree : Screen("game_three")
    data object GameOver : Screen("gameOver")
    data object Settings : Screen("settings")
    data object Start : Screen("start")
    data object Multiplayer : Screen("multiplayer")
}