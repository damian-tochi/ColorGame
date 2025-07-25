package com.example.colorgame.android.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.launch


@Composable
fun BouncyClickBox(
    modifier: Modifier = Modifier,
    scaleFactor: Float = 0.9f,
    animationDuration: Int = 100,
    coloAnimationDuration: Int = 350,
    shape: Shape = RoundedCornerShape(12.dp),
    targetColor: Color,
    showLottie: Boolean = false,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val scale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = coloAnimationDuration),
        label = "ColorAnimation"
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("stars game square.json"))
    val lottieProgress = rememberLottieAnimatable()

    Box(
        modifier = modifier
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value
            )
            .background(animatedColor, shape)
            .clickable {
                coroutineScope.launch {
                    scale.animateTo(scaleFactor, animationSpec = tween(animationDuration))
                    scale.animateTo(1f, animationSpec = tween(animationDuration))
                    onClick()

                    if (showLottie && composition != null) {
                        lottieProgress.animate(
                            composition = composition,
                            clipSpec = LottieClipSpec.Progress(0f, 1f)
                        )
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        content()

        if (showLottie && composition != null) {
            LottieAnimation(
                composition = composition,
                progress = { lottieProgress.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .aspectRatio(1f)
            )
        }
    }
}
