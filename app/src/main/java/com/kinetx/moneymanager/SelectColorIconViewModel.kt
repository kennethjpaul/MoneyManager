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
        tmp.add(SelectCategoryItem(1, R.drawable.shopping, R.color.black, ""))
        tmp.add(SelectCategoryItem(2, R.drawable.moneytransfer, R.color.black, ""))
        tmp.add(SelectCategoryItem(3, R.drawable.guitar, R.color.black, ""))
        tmp.add(SelectCategoryItem(4, R.drawable.wallet, R.color.black, ""))
        tmp.add(SelectCategoryItem(5, R.drawable.soccer, R.color.black, ""))
        tmp.add(SelectCategoryItem(6, R.drawable.restaurant, R.color.black, ""))
        tmp.add(SelectCategoryItem(7, R.drawable.trash, R.color.black, ""))
        tmp.add(SelectCategoryItem(8, R.drawable.cycle, R.color.black, ""))
        tmp.add(SelectCategoryItem(9, R.drawable.office, R.color.black, ""))
        tmp.add(SelectCategoryItem(10, R.drawable.tools1, R.color.black, ""))
        tmp.add(SelectCategoryItem(11, R.drawable.shopping2, R.color.black, ""))
        tmp.add(SelectCategoryItem(12, R.drawable.sales, R.color.black, ""))
        tmp.add(SelectCategoryItem(13, R.drawable.weight, R.color.black, ""))
        tmp.add(SelectCategoryItem(14, R.drawable.basket, R.color.black, ""))
        tmp.add(SelectCategoryItem(15, R.drawable.football, R.color.black, ""))
        tmp.add(SelectCategoryItem(16, R.drawable.snack, R.color.black, ""))
        tmp.add(SelectCategoryItem(17, R.drawable.tools2, R.color.black, ""))
        tmp.add(SelectCategoryItem(18, R.drawable.debitcard, R.color.black, ""))
        tmp.add(SelectCategoryItem(19, R.drawable.home, R.color.black, ""))
        tmp.add(SelectCategoryItem(20, R.drawable.locker, R.color.black, ""))
        tmp.add(SelectCategoryItem(21, R.drawable.house, R.color.black, ""))
        tmp.add(SelectCategoryItem(22, R.drawable.creditcard, R.color.black, ""))
        tmp.add(SelectCategoryItem(23, R.drawable.graduatehat, R.color.black, ""))
        tmp.add(SelectCategoryItem(24, R.drawable.run, R.color.black, ""))
        tmp.add(SelectCategoryItem(25, R.drawable.hospital, R.color.black, ""))
        tmp.add(SelectCategoryItem(26, R.drawable.insurance, R.color.black, ""))
        tmp.add(SelectCategoryItem(27, R.drawable.salary, R.color.black, ""))
        tmp.add(SelectCategoryItem(28, R.drawable.transportation, R.color.black, ""))
        tmp.add(SelectCategoryItem(29, R.drawable.gift, R.color.black, ""))
        tmp.add(SelectCategoryItem(30, R.drawable.train, R.color.black, ""))
        tmp.add(SelectCategoryItem(31, R.drawable.masks, R.color.black, ""))
        tmp.add(SelectCategoryItem(32, R.drawable.atm, R.color.black, ""))
        tmp.add(SelectCategoryItem(33, R.drawable.graduate, R.color.black, ""))
        tmp.add(SelectCategoryItem(34, R.drawable.bank, R.color.black, ""))
        tmp.add(SelectCategoryItem(35, R.drawable.bus, R.color.black, ""))
        tmp.add(SelectCategoryItem(36, R.drawable.heart, R.color.black, ""))
        tmp.add(SelectCategoryItem(37, R.drawable.help, R.color.black, ""))

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