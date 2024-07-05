package com.clozanodev.ethereumgastracker.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.clozanodev.ethereumgastracker.data.repository.GasRepository

class GasViewModelFactory(
    private val application: Application,
    private val repository: GasRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GasViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}