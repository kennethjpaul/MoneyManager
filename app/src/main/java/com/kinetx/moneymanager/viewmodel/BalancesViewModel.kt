package com.kinetx.moneymanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.dataclass.CategoryListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BalancesViewModel (application: Application): AndroidViewModel(application)
{

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle


    private val _list = MutableLiveData<List<CategoryListData>>()
    val list : LiveData<List<CategoryListData>>
        get() = _list

    private val repository : DatabaseRepository


    init
    {
        _fragmentTitle.value = "Accounts"
        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        updatePage()


    }

    fun updatePage() {
        viewModelScope.launch(Dispatchers.IO)
        {
            _list.postValue(repository.getAccountSummary())
        }

    }

}