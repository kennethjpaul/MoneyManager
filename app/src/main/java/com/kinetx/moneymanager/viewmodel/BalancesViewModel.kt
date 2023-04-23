package com.kinetx.moneymanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.dataclass.CategoryListData

class BalancesViewModel (application: Application): AndroidViewModel(application)
{

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle


    var list : LiveData<List<CategoryListData>>

    private val repository : DatabaseRepository


    init
    {
        _fragmentTitle.value = "Current Balance"
        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)
        list = repository.testingQuery()

    }

}