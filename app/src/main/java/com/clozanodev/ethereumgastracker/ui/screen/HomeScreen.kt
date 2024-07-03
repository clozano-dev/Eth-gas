package com.clozanodev.ethereumgastracker.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clozanodev.ethereumgastracker.BuildConfig
import com.clozanodev.ethereumgastracker.R
import com.clozanodev.ethereumgastracker.ui.components.GasPriceCard
import com.clozanodev.ethereumgastracker.viewmodel.GasViewModel

@Composable
fun HomeScreen(viewModel: GasViewModel) {
    val gasPrices by viewModel.gasPrices.observeAsState()
    val error by viewModel.error.observeAsState()

    val apiKey: String = BuildConfig.API_KEY

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.current_eth_price),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (gasPrices != null) {
            Row (
                modifier = Modifier.fillMaxWidth(),
            ){
                GasPriceCard(
                    tittle = "Low",
                    price = "${gasPrices!!.safeGasPrice} Gwei",
                    modifier = Modifier.weight(1f)
                )
                GasPriceCard(
                    tittle = "High",
                    price = "${gasPrices!!.fastGasPrice} Gwei",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            GasPriceCard(
                tittle = "Average",
                price = "${gasPrices!!.proposeGasPrice} Gwei",
            )
        } else {
            error?.let {
                Text(text = "Error: $it", color = MaterialTheme.colors.error)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchGasPrices(apiKey)
    }
}