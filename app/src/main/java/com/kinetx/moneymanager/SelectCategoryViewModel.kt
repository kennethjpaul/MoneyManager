package com.kinetx.moneymanager

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.collections.ArrayList

class SelectCategoryViewModel : ViewModel() {

    private val _itemList  = MutableLiveData<ArrayList<SelectCategoryItem>>()
    val itemList : LiveData<ArrayList<SelectCategoryItem>>
        get() = _itemList

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle


    init {
        _fragmentTitle.value =""
    }

    fun initializeLayout(argList: SelectCategoryFragmentArgs) {

        val tmp = ArrayList<SelectCategoryItem>()
        if (argList.categoryType=="category" && argList.transactionType=="expense")
        {
            tmp.add(SelectCategoryItem(1, R.drawable.home, java.lang.Long.decode("0xFFFF0000").toInt(), "House hold"))
            tmp.add(SelectCategoryItem(2, R.drawable.restaurant, java.lang.Long.decode("0xFFBB86FC").toInt(), "Eating out"))
            tmp.add(SelectCategoryItem(3, R.drawable.shopping, java.lang.Long.decode("0xFF018786").toInt(), "Groceries"))
            tmp.add(SelectCategoryItem(4, R.drawable.heart, java.lang.Long.decode("0xFFFF0000").toInt(), "Health"))
            tmp.add(SelectCategoryItem(5, R.drawable.weight, java.lang.Long.decode("0xFFa4c639").toInt(), "Sports"))
            _fragmentTitle.value = "Select category"
            _itemList.value = tmp
        }

        if (argList.categoryType=="category" && argList.transactionType=="income")
        {

            tmp.add(SelectCategoryItem(1, R.drawable.salary,java.lang.Long.decode("0xFF007fff0").toInt(), "Salary"))
            tmp.add(SelectCategoryItem(2, R.drawable.debitcard, java.lang.Long.decode("0xFFBB86FC").toInt(), "Interest"))
            tmp.add(SelectCategoryItem(3, R.drawable.moneytransfer, java.lang.Long.decode("0xFFFF0000").toInt(), "Borrowed"))
            _fragmentTitle.value = "Select category"
            _itemList.value = tmp

        }

        if (argList.categoryType=="account" || argList.transactionType=="transfer")
        {
            tmp.add(SelectCategoryItem(1, R.drawable.bank, java.lang.Long.decode("0xFF018786").toInt(), "Main Account"))
            tmp.add(SelectCategoryItem(2, R.drawable.wallet, java.lang.Long.decode("0xFFFF0000").toInt(), "Wallet"))
            tmp.add(SelectCategoryItem(3, R.drawable.creditcard, java.lang.Long.decode("0xFFa4c639").toInt(), "Credit card"))
            _fragmentTitle.value = "Select account"
            _itemList.value = tmp

        }

    }



}
