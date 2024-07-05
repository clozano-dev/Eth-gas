package com.clozanodev.ethereumgastracker.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.clozanodev.ethereumgastracker.R
import com.clozanodev.ethereumgastracker.data.model.GasPriceResult
import com.clozanodev.ethereumgastracker.data.repository.GasRepository
import com.clozanodev.ethereumgastracker.util.SharedPreferencesManager
import kotlinx.coroutines.launch

class GasViewModel(application: Application, private val repository: GasRepository) : AndroidViewModel(application) {
    private val _gasPrices = MutableLiveData<GasPriceResult>()
    val gasPrices: LiveData<GasPriceResult> = _gasPrices

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _lowerLimit = MutableLiveData<Int>()
    val lowerLimit: LiveData<Int> = _lowerLimit

    private val _upperLimit = MutableLiveData<Int>()
    val upperLimit: LiveData<Int> = _upperLimit

    init {
        _lowerLimit.value = SharedPreferencesManager.getLowerLimit(application)
        _upperLimit.value = SharedPreferencesManager.getUpperLimit(application)
    }

    fun fetchGasPrices(apiKey: String) {
        viewModelScope.launch {
            try {
                val result = repository.getGasPrice(apiKey)
                _gasPrices.value = result

                val lowerLimitValue = _lowerLimit.value ?: 0
                val upperLimitValue = _upperLimit.value ?: 0
                val proposeGasPrice = result.proposeGasPrice.toIntOrNull()

                if (proposeGasPrice != null) {
                    if (proposeGasPrice < lowerLimitValue && lowerLimitValue != 0) {
                        sendNotification("Gas price dropped below $lowerLimitValue Gwei")
                        clearLowerLimit()
                    } else if (proposeGasPrice > upperLimitValue && upperLimitValue != 0) {
                        sendNotification("Gas price exceeded $upperLimitValue Gwei")
                        clearUpperLimit()
                    }
                }

            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(message: String) {
        val builder = NotificationCompat.Builder(getApplication(), "GAS_PRICE_ALERTS")
            .setSmallIcon(R.drawable.ethereum)
            .setContentTitle("Gas Price Alert")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(getApplication())) {
            notify(1, builder.build())
        }
    }

    fun saveUpperLimit(upperLimit: Int) {
        SharedPreferencesManager.saveUpperLimit(getApplication(), upperLimit)
        _upperLimit.value = upperLimit
    }

    fun saveLowerLimit(lowerLimit: Int) {
        SharedPreferencesManager.saveLowerLimit(getApplication(), lowerLimit)
        _lowerLimit.value = lowerLimit
    }

    private fun clearLowerLimit() {
        SharedPreferencesManager.clearLowerLimit(getApplication())
        _lowerLimit.value = 0

    }

    private fun clearUpperLimit() {
        SharedPreferencesManager.clearUpperLimit(getApplication())
        _upperLimit.value = 0
    }
}