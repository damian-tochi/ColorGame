package com.example.colorgame.android.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.colorgame.android.components.GameButton
import com.example.colorgame.android.components.MultiColorText


@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("color_game_prefs", Context.MODE_PRIVATE)
    val isMuted = remember { mutableStateOf(sharedPreferences.getBoolean("mute_sounds", false)) }
    val rainbowColor = listOf(
        Color.Red, Color(0xFFFFA500), Color.Yellow,
        Color.Green, Color.Cyan, Color.Blue, Color.Magenta
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1c1b2b))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.weight(1f))

        MultiColorText(
            text = "Settings",
            fontSize = 24.sp,
            letterColors = rainbowColor,
            defaultColor = Color.White,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        GameButton(
            onClick = {
                val newValue = !isMuted.value
                isMuted.value = newValue
                sharedPreferences.edit().putBoolean("mute_sounds", newValue).apply()
            }, buttonIcon = Icons.Filled.Build,
            color = ButtonDefaults.buttonColors(
                containerColor = Color.Magenta,
                contentColor = Color.White
            ), title = if (isMuted.value) "Unmute Sounds" else "Mute All Sounds"
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
