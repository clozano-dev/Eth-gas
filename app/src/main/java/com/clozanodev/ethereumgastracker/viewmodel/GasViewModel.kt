package com.clozanodev.ethereumgastracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clozanodev.ethereumgastracker.data.model.GasPriceResult
import com.clozanodev.ethereumgastracker.data.repository.GasRepository
import kotlinx.coroutines.launch

class GasViewModel(private val repository: GasRepository) : ViewModel() {
    private val _gasPrices = MutableLiveData<GasPriceResult>()
    val gasPrices: LiveData<GasPriceResult> = _gasPrices

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

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
}