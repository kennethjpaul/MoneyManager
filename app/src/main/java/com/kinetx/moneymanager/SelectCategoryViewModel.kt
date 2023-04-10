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
        if (argList.actionType=="category" && argList.transactionType=="expense")
        {
            tmp.add(SelectCategoryItem(1, R.drawable.cash, R.color.black, "House hold"))
            tmp.add(SelectCategoryItem(2, R.drawable.atm_256, R.color.teal_700, "Eating out"))
            tmp.add(SelectCategoryItem(3, R.drawable.bank, R.color.ao, "Groceries"))
            tmp.add(SelectCategoryItem(4, R.drawable.calculator, R.color.almond, "Health"))
            tmp.add(SelectCategoryItem(5, R.drawable.credit_card, R.color.air_force_blue, "Sports"))
            _fragmentTitle.value = "Select category"
            _itemList.value = tmp
        }

        if (argList.actionType=="category" && argList.transactionType=="income")
        {

            tmp.add(SelectCategoryItem(1, R.drawable.customer, R.color.android_green, "Salary"))
            tmp.add(SelectCategoryItem(2, R.drawable.dinar, R.color.army_green, "Interest"))
            tmp.add(SelectCategoryItem(3, R.drawable.donate, R.color.auburn, "Borrowed"))
            tmp.add(SelectCategoryItem(4, R.drawable.jewel, R.color.azure, "Cash"))
            tmp.add(SelectCategoryItem(5, R.drawable.locker, R.color.purple_200, "Random"))
            tmp.add(SelectCategoryItem(6, R.drawable.moeydeposit, R.color.black, "Profit"))
            _fragmentTitle.value = "Select category"
            _itemList.value = tmp

        }

        if (argList.actionType=="account" || argList.transactionType=="transfer")
        {
            tmp.add(SelectCategoryItem(1, R.drawable.moneybag, R.color.android_green, "Main Account"))
            tmp.add(SelectCategoryItem(2, R.drawable.moneytransfer, R.color.army_green, "Wallet"))
            tmp.add(SelectCategoryItem(3, R.drawable.sales, R.color.auburn, "Credit card"))
            _fragmentTitle.value = "Select account"
            _itemList.value = tmp

        }

    }



}
