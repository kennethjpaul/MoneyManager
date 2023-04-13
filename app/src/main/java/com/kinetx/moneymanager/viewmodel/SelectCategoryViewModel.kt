package com.kinetx.moneymanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.fragment.SelectCategoryFragmentArgs
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseDao
import com.kinetx.moneymanager.enums.CategoryType
import kotlin.collections.ArrayList

class SelectCategoryViewModel (val database: DatabaseDao, application: Application, argList: SelectCategoryFragmentArgs) : AndroidViewModel(application) {

    private val _itemList  = MutableLiveData<List<CategoryDatabase>>()
    val itemList : LiveData<List<CategoryDatabase>>
        get() = _itemList

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle



    init {
        val tmp = ArrayList<CategoryDatabase>()
        if (argList.categoryType=="category" && argList.transactionType=="expense")
        {
            tmp.add(CategoryDatabase(1,"House",CategoryType.EXPENSE,
                R.drawable.home,java.lang.Long.decode("0xFFFF0000").toInt()))
            tmp.add(CategoryDatabase(2,"Restaurants",CategoryType.EXPENSE,
                R.drawable.restaurant,java.lang.Long.decode("0xFFBB86FC").toInt()))
            tmp.add(CategoryDatabase(3,"Groceries",CategoryType.EXPENSE,
                R.drawable.shopping,java.lang.Long.decode("0xFF018786").toInt()))
            tmp.add(CategoryDatabase(4,"Health",CategoryType.EXPENSE,
                R.drawable.heart,java.lang.Long.decode("0xFFFF0000").toInt()))
            tmp.add(CategoryDatabase(5,"Sports",CategoryType.EXPENSE,
                R.drawable.weight,java.lang.Long.decode("0xFFa4c639").toInt()))
            _fragmentTitle.value = "Select category"
            _itemList.value = tmp
        }

        if (argList.categoryType=="category" && argList.transactionType=="income")
        {

            tmp.add(CategoryDatabase(1,"Salary",CategoryType.INCOME,
                R.drawable.salary,java.lang.Long.decode("0xFF007fff0").toInt()))
            tmp.add(CategoryDatabase(2,"Interest",CategoryType.INCOME,
                R.drawable.debitcard,java.lang.Long.decode("0xFFBB86FC").toInt()))
            tmp.add(CategoryDatabase(3,"Borrowed",CategoryType.INCOME,
                R.drawable.moneytransfer,java.lang.Long.decode("0xFFFF0000").toInt()))
            _fragmentTitle.value = "Select category"
            _itemList.value = tmp

        }

        if (argList.categoryType=="account" || argList.transactionType=="transfer")
        {
            tmp.add(CategoryDatabase(1,"Main",CategoryType.ACCOUNT,
                R.drawable.bank,java.lang.Long.decode("0xFF018786").toInt()))
            tmp.add(CategoryDatabase(2,"Wallet",CategoryType.ACCOUNT,
                R.drawable.wallet,java.lang.Long.decode("0xFFFF0000").toInt()))
            tmp.add(CategoryDatabase(3,"Credit card",CategoryType.ACCOUNT,
                R.drawable.creditcard,java.lang.Long.decode("0xFFa4c639").toInt()))
            _fragmentTitle.value = "Select account"
            _itemList.value = tmp

        }

    }


}
