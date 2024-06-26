package com.clozanodev.ethereumgastracker.data.model

import com.google.gson.annotations.SerializedName

data class GasPriceResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: GasPriceResult
)
