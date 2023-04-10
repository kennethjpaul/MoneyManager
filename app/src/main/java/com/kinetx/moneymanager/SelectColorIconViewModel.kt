package com.kinetx.moneymanager

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectColorIconViewModel: ViewModel() {

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val _itemList  = MutableLiveData<ArrayList<SelectCategoryItem>>()
    val itemList : LiveData<ArrayList<SelectCategoryItem>>
        get() = _itemList

    init {
        _fragmentTitle.value = ""
    }

    fun initializeLayout(argList: SelectColorIconFragmentArgs) {
        _fragmentTitle.value = "Select ${argList.colorIconType}"

        when(argList.colorIconType)
        {
            "icon" -> initializeIconData()
            "color"-> initializeColorData()
        }
    }

    private fun initializeIconData() {
        Log.i("ColorIcon","Icon")
        val tmp = ArrayList<SelectCategoryItem>()

        tmp.add(SelectCategoryItem(1, R.drawable.cash, R.color.black, ""))
        tmp.add(SelectCategoryItem(2, R.drawable.atm_256, R.color.black, ""))
        tmp.add(SelectCategoryItem(3, R.drawable.bank, R.color.black, ""))
        tmp.add(SelectCategoryItem(4, R.drawable.calculator, R.color.black, ""))
        tmp.add(SelectCategoryItem(5, R.drawable.credit_card, R.color.black, ""))
        tmp.add(SelectCategoryItem(1, R.drawable.customer, R.color.black, ""))
        tmp.add(SelectCategoryItem(2, R.drawable.dinar, R.color.black, ""))
        tmp.add(SelectCategoryItem(3, R.drawable.donate, R.color.black, ""))
        tmp.add(SelectCategoryItem(4, R.drawable.jewel, R.color.black, ""))
        tmp.add(SelectCategoryItem(5, R.drawable.locker, R.color.black, ""))
        tmp.add(SelectCategoryItem(6, R.drawable.moeydeposit, R.color.black, ""))
        tmp.add(SelectCategoryItem(1, R.drawable.moneybag, R.color.black, ""))
        tmp.add(SelectCategoryItem(2, R.drawable.moneytransfer, R.color.black, ""))
        tmp.add(SelectCategoryItem(3, R.drawable.sales, R.color.black, ""))
        _itemList.value = tmp
    }

    private fun initializeColorData() {
        Log.i("ColorIcon","Color")
        val tmp = ArrayList<SelectCategoryItem>()
        tmp.add(SelectCategoryItem(1, 0, R.color.black, ""))
        tmp.add(SelectCategoryItem(2, 0, R.color.teal_700, ""))
        tmp.add(SelectCategoryItem(3, 0, R.color.ao, ""))
        tmp.add(SelectCategoryItem(4, 0, R.color.almond, ""))
        tmp.add(SelectCategoryItem(5, 0, R.color.air_force_blue, ""))
        tmp.add(SelectCategoryItem(1, 0, R.color.android_green, ""))
        tmp.add(SelectCategoryItem(2, 0, R.color.army_green, ""))
        tmp.add(SelectCategoryItem(3, 0, R.color.auburn, ""))
        tmp.add(SelectCategoryItem(4, 0, R.color.azure, ""))
        tmp.add(SelectCategoryItem(5, 0, R.color.purple_200, ""))
        _itemList.value = tmp
    }

}