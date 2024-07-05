package com.clozanodev.ethereumgastracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
            } catch (e: Exception) {
                _error.value = e.message
            }
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

    fun clearLowerLimit() {
        SharedPreferencesManager.clearLowerLimit(getApplication())
        _lowerLimit.value = 0

    }

    fun clearUpperLimit() {
        SharedPreferencesManager.clearUpperLimit(getApplication())
        _upperLimit.value = 0
    }
}