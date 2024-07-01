package com.clozanodev.ethereumgastracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.clozanodev.ethereumgastracker.data.repository.GasRepository

class GasViewModelFactory(private val repository: GasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GasViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}