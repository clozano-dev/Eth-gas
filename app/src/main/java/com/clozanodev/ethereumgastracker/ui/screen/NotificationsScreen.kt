package com.clozanodev.ethereumgastracker.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.clozanodev.ethereumgastracker.viewmodel.GasViewModel

@Composable
fun NotificationsScreen(viewModel: GasViewModel){
    Text(text = "Notifications")
}