package com.example.colorgame.android.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


@Composable
fun ScoreText(
    score: Int,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontSize = 14.sp, color = Color.Green),
    animationDuration: Int = 300
) {
    var previousScore by remember { mutableStateOf(score) }
    val scale = remember { Animatable(1f) }
    val animatedScore by animateIntAsState(
        targetValue = score,
        animationSpec = tween(durationMillis = animationDuration),
        label = "ScoreIntAnim"
    )

    LaunchedEffect(score) {
        if (score != previousScore) {
            scale.snapTo(1.3f)
            scale.animateTo(1f, animationSpec = tween(animationDuration))
            previousScore = score
        }
    }

    Text(
        text = "Score: $animatedScore",
        modifier = modifier.graphicsLayer(scaleX = scale.value, scaleY = scale.value),
        style = textStyle
    )
}
