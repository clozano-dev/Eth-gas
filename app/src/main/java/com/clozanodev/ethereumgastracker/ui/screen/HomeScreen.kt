package com.clozanodev.ethereumgastracker.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clozanodev.ethereumgastracker.BuildConfig
import com.clozanodev.ethereumgastracker.R
import com.clozanodev.ethereumgastracker.ui.components.GasPriceCard
import com.clozanodev.ethereumgastracker.ui.theme.CustomFont
import com.clozanodev.ethereumgastracker.viewmodel.GasViewModel

@Composable
fun HomeScreen(viewModel: GasViewModel) {
    val gasPrices by viewModel.gasPrices.observeAsState()
    val error by viewModel.error.observeAsState()

    val apiKey: String = BuildConfig.API_KEY

    Surface(
        color = Color(0xFFE0E2EE),
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            if (gasPrices != null) {

                Row {
                    GasPriceCard(
                        tittle = "Low",
                        price = "${gasPrices!!.safeGasPrice}",
                        modifier = Modifier.weight(1f)
                    )
                    GasPriceCard(
                        tittle = "High",
                        price = "${gasPrices!!.fastGasPrice}",
                        modifier = Modifier.weight(1f)
                    )
                }
                GasPriceCard(
                    tittle = "Average",
                    price = "${gasPrices!!.proposeGasPrice}",
                )

                Image(
                    painter = painterResource(id = R.drawable.ethereum_home),
                    contentDescription = "Ethereum Logo",
                    modifier = Modifier
                        .size(240.dp)
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = stringResource(R.string.ethereum_gas_price_in_gwei),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = CustomFont,
                    color = Color(0xFF464A74),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

            } else {
                error?.let {
                    Text(text = "Error: $it")
                }

            }
        }


    }
    LaunchedEffect(Unit) {
        viewModel.fetchGasPrices(apiKey)
    }
}