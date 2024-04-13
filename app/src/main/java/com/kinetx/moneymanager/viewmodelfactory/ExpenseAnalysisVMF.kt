package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.viewmodel.ExpenseAnalysisVM

class ExpenseAnalysisVMF(val application: Application): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseAnalysisVM::class.java))
        {
            return ExpenseAnalysisVM(application) as T
        }
        throw IllegalArgumentException("ExpenseAnalysisVMF: Unknown ViewModel")
    }
}