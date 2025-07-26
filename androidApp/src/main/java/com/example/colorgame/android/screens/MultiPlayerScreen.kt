package com.example.colorgame.android.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.colorgame.android.components.GameButton



@Composable
fun MultiPlayerScreen(navController: NavController) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1c1b2b))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.weight(1f))

        Text("In development!\nYou can clone and contribute if you like")

        Spacer(Modifier.weight(1f))

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
