package com.kinetx.moneymanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel () {

    private val _incomeMonth = MutableLiveData<Float>()
    val incomeMonth : LiveData<Float>
        get() = _incomeMonth


    private val _expenseMonth = MutableLiveData<Float>()
    val expenseMonth : LiveData<Float>
        get() = _expenseMonth

    private val _accountTotal = MutableLiveData<Float>()
    val accountTotal : LiveData<Float>
        get() = _accountTotal



    init {
        _incomeMonth.value = 0.00f
        _expenseMonth.value = 0.00f
        _accountTotal.value = 0.00f
    }

}