package com.example.colorgame.android.util

import androidx.compose.ui.graphics.Color


fun backgroundColorForSpeedLevel(speedLevel: Int): Color {
    return when (speedLevel) {
        1 -> Color(0xFF1c1b2b)
        2 -> Color.DarkGray
        3 -> Color.Gray
        4 -> Color.Red
        else -> Color.White
    }
}