package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.viewmodel.SelectCategoryViewModel
import com.kinetx.moneymanager.database.DatabaseDao
import com.kinetx.moneymanager.fragment.SelectCategoryFragmentArgs

class SelectCategoryViewModelFactory (
    private val database: DatabaseDao,
    private val application: Application,
    private val argList : SelectCategoryFragmentArgs

        ) : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {

        if (modelClass.isAssignableFrom(SelectCategoryViewModel::class.java))
        {
            return SelectCategoryViewModel(database, application, argList) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}