package com.kinetx.moneymanager.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.dataclass.TransactionListClass
import com.kinetx.moneymanager.fragment.TransactionListFragmentArgs
import kotlinx.coroutines.*
import java.math.RoundingMode
import java.text.DecimalFormat

class TransactionListViewModel (argList: TransactionListFragmentArgs, application: Application) : AndroidViewModel(application)
{

    private val _listTransformed = MutableLiveData<List<TransactionListClass>>()
    val listTransformed : LiveData<List<TransactionListClass>>
        get() = _listTransformed


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

        updateTransactions(argList)



        _fragmentTitle.value = "Transactions"

    }

    private fun updateTransactions(argList: TransactionListFragmentArgs) {
        viewModelScope.launch(Dispatchers.IO)
        {
            if (argList.accountId==-1L && argList.categoryId==-1L)
            {
                _listTransformed.postValue(repository.getTransactionsAllAccountsAllCategories(argList.transactionType,argList.dateStart,argList.dateEnd))

            }
            else if (argList.accountId==-1L)
            {
                _listTransformed.postValue(repository.getTransactionsAllAccountWithCategory(argList.transactionType,argList.categoryId,argList.dateStart,argList.dateEnd))
            }
            else if (argList.categoryId==-1L)
            {
                _listTransformed.postValue(repository.getTransactionsWithAccountAllCategory(argList.transactionType,argList.accountId,argList.dateStart,argList.dateEnd))
            }
            else
            {
                _listTransformed.postValue(repository.getTransactionsWithAccountWithCategory(argList.transactionType,argList.accountId,argList.categoryId,argList.dateStart,argList.dateEnd))
            }

        }
    }

    fun updateTotal(it: List<TransactionListClass>?) {
            if (it!=null)
            {

                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.DOWN
                _totalTransactionAmount.value = df.format(it.map { it.amount }.sum())
            }
    }


}