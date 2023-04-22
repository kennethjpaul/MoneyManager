package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.widget.RadioGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kinetx.moneymanager.R

class SummaryViewModel (application: Application): AndroidViewModel(application)
{

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val _selectedDay = MutableLiveData<String>()
    val selectedDay : LiveData<String>
        get() = _selectedDay

    private val _selectedMonth = MutableLiveData<String>()
    val selectedMonth : LiveData<String>
        get() = _selectedMonth

    private val _selectedYear = MutableLiveData<String>()
    val selectedYear : LiveData<String>
        get() = _selectedYear

    private val _selectedWeekStart = MutableLiveData<String>()
    val selectedWeekStart : LiveData<String>
        get() = _selectedWeekStart

    private val _selectedWeekStartMonth = MutableLiveData<String>()
    val selectedWeekStartMonth : LiveData<String>
        get() = _selectedWeekStartMonth

    private val _selectedWeekEnd = MutableLiveData<String>()
    val selectedWeekEnd : LiveData<String>
        get() = _selectedWeekEnd

    private val _selectedWeekEndMonth = MutableLiveData<String>()
    val selectedWeekEndMonth : LiveData<String>
        get() = _selectedWeekEndMonth




    private val _selectedType = MutableLiveData<Int>()
    val selectedType : LiveData<Int>
        get() = _selectedType


    init
    {
        _fragmentTitle.value = "Summary"

        _selectedDay.value ="20"
        _selectedMonth.value ="Apr"
        _selectedYear.value = "2023"
        _selectedType.value = 2
        _selectedWeekStart.value = "17"
        _selectedWeekEnd.value = "23"
        _selectedWeekStartMonth.value = "Apr"
        _selectedWeekEndMonth.value = "Apr"
    }

    fun onRadioClick(radioGroup : RadioGroup, id : Int)
    {
        when(id)
        {
            R.id.summary_day_radio ->
            {
                _selectedType.value = 0
            }
            R.id.summary_week_radio ->
            {
                _selectedType.value = 1
            }
            R.id.summary_month_radio->
            {
                _selectedType.value = 2
            }
            R.id.summary_year_radio->
            {
                _selectedType.value = 3
            }
        }
    }
}