package com.clozanodev.ethereumgastracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
    private val viewModel: GasViewModel by viewModels {
        GasViewModelFactory(
            application, repository
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications disabled. Please enable them in settings.", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            EthereumGasTrackerTheme {
                MainScreen(viewModel)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        //createNotificationChannel()

        periodicWorkRequest()
    }

    /*    private fun createNotificationChannel() {
            val name = "Gas Price Alerts"
            val descriptionText = "Notifications for gas price alerts"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("GAS_PRICE_ALERTS", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }*/

    private fun periodicWorkRequest() {
        val workRequest =
            PeriodicWorkRequestBuilder<GasPriceWorker>(15, TimeUnit.MINUTES).setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "GasPriceWorker", ExistingPeriodicWorkPolicy.KEEP, workRequest
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