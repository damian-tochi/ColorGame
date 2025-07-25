package com.example.colorgame.android.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.colorgame.android.R
import com.example.colorgame.android.components.GameButton
import com.example.colorgame.android.components.MultiColorText
import com.example.colorgame.android.util.BackgroundSoundPlayer


@Composable
fun StartScreen(navController: NavController) {
    val context = LocalContext.current

    val rainbowColor = listOf(
        Color.Red, Color(0xFFFFA500), Color.Yellow,
        Color.Green, Color.Cyan, Color.Blue, Color.Magenta
    )

    val prefs = context.getSharedPreferences("color_game_prefs", Context.MODE_PRIVATE)
    val hasSavedGame = prefs.contains("last_score")

    val isMuted = prefs.getBoolean("mute_sounds", false)

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("MOVING STAR.json"))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(Unit) {
        if (!isMuted) {
            BackgroundSoundPlayer.start(context)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            BackgroundSoundPlayer.stop()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1c1b2b))
            .padding(16.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(Modifier.weight(1f))
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                MultiColorText(
                    text = "Color Game",
                    fontSize = 38.sp,
                    letterColors = rainbowColor,
                    defaultColor = Color.White,
                    modifier = Modifier.padding(8.dp)
                )


                Image(
                    painter = rememberAsyncImagePainter(R.drawable.ic_logo),
                    contentDescription = "Game Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Transparent)
                        .padding(4.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            GameButton(
                onClick = {
                    prefs.edit().clear().apply()
                    navController.navigate("game")
                }, buttonIcon = Icons.Default.PlayArrow,
                color = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF008000),
                    contentColor = Color.White
                ), title = "New Game"
            )

            if (hasSavedGame) {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate("game?continue=true")
                    },
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text("Continue Game")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            GameButton(
                onClick = {
                    prefs.edit().clear().apply()
                }, buttonIcon = Icons.Default.Refresh, color = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF5350),
                    contentColor = Color.White
                ), title = "Reset Game"
            )

            Spacer(modifier = Modifier.height(16.dp))

            GameButton(
                onClick = {
                    navController.navigate("settings")
                }, buttonIcon = Icons.Default.Settings,
                color = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA500),
                    contentColor = Color.White
                ), title = "Settings"
            )

            Spacer(Modifier.weight(1f))
        }
    }
}
