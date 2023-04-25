package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.view.View
import android.widget.RadioGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.helpers.DateManipulation

class PlotTransactionViewModel(application: Application) : AndroidViewModel(application) {

    var myCalendarEnd: Calendar = Calendar.getInstance()
    var myCalendarStart : Calendar  = Calendar.getInstance()

    private val monthArray = arrayOf(
        "Jan", "Feb",
        "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    private val sp = PreferenceManager.getDefaultSharedPreferences(getApplication())
    private val startDayofMonth : Int = sp.getString("startDayOfMonth","1")!!.toInt()


    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val _accountSpinnerEntries = MutableLiveData<List<String>>()
    val accountSpinnerEntries : LiveData<List<String>>
        get() = _accountSpinnerEntries

    val accountSpinnerSelectedPosition = MutableLiveData<Int>()

    private val _categorySpinnerIsVisible = MutableLiveData<Int>()
    val categorySpinnerIsVisible : LiveData<Int>
        get() = _categorySpinnerIsVisible

    private val _accountSpinnerIsVisible = MutableLiveData<Int>()
    val accountSpinnderIsVisible : LiveData<Int>
        get() = _accountSpinnerIsVisible

    val categorySpinnerSelectedPosition = MutableLiveData<Int>()

    private val _categorySpinnerEntries = MutableLiveData<List<String>>()
    val categorySpinnerEntries : LiveData<List<String>>
        get() = _categorySpinnerEntries



    private val _startDay = MutableLiveData<String>()
    val startDay : LiveData<String>
        get() = _startDay

    private val _startMonth = MutableLiveData<String>()
    val startMonth : LiveData<String>
        get() = _startMonth

    private val _startYear = MutableLiveData<String>()
    val startYear : LiveData<String>
        get() = _startYear


    private val _endDay = MutableLiveData<String>()
    val endDay : LiveData<String>
        get() = _endDay

    private val _endMonth = MutableLiveData<String>()
    val endMonth : LiveData<String>
        get() = _endMonth

    private val _endYear = MutableLiveData<String>()
    val endYear : LiveData<String>
        get() = _endYear



    var accountDbList : LiveData<List<CategoryDatabase>>
    var incomeDbList : LiveData<List<CategoryDatabase>>
    var expenseDbList : LiveData<List<CategoryDatabase>>
    private val repository : DatabaseRepository

    init {
        _fragmentTitle.value = "Select data to plot"
        _categorySpinnerIsVisible.value = View.GONE
        _accountSpinnerIsVisible.value = View.GONE

        myCalendarEnd = resetToMidnight(myCalendarEnd)
        myCalendarStart = resetToMidnight(myCalendarStart)



        _endDay.value =  myCalendarEnd.get(Calendar.DAY_OF_MONTH).toString()
        _endMonth.value = monthArray[myCalendarEnd.get(
            Calendar.MONTH)]
        _endYear.value = myCalendarEnd.get(Calendar.YEAR).toString()


        myCalendarStart = DateManipulation.getStartOfMonth(myCalendarEnd,startDayofMonth)

        _startDay.value = myCalendarStart.get(Calendar.DAY_OF_MONTH).toString()
        _startMonth.value = monthArray[myCalendarStart.get(Calendar.MONTH)]
        _startYear.value = myCalendarStart.get(Calendar.YEAR).toString()

        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        accountDbList = repository.readAllAccountCategory
        incomeDbList = repository.readAllIncomeCategory
        expenseDbList = repository.readAllExpenseCategory

        accountSpinnerSelectedPosition.value = 0
        categorySpinnerSelectedPosition.value = 0

    }

    private fun resetToMidnight(myCalendar: Calendar): Calendar {
        myCalendar.set(Calendar.HOUR_OF_DAY,0)
        myCalendar.set(Calendar.MINUTE,0)
        myCalendar.set(Calendar.SECOND,0)
        myCalendar.set(Calendar.MILLISECOND,0)

        return myCalendar
    }

    private val dateEndPicker = DatePickerDialog.OnDateSetListener { _, year, month, dayofMonth ->
        myCalendarEnd.set(Calendar.YEAR, year)
        myCalendarEnd.set(Calendar.MONTH, month)
        myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayofMonth)
        _endDay.value = dayofMonth.toString()
        _endMonth.value = monthArray[month]
        _endYear.value = year.toString()
    }

    private val dateStartPicker = DatePickerDialog.OnDateSetListener { _, year, month, dayofMonth ->
        myCalendarStart.set(Calendar.YEAR, year)
        myCalendarStart.set(Calendar.MONTH, month)
        myCalendarStart.set(Calendar.DAY_OF_MONTH, dayofMonth)
        _startDay.value = dayofMonth.toString()
        _startMonth.value = monthArray[month]
        _startYear.value = year.toString()
    }

    fun dateEndPick(it: View?) {
        if (it != null) {
            DatePickerDialog(
                it.context,
                dateEndPicker,
                myCalendarEnd.get(Calendar.YEAR),
                myCalendarEnd.get(Calendar.MONTH),
                myCalendarEnd.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    fun dateStartPick(it: View?) {
        if (it != null) {
            DatePickerDialog(
                it.context,
                dateStartPicker,
                myCalendarStart.get(Calendar.YEAR),
                myCalendarStart.get(Calendar.MONTH),
                myCalendarStart.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


    fun onRadioClick(radioGroup : RadioGroup, id : Int)
    {
        _accountSpinnerEntries.value = populateSpinner(accountDbList.value)
        _accountSpinnerIsVisible.value = View.VISIBLE
        when(id)
        {
            R.id.plot_category_income ->
            {
                _categorySpinnerEntries.value = populateSpinner(incomeDbList.value)
                _categorySpinnerIsVisible.value = View.VISIBLE

            }
            R.id.plot_category_expense ->
            {
                _categorySpinnerEntries.value = populateSpinner(expenseDbList.value)
                _categorySpinnerIsVisible.value = View.VISIBLE
            }
            R.id.plot_category_transfer ->
            {
                _categorySpinnerIsVisible.value = View.GONE
            }
        }
    }

    private fun populateSpinner (list : List<CategoryDatabase>?): List<String> {
        val l = mutableListOf("All")

        if (list != null) {
            for (i in list) {
                l.add(i.categoryName)
            }
        }

        return l
    }

    fun getAccountCategoryIds(transactionType: TransactionType) : Pair<Long, Long>
    {

        val accountId : Long = if (accountSpinnerSelectedPosition.value==0)
        {
            -1L
        }
        else
        {
            accountDbList.value?.get(accountSpinnerSelectedPosition.value!!-1)?.categoryId!!
        }

        val categoryId : Long = if(categorySpinnerSelectedPosition.value==0)
        {
            -1L
        }
        else
        {
            when(transactionType)
            {
                TransactionType.INCOME ->
                {
                    incomeDbList.value?.get(categorySpinnerSelectedPosition.value!!-1)?.categoryId!!
                }
                TransactionType.EXPENSE ->
                {
                    expenseDbList.value?.get(categorySpinnerSelectedPosition.value!!-1)?.categoryId!!
                }
                else ->
                {
                    -1L
                }
            }
        }


        return Pair(accountId,categoryId)

    }


}