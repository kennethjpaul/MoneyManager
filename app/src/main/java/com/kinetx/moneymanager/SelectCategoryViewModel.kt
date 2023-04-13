package com.kinetx.moneymanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseDao
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
            tmp.add(CategoryDatabase(1,"House","expense",R.drawable.home,java.lang.Long.decode("0xFFFF0000").toInt()))
            tmp.add(CategoryDatabase(2,"Restaurants","expense",R.drawable.restaurant,java.lang.Long.decode("0xFFBB86FC").toInt()))
            tmp.add(CategoryDatabase(3,"Groceries","expense",R.drawable.shopping,java.lang.Long.decode("0xFF018786").toInt()))
            tmp.add(CategoryDatabase(4,"Health","expense",R.drawable.heart,java.lang.Long.decode("0xFFFF0000").toInt()))
            tmp.add(CategoryDatabase(5,"Sports","expense",R.drawable.weight,java.lang.Long.decode("0xFFa4c639").toInt()))
            _fragmentTitle.value = "Select category"
            _itemList.value = tmp
        }

        if (argList.categoryType=="category" && argList.transactionType=="income")
        {

            tmp.add(CategoryDatabase(1,"Salary","income",R.drawable.salary,java.lang.Long.decode("0xFF007fff0").toInt()))
            tmp.add(CategoryDatabase(2,"Interest","income",R.drawable.debitcard,java.lang.Long.decode("0xFFBB86FC").toInt()))
            tmp.add(CategoryDatabase(3,"Borrowed","income",R.drawable.moneytransfer,java.lang.Long.decode("0xFFFF0000").toInt()))
            _fragmentTitle.value = "Select category"
            _itemList.value = tmp

        }

        if (argList.categoryType=="account" || argList.transactionType=="transfer")
        {
            tmp.add(CategoryDatabase(1,"Main","account",R.drawable.bank,java.lang.Long.decode("0xFF018786").toInt()))
            tmp.add(CategoryDatabase(2,"Wallet","account",R.drawable.wallet,java.lang.Long.decode("0xFFFF0000").toInt()))
            tmp.add(CategoryDatabase(3,"Credit card","account",R.drawable.creditcard,java.lang.Long.decode("0xFFa4c639").toInt()))
            _fragmentTitle.value = "Select account"
            _itemList.value = tmp

        }

    }


}
