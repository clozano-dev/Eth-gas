package com.clozanodev.ethereumgastracker.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.clozanodev.ethereumgastracker.BuildConfig
import com.clozanodev.ethereumgastracker.data.repository.GasRepository
import com.clozanodev.ethereumgastracker.util.SharedPreferencesManager
import retrofit2.HttpException

class GasPriceWorker(
    appContext: Context, params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val apiKey: String = BuildConfig.API_KEY

    override suspend fun doWork(): Result {

        Log.d("GasPriceWorker1", "Worker started")

        return try {
            val gasRepository = GasRepository()
            val gasResponse = gasRepository.getGasPrice(apiKey)

            val lowerLimit = SharedPreferencesManager.getLowerLimit(applicationContext)
            val upperLimit = SharedPreferencesManager.getLowerLimit(applicationContext)
            val proposeGasPrice = gasResponse.proposeGasPrice.toIntOrNull()

            if (lowerLimit != 0 && proposeGasPrice!! < lowerLimit) {
                SharedPreferencesManager.clearLowerLimit(applicationContext)
            } else if (upperLimit != 0 && proposeGasPrice!! > upperLimit) {
                SharedPreferencesManager.clearUpperLimit(applicationContext)
            }

            Log.d("GasPriceWorker2", "Worker finished")
            Result.success()
        } catch (e: HttpException) {
            Log.d("GasPriceWorker3", "Worker failed")
            Result.retry()
        }
    }
}