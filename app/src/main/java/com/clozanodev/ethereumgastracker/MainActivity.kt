package com.clozanodev.ethereumgastracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.clozanodev.ethereumgastracker.data.repository.GasRepository
import com.clozanodev.ethereumgastracker.ui.navigation.MainScreen
import com.clozanodev.ethereumgastracker.ui.theme.EthereumGasTrackerTheme
import com.clozanodev.ethereumgastracker.viewmodel.GasViewModel
import com.clozanodev.ethereumgastracker.viewmodel.GasViewModelFactory
import com.clozanodev.ethereumgastracker.worker.GasPriceWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val repository by lazy { GasRepository() }
    private val viewModel: GasViewModel by viewModels { GasViewModelFactory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EthereumGasTrackerTheme {
                MainScreen(viewModel)
            }
        }

        periodicWorkRequest()
    }

    private fun periodicWorkRequest() {
        val workRequest = PeriodicWorkRequestBuilder<GasPriceWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "GasPriceWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EthereumGasTrackerTheme {
        //Greeting("Android")
    }
}