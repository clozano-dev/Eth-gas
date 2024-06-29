package com.clozanodev.ethereumgastracker.data.repository

import com.clozanodev.ethereumgastracker.data.api.RetrofitInstance
import com.clozanodev.ethereumgastracker.data.model.GasPriceResult

class GasRepository {
    suspend fun getGasPrice(apiKey: String): GasPriceResult {
        val response = RetrofitInstance.api.getGasPrice(apiKey)
        if (response.status == "1" && response.message == "OK") {
            return response.result
        } else {
            throw Exception("Failed to fetch gas prices: ${response.message}")
        }
    }
}