package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.database.TransactionDatabase
import com.kinetx.moneymanager.dataclass.TransactionChildList
import com.kinetx.moneymanager.dataclass.TransactionListClass
import com.kinetx.moneymanager.dataclass.TransactionParentList
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.fragment.AccountTransactionFragmentArgs
import com.kinetx.moneymanager.helpers.CommonOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountTransactionsVM(application: Application, argList: AccountTransactionFragmentArgs): AndroidViewModel(application) {

    var listRoomDatabase = MutableLiveData<List<TransactionParentList>>()

    private val repository : DatabaseRepository


    init {
        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        viewModelScope.launch(Dispatchers.IO)
        {
            val m = repository.getAllTransactionsWithAccount(argList.accountId,argList.dateStart,argList.dateEnd).sortedBy { it.transactionDate }
            val k = repository.getLatestBalanceWithAccount(argList.accountId)


            var data = m.groupBy { it.transactionDate }.map {
                TransactionParentList(it.key, it.value.map {
                    TransactionChildList(it.transactionId,it.transactionCategoryOne.toString(),it.transactionCategoryTwo.toString(),when(it.transactionType)
                    {
                        TransactionType.TRANSFER -> Color.parseColor("#FFa4c639")
                        TransactionType.INCOME -> Color.parseColor("#FF5d8aa8")
                        TransactionType.EXPENSE -> Color.parseColor("#FF970203")
                        else-> Color.parseColor("#FFa4c639")
                    },it.transactionComment,it.transactionAmount.toFloat(),
                        CommonOperations.getResourceInt(application,"bank"),0,it.transactionType)
                }, (it.value.filter { it.transactionType == TransactionType.EXPENSE }
                    .sumOf { it.transactionAmount } - it.value.filter { it.transactionType == TransactionType.INCOME }
                    .sumOf { it.transactionAmount }).toFloat() )
            }

            var cumSum = 0.0F
            for (item in data)
            {
                cumSum += item.amount
                val newValue = k.balance - cumSum
                item.amount = newValue.toFloat()
            }

            data = data.sortedByDescending { it.date }

            listRoomDatabase.postValue(data)

        }


    }
}