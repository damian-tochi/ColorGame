package com.example.colorgame.android.components


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Composable
fun GameButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonIcon: ImageVector,
    color: ButtonColors,
    title: String
) {
    val scale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick =  {
        coroutineScope.launch {
            scale.animateTo(
                0.9f,
                animationSpec = tween(100)
            )
            scale.animateTo(
                1f,
                animationSpec = tween(100)
            )
            onClick()
        }
    },
        modifier = modifier
            .fillMaxWidth(0.6f)
            .height(52.dp)
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value
            ),
        shape = RoundedCornerShape(30.dp),
        colors = color,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Icon(
            imageVector = buttonIcon,
            contentDescription = "Reset Icon",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
