package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.SelectColorIconFragmentArgs
import com.kinetx.moneymanager.SelectColorIconViewModel

class SelectIconColorViewModelFactory(
    private val argList : SelectColorIconFragmentArgs,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectColorIconViewModel::class.java))
        {
            return SelectColorIconViewModel(argList,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
