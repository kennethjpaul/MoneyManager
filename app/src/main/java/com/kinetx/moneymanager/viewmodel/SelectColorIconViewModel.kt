package com.kinetx.moneymanager.viewmodel
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.dataclass.IconDataClass
import com.kinetx.moneymanager.fragment.SelectColorIconFragmentArgs


class SelectColorIconViewModel(argList : SelectColorIconFragmentArgs, application: Application): ViewModel() {

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val _itemList  = MutableLiveData<ArrayList<IconDataClass>>()
    val itemList : LiveData<ArrayList<IconDataClass>>
        get() = _itemList

    init {
        _fragmentTitle.value = ""
        _fragmentTitle.value = "Select ${argList.colorIconType}"

        when(argList.colorIconType)
        {
            "icon" -> initializeIconData()
            "color"-> initializeColorData()
        }
    }


    private fun initializeIconData() {
        Log.i("ColorIcon","Icon")
        val tmp = ArrayList<IconDataClass>()
        tmp.add(IconDataClass(R.drawable.shopping,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.moneytransfer,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.guitar,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.wallet,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.soccer,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.restaurant,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.trash,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.cycle,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.office,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.tools1,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.shopping2,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.sales,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.weight,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.payment,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.basket,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.football,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.deposit,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.transfer,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.snack,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.tools2,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.debitcard,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.home,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.locker,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.house,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.creditcard,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.appicon,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.graduatehat,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.run,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.hospital,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.insurance,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.salary,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.transportation,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.gift,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.train,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.masks,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.atm,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.graduate,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.bank,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.bus,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.heart,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.help,java.lang.Long.decode("0xFF000000").toInt()))
        _itemList.value = tmp
    }

    private fun initializeColorData() {
        Log.i("ColorIcon","Color")
        val tmp = ArrayList<IconDataClass>()
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF6B8E23").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFFa4c639").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF003b49").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF005252").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF1b8a6b").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF018786").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF1b365d").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF1d4289").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF2244a5").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF5d3754").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF3f125f").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF7b151e").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFFd3273e").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFFFF0000").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFFdc582a").toInt()))
        tmp.add(IconDataClass(0,java.lang.Long.decode("0xFF000000").toInt()))

        _itemList.value = tmp
    }

}