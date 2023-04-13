package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.CategoryFragmentArgs
import com.kinetx.moneymanager.viewmodel.CategoryViewModel

class CategoryViewModelFactory (
    private val argList : CategoryFragmentArgs,
    private val application: Application ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java))
        {
            return CategoryViewModel(argList,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}