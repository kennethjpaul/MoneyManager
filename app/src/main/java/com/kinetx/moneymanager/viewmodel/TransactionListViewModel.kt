package com.kinetx.moneymanager.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.database.TransactionDatabase
import com.kinetx.moneymanager.dataclass.TransactionListClass
import com.kinetx.moneymanager.fragment.TransactionListFragmentArgs
import kotlinx.coroutines.*

class TransactionListViewModel (argList: TransactionListFragmentArgs, application: Application) : AndroidViewModel(application)
{
    fun setTotal() {
        _totalTransactionAmount.value = listTransactionAmount.value?.sum().toString()
    }


    var listTransactionAmount : LiveData<List<Float>>
    var listTransformed : LiveData<List<TransactionListClass>>

    private val _totalTransactionAmount = MutableLiveData<String>()
    val totalTransactionAmount : LiveData<String>
        get() = _totalTransactionAmount

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val repository : DatabaseRepository

    init
    {
        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        if (argList.accountId==-1L && argList.categoryId==-1L)
        {
            listTransformed = repository.getTransactionsAllAccountsAllCategories(argList.transactionType,argList.dateStart,argList.dateEnd)

        }
        else if (argList.accountId==-1L)
        {
           listTransformed = repository.getTransactionsAllAccountWithCategory(argList.transactionType,argList.categoryId,argList.dateStart,argList.dateEnd)
        }
        else if (argList.categoryId==-1L)
        {
           listTransformed = repository.getTransactionsWithAccountAllCategory(argList.transactionType,argList.accountId,argList.dateStart,argList.dateEnd)
        }
        else
        {
           listTransformed = repository.getTransactionsWithAccountWithCategory(argList.transactionType,argList.accountId,argList.categoryId,argList.dateStart,argList.dateEnd)
        }


        listTransactionAmount = Transformations.map(listTransformed)
        {
                list ->
            list.map {
                it.amount
            }
        }

        _fragmentTitle.value = "Transactions"

    }


}