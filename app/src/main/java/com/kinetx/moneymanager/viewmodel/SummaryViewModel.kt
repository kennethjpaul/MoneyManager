package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.icu.util.Calendar
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.helpers.DateManipulation

class SummaryViewModel (application: Application): AndroidViewModel(application)
{


    private val monthArray = arrayOf(
        "Jan", "Feb",
        "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )


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

    private var myCalendar : Calendar = Calendar.getInstance()
    private var curCalendar: Calendar = Calendar.getInstance()

    init
    {
        _fragmentTitle.value = "Summary"
        myCalendar  = DateManipulation.resetToMidnight(myCalendar)
        curCalendar = DateManipulation.resetToMidnight(curCalendar)

        _selectedType.value = 2

        updateCustomDateView(myCalendar)


    }


    private fun updateCustomDateView(myCalendar: Calendar)
    {
        _selectedDay.value =    myCalendar.get(Calendar.DAY_OF_MONTH).toString()
        _selectedMonth.value = monthArray[myCalendar.get(Calendar.MONTH)]
        _selectedYear.value = myCalendar.get(Calendar.YEAR).toString()

        val tmp1 = DateManipulation.getStartOfWeek(myCalendar)
        _selectedWeekStart.value = tmp1.first.toString()
        _selectedWeekStartMonth.value = monthArray[tmp1.second]

        val tmp2 = DateManipulation.getEndOfWeek(myCalendar)
        _selectedWeekEnd.value = tmp2.first.toString()
        _selectedWeekEndMonth.value = monthArray[tmp2.second]
    }


    fun onRadioClick(radioGroup : RadioGroup, id : Int)
    {
        when(id)
        {
            R.id.summary_day_radio ->
            {
                _selectedType.value = 0
                myCalendar = DateManipulation.copyDayMonthYear(myCalendar,curCalendar)
                updateCustomDateView(myCalendar)
            }
            R.id.summary_week_radio ->
            {
                _selectedType.value = 1
                myCalendar = DateManipulation.copyDayMonthYear(myCalendar,curCalendar)
                updateCustomDateView(myCalendar)
            }
            R.id.summary_month_radio->
            {
                _selectedType.value = 2
                myCalendar = DateManipulation.copyDayMonthYear(myCalendar,curCalendar)
                updateCustomDateView(myCalendar)
            }
            R.id.summary_year_radio->
            {
                _selectedType.value = 3
                myCalendar = DateManipulation.copyDayMonthYear(myCalendar,curCalendar)
                updateCustomDateView(myCalendar)
            }
        }
    }

    fun changeDate(direction: Int, scope: Int) {

        when(scope)
        {
            R.id.summary_day_radio->
            {

                myCalendar = DateManipulation.changeDayByN(myCalendar,1,direction)
                updateCustomDateView(myCalendar)
            }
            R.id.summary_week_radio->
            {
                myCalendar = DateManipulation.changeDayByN(myCalendar,7,direction)
                updateCustomDateView(myCalendar)
            }
            R.id.summary_month_radio->
            {
                myCalendar = DateManipulation.changeMonthByN(myCalendar,1,direction)
                updateCustomDateView(myCalendar)
            }
            R.id.summary_year_radio->
            {
                myCalendar = DateManipulation.changeYearByN(myCalendar,1,direction)
                updateCustomDateView(myCalendar)
            }
        }


    }
}