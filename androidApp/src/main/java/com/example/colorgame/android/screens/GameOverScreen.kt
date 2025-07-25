package com.example.colorgame.android.screens

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.colorgame.android.R
import com.example.colorgame.android.components.GameButton
import com.example.colorgame.android.util.BackgroundSoundPlayer


@Composable
fun GameOverScreen(navController: NavController, score: Int) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        context.getSharedPreferences("color_game_prefs", Context.MODE_PRIVATE)
    }
    val isMuted = sharedPreferences.getBoolean("mute_sounds", false)
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.embrace_effect).apply {
            isLooping = true
            setVolume(0.5f, 0.5f)
        }
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("Game Over.json"))
    val progress by animateLottieCompositionAsState(
        composition,
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1c1b2b)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text("Your Score: $score", color = Color.White, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Highest Score: ${sharedPreferences.getInt("max_score", 0)}",
            color = Color.White,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        GameButton(
            onClick = {
                navController.navigate("game") {
                    popUpTo("splash") { inclusive = true }
                }
            }, buttonIcon = Icons.Default.Refresh,
            color = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1B4D3F),
                contentColor = Color.White
            ), title = "Play Again"
        )

        Spacer(modifier = Modifier.height(16.dp))

        GameButton(
            onClick = {
                navController.navigate("start") {
                    popUpTo(0) { inclusive = true }
                }
            }, buttonIcon = Icons.Default.Home,
            color = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ), title = "Main Menu"
        )

        Spacer(modifier = Modifier.weight(1f))

    }
}
