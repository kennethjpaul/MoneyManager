package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.viewmodel.TransactionsViewModel

class TransactionsViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(TransactionsViewModel::class.java))
        {
            return TransactionsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}