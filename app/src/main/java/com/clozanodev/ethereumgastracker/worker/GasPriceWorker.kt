package com.clozanodev.ethereumgastracker.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.clozanodev.ethereumgastracker.BuildConfig
import com.clozanodev.ethereumgastracker.data.repository.GasRepository
import retrofit2.HttpException

class GasPriceWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val apiKey: String = BuildConfig.API_KEY

    override suspend fun doWork(): Result {

        Log.d("GasPriceWorker1", "Worker started")

        return try {
            val gasRepository = GasRepository()
            gasRepository.getGasPrice(apiKey)
            Log.d("GasPriceWorker2", "Worker finished")
            Result.success()
        } catch (e: HttpException){
            Log.d("GasPriceWorker3", "Worker failed")
            Result.retry()
        }
    }
}