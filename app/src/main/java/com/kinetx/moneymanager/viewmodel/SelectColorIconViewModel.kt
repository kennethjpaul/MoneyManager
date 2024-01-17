package com.kinetx.moneymanager.viewmodel
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.dataclass.IconDataClass
import com.kinetx.moneymanager.enums.ColorIconType
import com.kinetx.moneymanager.fragment.SelectColorIconFragmentArgs


class SelectColorIconViewModel(argList : SelectColorIconFragmentArgs, application: Application): ViewModel() {

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val _itemList  = MutableLiveData<ArrayList<IconDataClass>>()
    val itemList : LiveData<ArrayList<IconDataClass>>
        get() = _itemList

    init {


        when(argList.colorIconType)
        {
            ColorIconType.ICON ->
            {
                _fragmentTitle.value = "Select Icon"
                initializeIconData()
            }
            ColorIconType.COLOR->
            {
                _fragmentTitle.value = "Select Color"
                initializeColorData()
            }
        }
    }


    private fun initializeIconData() {
        val tmp = ArrayList<IconDataClass>()
        tmp.add(IconDataClass(R.drawable.shopping,"shopping",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.moneytransfer,"moneytransfer",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.guitar,"guitar",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.wallet,"wallet",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.soccer,"soccer",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.restaurant,"restaurant",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.trash,"trash",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.cycle,"cycle",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.office,"office",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.tools1,"tools1",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.shopping2,"shopping2",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.sales,"sales",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.weight,"weight",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.payment,"payment",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.basket,"basket",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.football,"football",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.deposit,"deposit",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.transfer,"transfer",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.snack,"snack",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.tools2,"tools2",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.debitcard,"debitcard",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.home,"home",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.locker,"locker",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.house,"house",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.creditcard,"creditcard",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.appicon,"appicon",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.graduatehat,"graduatehat",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.run,"run",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.hospital,"hospital",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.insurance,"insurance",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.salary,"salary",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.transportation,"transportation",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.gift,"gift",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.train,"train",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.masks,"masks",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.atm,"atm",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.graduate,"graduate",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.bank,"bank",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.bus,"bus",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.heart,"heart",java.lang.Long.decode("0xFF000000").toInt()))
        tmp.add(IconDataClass(R.drawable.help,"help",java.lang.Long.decode("0xFF000000").toInt()))
        _itemList.value = tmp
    }

    private fun initializeColorData() {
        val tmp = ArrayList<IconDataClass>()
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF6B8E23").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFFa4c639").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF003b49").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF005252").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF1b8a6b").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF018786").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF1b365d").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF1d4289").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF2244a5").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF5d3754").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF3f125f").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF7b151e").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFFd3273e").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFFFF0000").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFFdc582a").toInt()))
        tmp.add(IconDataClass(0,"help",java.lang.Long.decode("0xFF000000").toInt()))

        _itemList.value = tmp
    }

}