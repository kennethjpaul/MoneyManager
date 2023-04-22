package com.kinetx.moneymanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SettingsViewModel (application: Application) : AndroidViewModel(application)
{
    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle


    private val _startDaySpinnerEntries = MutableLiveData<List<String>>()
    val startDaySpinnerEntries : LiveData<List<String>>
        get() = _startDaySpinnerEntries

    val startDaySpinnerSelectedPosition = MutableLiveData<Int>()

    init
    {

        _fragmentTitle.value = "Settings"
        _startDaySpinnerEntries.value = listOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31")
    }
}