package com.example.colorgame.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.colorgame.android.screens.GameOverScreen
import com.example.colorgame.android.screens.GameScreen
import com.example.colorgame.android.screens.LevelThreeGameScreen
import com.example.colorgame.android.screens.LevelTwoGameScreen
import com.example.colorgame.android.screens.MultiPlayerScreen
import com.example.colorgame.android.screens.SettingsScreen
import com.example.colorgame.android.screens.SplashScreen
import com.example.colorgame.android.screens.StartScreen
import com.example.colorgame.engine.GameEngine
import com.example.colorgame.engine.GameEngineThree
import com.example.colorgame.engine.GameEngineTwo
import com.example.colorgame.navigation.Screen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavHost()
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen { navController.navigate(Screen.Start.route) }
        }
        composable(Screen.Game.route) {
            val gameEngine = remember { GameEngine() }
            GameScreen(navController, gameEngine)
        }
        composable(Screen.GameTwo.route) {
            val gameEngine = remember { GameEngineTwo() }
            LevelTwoGameScreen(navController, gameEngine)
        }
        composable(Screen.GameThree.route) {
            val gameEngine = remember { GameEngineThree() }
            LevelThreeGameScreen(navController, gameEngine)
        }
        composable("${Screen.GameOver.route}/{score}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            GameOverScreen(navController, score)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(Screen.Start.route) {
            StartScreen(navController)
        }
        composable(Screen.Multiplayer.route) {
            MultiPlayerScreen(navController)
        }
    }
}
