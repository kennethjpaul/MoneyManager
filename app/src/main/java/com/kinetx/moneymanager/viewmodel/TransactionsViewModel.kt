package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.dataclass.TransactionListClass
import com.kinetx.moneymanager.helpers.DateManipulation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionsViewModel (application: Application) : AndroidViewModel(application) {

    var myCalendarEnd: Calendar = Calendar.getInstance()
    var myCalendarStart : Calendar = Calendar.getInstance()

    private val monthArray = arrayOf(
        "Jan", "Feb",
        "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    private val sp = PreferenceManager.getDefaultSharedPreferences(getApplication())
    private val startDayofMonth : Int = sp.getString("startDayOfMonth","1")!!.toInt()

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

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

    private val repository : DatabaseRepository

    var listRoomDatabase = MutableLiveData<List<TransactionListClass>>()

    init {

        _fragmentTitle.value = "Transactions"

        myCalendarEnd = DateManipulation.resetToMidnight(myCalendarEnd)
        myCalendarStart = DateManipulation.resetToMidnight(myCalendarStart)

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

        //listTransformed.postValue(repository.getAllTransactions(myCalendarStart.timeInMillis,myCalendarEnd.timeInMillis))
        updateTransactions()


    }

    private val dateEndPicker = DatePickerDialog.OnDateSetListener { _, year, month, dayofMonth ->
        myCalendarEnd.set(Calendar.YEAR, year)
        myCalendarEnd.set(Calendar.MONTH, month)
        myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayofMonth)
        _endDay.value = dayofMonth.toString()
        _endMonth.value = monthArray[month]
        _endYear.value = year.toString()
        updateTransactions()
    }



    private val dateStartPicker = DatePickerDialog.OnDateSetListener { _, year, month, dayofMonth ->
        myCalendarStart.set(Calendar.YEAR, year)
        myCalendarStart.set(Calendar.MONTH, month)
        myCalendarStart.set(Calendar.DAY_OF_MONTH, dayofMonth)
        _startDay.value = dayofMonth.toString()
        _startMonth.value = monthArray[month]
        _startYear.value = year.toString()
        updateTransactions()
    }


    private fun updateList()
    {

    }


    fun updateTransactions() {
        Log.i("III","Update")
        viewModelScope.launch(Dispatchers.IO)
        {
            listRoomDatabase.postValue(
                repository.getAllTransactions(
                    myCalendarStart.timeInMillis,
                    myCalendarEnd.timeInMillis
                )
            )
        }
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
}