import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier, progress: Float) {
    val animatedColor by animateColorAsState(
        targetValue = when {
            progress > 0.66f -> Color(0xFF81C784)
            progress > 0.33f -> Color(0xFFFFF176)
            else -> Color(0xFFE57373)
        },
        label = "ProgressColor"
    )

    Box(
        modifier = modifier.size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { progress },
            color = animatedColor,
            strokeWidth = 4.dp,
            trackColor = Color.Transparent
        )
    }
}
