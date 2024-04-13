package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.viewmodel.MonthlyOverviewVM

class MonthlyOverviewVMF(val application: Application): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MonthlyOverviewVM::class.java))
        {
            return MonthlyOverviewVM(application) as T
        }
        throw IllegalArgumentException("MonthlyOverviewVMF: Unknown viewmodel")
    }
}