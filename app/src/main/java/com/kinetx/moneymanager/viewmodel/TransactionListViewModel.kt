package com.kinetx.moneymanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.database.TransactionDatabase
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.fragment.TransactionListFragmentArgs

class TransactionListViewModel (argList: TransactionListFragmentArgs, application: Application) : AndroidViewModel(application)
{



    var readAllTransaction : LiveData<List<TransactionDatabase>>
    private val repository : DatabaseRepository

    init
    {
        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)


        readAllTransaction = repository.getTransactionsAllAccountsAllCategories(TransactionType.INCOME,0,1781762771342)

    }

}