package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.viewmodel.MonthlyBudgetViewModel

class MonthlyBudgetViewModelFactory(val application: Application): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MonthlyBudgetViewModel::class.java))
        {
            return MonthlyBudgetViewModel(application) as T
        }
        throw IllegalArgumentException("MonthlyBudgetViewModelFactory: Unknown ViewModel")
    }
}