package com.example.colorgame.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun SelectLevelDialog(
    onLevelSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val levels = listOf("Easy", "Medium", "Hard", "Multiplayer")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Level") },
        text = {
            Column {
                levels.forEach { level ->
                    when (level) {

                        "Easy" -> GameLevelButton(
                            onClick = {
                                onLevelSelected("game_two")
                            }, color = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF008000),
                                contentColor = Color.White
                            ), title = level
                        )

                        "Medium" -> GameLevelButton(
                            onClick = {
                                onLevelSelected("game")
                            }, color = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFA500),
                                contentColor = Color.White
                            ), title = level
                        )

                        "Hard" -> GameLevelButton(
                            onClick = {
                                onLevelSelected("game_three")
                            }, color = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEF5350),
                                contentColor = Color.White
                            ), title = level
                        )

                        "Multiplayer" -> Column {
                            Spacer(Modifier.height(15.dp))

                            Box(modifier = Modifier
                                .background(color = Color.DarkGray)
                                .height(1.dp)
                                .fillMaxWidth())

                            Spacer(Modifier.height(15.dp))

                            GameLevelButton(
                                onClick = {
                                    onLevelSelected("multiplayer")
                                }, color = ButtonDefaults.buttonColors(
                                    containerColor = Color.Magenta,
                                    contentColor = Color.White
                                ), title = level
                            )
                        }

                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {},
        containerColor = Color(0xFF1c1b2b),
        tonalElevation = 5.dp,
    )
}
