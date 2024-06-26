package com.clozanodev.ethereumgastracker.data.model

import com.google.gson.annotations.SerializedName

data class GasPriceResult(
    @SerializedName("SafeGasPrice") val safeGasPrice: String,
    @SerializedName("ProposeGasPrice") val proposeGasPrice: String,
    @SerializedName("FastGasPrice") val fastGasPrice: String
)
