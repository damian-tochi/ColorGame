package com.example.colorgame.android.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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


@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("color_game_prefs", Context.MODE_PRIVATE)
    val isMuted = remember { mutableStateOf(sharedPreferences.getBoolean("mute_sounds", false)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Settings", color = Color.White, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val newValue = !isMuted.value
            isMuted.value = newValue
            sharedPreferences.edit().putBoolean("mute_sounds", newValue).apply()
        }) {
            Text(if (isMuted.value) "Unmute Sounds" else "Mute All Sounds")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate("splash") {
                popUpTo("splash") { inclusive = true }
            }
        }) {
            Text("Back to Menu")
        }
    }
}
