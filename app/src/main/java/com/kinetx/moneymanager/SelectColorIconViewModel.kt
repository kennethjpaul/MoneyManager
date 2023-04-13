package com.kinetx.moneymanager
import android.R.drawable
import android.app.Application
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kinetx.moneymanager.database.CategoryDatabase
import java.lang.reflect.Field


class SelectColorIconViewModel(argList : SelectColorIconFragmentArgs, application: Application): ViewModel() {

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val _itemList  = MutableLiveData<ArrayList<CategoryDatabase>>()
    val itemList : LiveData<ArrayList<CategoryDatabase>>
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
        val tmp = ArrayList<CategoryDatabase>()
        tmp.add(CategoryDatabase(1,"","",R.drawable.shopping,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(2,"","",R.drawable.moneytransfer,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(3,"","",R.drawable.guitar,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(4,"","",R.drawable.wallet,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(5,"","",R.drawable.soccer,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(6,"","",R.drawable.restaurant,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(7,"","",R.drawable.trash,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(8,"","",R.drawable.cycle,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(9,"","",R.drawable.office,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(10,"","",R.drawable.tools1,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(11,"","",R.drawable.shopping2,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(12,"","",R.drawable.sales,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(13,"","",R.drawable.weight,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(14,"","",R.drawable.payment,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(15,"","",R.drawable.basket,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(16,"","",R.drawable.football,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(17,"","",R.drawable.deposit,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(18,"","",R.drawable.transfer,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(19,"","",R.drawable.snack,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(20,"","",R.drawable.tools2,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(21,"","",R.drawable.debitcard,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(22,"","",R.drawable.home,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(23,"","",R.drawable.locker,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(24,"","",R.drawable.house,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(25,"","",R.drawable.creditcard,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(26,"","",R.drawable.appicon,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(27,"","",R.drawable.graduatehat,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(28,"","",R.drawable.run,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(29,"","",R.drawable.hospital,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(30,"","",R.drawable.insurance,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(31,"","",R.drawable.salary,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(32,"","",R.drawable.transportation,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(33,"","",R.drawable.gift,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(34,"","",R.drawable.train,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(35,"","",R.drawable.masks,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(36,"","",R.drawable.atm,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(37,"","",R.drawable.graduate,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(38,"","",R.drawable.bank,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(39,"","",R.drawable.bus,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(40,"","",R.drawable.heart,java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(CategoryDatabase(41,"","",R.drawable.help,java.lang.Long.decode("0xFF000000").toInt()))
        _itemList.value = tmp
    }

    private fun initializeColorData() {
        Log.i("ColorIcon","Color")
        val tmp = ArrayList<CategoryDatabase>()
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF6B8E23").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFFa4c639").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF003b49").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF005252").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF1b8a6b").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF018786").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF1b365d").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF1d4289").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF2244a5").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF5d3754").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF3f125f").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF7b151e").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFFd3273e").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFFFF0000").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFFdc582a").toInt()))
        tmp.add(CategoryDatabase(0,"","",0,java.lang.Long.decode("0xFF000000").toInt()))

        _itemList.value = tmp
    }

}