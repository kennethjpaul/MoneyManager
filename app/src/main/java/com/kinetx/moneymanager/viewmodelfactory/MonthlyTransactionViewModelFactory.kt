package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.viewmodel.MonthlyTransactionViewModel

class MonthlyTransactionViewModelFactory(val application: Application):ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonthlyTransactionViewModel::class.java))
        {
            return MonthlyTransactionViewModel(application) as T
        }
        throw IllegalArgumentException("MonthlyTransactionViewModelFactory: Unknown ViewModel class")
    }
}