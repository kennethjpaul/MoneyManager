package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.viewmodel.CategorySummaryVM

class CategorySummaryVMF(val application: Application):ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategorySummaryVM::class.java))
        {
            return CategorySummaryVM(application) as T
        }
        throw IllegalArgumentException("CategorySummaryVMF: Unknown viewmodel")
    }
}