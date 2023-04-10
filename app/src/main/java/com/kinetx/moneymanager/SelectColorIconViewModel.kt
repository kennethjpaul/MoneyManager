package com.kinetx.moneymanager
import android.R.drawable
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.reflect.Field


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
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF6B8E23").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFFa4c639").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF003b49").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF005252").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF1b8a6b").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF018786").toInt(), ""))

        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF1b365d").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF1d4289").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF2244a5").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF5d3754").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF3f125f").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF7b151e").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFFd3273e").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFFFF0000").toInt(), ""))
        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFFdc582a").toInt(), ""))


        tmp.add(SelectCategoryItem(9, 0, java.lang.Long.decode("0xFF000000").toInt(), ""))






        _itemList.value = tmp
    }

}