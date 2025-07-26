package com.example.colorgame.android.screens


import ProgressIndicator
import android.content.Context
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.colorgame.android.R
import com.example.colorgame.android.components.BouncyClickBox
import com.example.colorgame.android.components.HeaderBox
import com.example.colorgame.android.components.ScoreText
import com.example.colorgame.android.util.backgroundColorForSpeedLevel
import com.example.colorgame.android.util.rememberClickSound
import com.example.colorgame.android.util.rememberFailSound
import com.example.colorgame.engine.GameEngineTwo
import kotlinx.coroutines.delay



@Composable
fun LevelTwoGameScreen(navController: NavController, gameEngine: GameEngineTwo) {
    val gameState = remember { mutableStateOf(gameEngine.state) }
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val sharedPreferences = context.getSharedPreferences("color_game_prefs", Context.MODE_PRIVATE)
    val tapSound = rememberClickSound(context)
    val failSound = rememberFailSound(context)
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.soft_background).apply {
            isLooping = true
            setVolume(0.3f, 0.3f)
        }
    }

    val isMuted = sharedPreferences.getBoolean("mute_sounds", false)

    LaunchedEffect(gameState.value.speedLevel) {
        if (!isMuted) {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
            val speed = 1.0f + (gameState.value.speedLevel - 1) * 0.1f
            mediaPlayer.playbackParams = PlaybackParams().setSpeed(speed.coerceAtMost(2.0f))

        }
    }

    LaunchedEffect(Unit) {
        while (!gameState.value.isGameOver) {
            delay(1000L / gameState.value.speedLevel)
            gameEngine.decrementTimer()
            gameState.value = gameEngine.state
        }
        val maxScore = sharedPreferences.getInt("max_score", 0)
        if (gameState.value.score > maxScore) {
            sharedPreferences.edit().putInt("max_score", gameState.value.score).apply()
        }

        mediaPlayer.stop()
        mediaPlayer.release()

        navController.navigate("gameOver/${gameState.value.score}") {
            popUpTo("game") { inclusive = true }
        }
    }

    val targetColor = backgroundColorForSpeedLevel(gameState.value.speedLevel)
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    var showLottie by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedColor)
            .padding(30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {
            ScoreText(score = gameState.value.score)
            Spacer(Modifier.weight(1f))
            Text("Level: ${gameState.value.speedLevel}",  color = Color.White, fontSize = 14.sp)
        }
        Spacer(Modifier.weight(1f))

        HeaderBox(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp)
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            targetColor = Color(gameState.value.highlightedColor),
        )

        ProgressIndicator(progress = gameState.value.timeRemaining.toFloat()/15)

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(22.dp),
            horizontalArrangement = Arrangement.spacedBy(22.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f)
        ) {
            items(gameState.value.colors) { colorLong ->

                Box {

                    BouncyClickBox(
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                            }
                        },
                        targetColor = Color(colorLong),
                        onClick = {
                            gameEngine.onColorSelected(colorLong)
                            gameState.value = gameEngine.state
                            if (gameState.value.isGameOver) {
                                failSound.playClick()
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                                } else {
                                    vibrator.vibrate(100)
                                }
                                navController.navigate("gameOver/${gameState.value.score}") {
                                    popUpTo("splash") { inclusive = true }
                                }
                                showLottie = false
                            } else {
                                showLottie = true
                                tapSound.playClick()
                            }
                        },
                        showLottie = showLottie
                    )
                }

            }
        }

    }
}
