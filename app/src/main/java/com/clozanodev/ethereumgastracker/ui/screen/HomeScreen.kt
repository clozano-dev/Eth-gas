package com.clozanodev.ethereumgastracker.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clozanodev.ethereumgastracker.BuildConfig
import com.clozanodev.ethereumgastracker.R
import com.clozanodev.ethereumgastracker.viewmodel.GasViewModel

@Composable
fun HomeScreen(viewModel: GasViewModel){
    val gasPrices by viewModel.gasPrices.observeAsState()
    val error by viewModel.error.observeAsState()

    val apiKey: String = BuildConfig.API_KEY

    Column (modifier = Modifier.padding(24.dp)){
        Text(text = stringResource(R.string.gas_cost))
        gasPrices?.let {
            Text(text = "Safe Gas Price: ${it.safeGasPrice}")
            Text(text = "Propose Gas Price: ${it.proposeGasPrice}")
            Text(text = "Fast Gas Price: ${it.fastGasPrice}")
        } ?: run {
            error?.let {
                Text(text = "Error: $it")
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchGasPrices(apiKey)
    }
}