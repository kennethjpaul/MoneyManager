package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import android.widget.ViewSwitcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.viewmodel.BalancesViewModel

class BalancesViewModelFactory (private val application: Application) : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BalancesViewModel::class.java))
        {
            return BalancesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}