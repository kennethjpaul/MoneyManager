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
            tmp.add(SelectCategoryItem(1, R.drawable.cash, java.lang.Long.decode("0xFFFF0000").toInt(), "House hold"))
            tmp.add(SelectCategoryItem(2, R.drawable.atm_256, java.lang.Long.decode("0xFFBB86FC").toInt(), "Eating out"))
            tmp.add(SelectCategoryItem(3, R.drawable.bank, java.lang.Long.decode("0xFF018786").toInt(), "Groceries"))
            tmp.add(SelectCategoryItem(4, R.drawable.calculator, java.lang.Long.decode("0xFFFF0000").toInt(), "Health"))
            tmp.add(SelectCategoryItem(5, R.drawable.credit_card, java.lang.Long.decode("0xFFa4c639").toInt(), "Sports"))
            _fragmentTitle.value = "Select category"
            _itemList.value = tmp
        }

        if (argList.categoryType=="category" && argList.transactionType=="income")
        {

            tmp.add(SelectCategoryItem(1, R.drawable.customer,java.lang.Long.decode("0xFF007fff0").toInt(), "Salary"))
            tmp.add(SelectCategoryItem(2, R.drawable.dinar, java.lang.Long.decode("0xFF20B2AA").toInt(), "Interest"))
            tmp.add(SelectCategoryItem(3, R.drawable.donate, java.lang.Long.decode("0xFF48D1CC").toInt(), "Borrowed"))
            tmp.add(SelectCategoryItem(4, R.drawable.jewel, java.lang.Long.decode("0xFF6B8E23").toInt(), "Cash"))
            tmp.add(SelectCategoryItem(5, R.drawable.locker, java.lang.Long.decode("0xFFFF0000").toInt(), "Random"))
            tmp.add(SelectCategoryItem(6, R.drawable.moeydeposit, java.lang.Long.decode("0xFFBB86FC").toInt(), "Profit"))
            _fragmentTitle.value = "Select category"
            _itemList.value = tmp

        }

        if (argList.categoryType=="account" || argList.transactionType=="transfer")
        {
            tmp.add(SelectCategoryItem(1, R.drawable.moneybag, java.lang.Long.decode("0xFF018786").toInt(), "Main Account"))
            tmp.add(SelectCategoryItem(2, R.drawable.moneytransfer, java.lang.Long.decode("0xFFFF0000").toInt(), "Wallet"))
            tmp.add(SelectCategoryItem(3, R.drawable.sales, java.lang.Long.decode("0xFFa4c639").toInt(), "Credit card"))
            _fragmentTitle.value = "Select account"
            _itemList.value = tmp

        }

    }



}
