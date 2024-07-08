package com.clozanodev.ethereumgastracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.clozanodev.ethereumgastracker.ui.theme.CustomFont

@Composable
fun GasPriceCard(
    tittle: String,
    price: String,
    modifier: Modifier = Modifier) {
    Card(
        backgroundColor = Color(0xFFFFFFFF),
        contentColor = Color(0xFF454a74),
        border = BorderStroke(4.dp, Color(0xFF8992B1)),
        shape = CircleShape,
        modifier = Modifier
            .height(180.dp)
            .padding(8.dp).width(160.dp),
        elevation = 8.dp
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = tittle,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFont,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = price,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFont,
                style = MaterialTheme.typography.h3
            )
        }
    }
}

