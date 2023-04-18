package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.fragment.TransactionListFragmentArgs
import com.kinetx.moneymanager.viewmodel.TransactionListViewModel

class TransactionListViewModelFactory(private val argList: TransactionListFragmentArgs, private val application: Application) : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TransactionListViewModel::class.java))
        {
            return TransactionListViewModel(argList,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}