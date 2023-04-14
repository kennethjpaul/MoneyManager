package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.fragment.AddTransactionFragmentArgs
import com.kinetx.moneymanager.viewmodel.AddTransactionViewModel
import com.kinetx.moneymanager.viewmodel.SelectCategoryViewModel

class AddTransactionViewModelFactory (
    private val argList : AddTransactionFragmentArgs,
    private val application: Application
        ) : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {

        if (modelClass.isAssignableFrom(AddTransactionViewModel::class.java))
        {
            return AddTransactionViewModel(argList,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}