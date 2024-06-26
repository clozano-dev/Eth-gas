package com.clozanodev.ethereumgastracker.data.api

import com.clozanodev.ethereumgastracker.data.model.GasPriceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EtherscanApiService {
    @GET("api?module=gastracker&action=gasoracle")
    suspend fun getGasPrice(@Query("apikey") apiKey: String): GasPriceResponse

}