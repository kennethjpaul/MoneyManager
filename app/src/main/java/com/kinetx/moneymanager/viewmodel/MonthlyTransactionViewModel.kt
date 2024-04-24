package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.icu.util.Calendar
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

class MonthlyTransactionViewModel(application: Application): AndroidViewModel(application) {


    private val sp = PreferenceManager.getDefaultSharedPreferences(getApplication())
    private val startOfMonth : Int = sp.getString("startDayOfMonth","1")!!.toInt()
    private var weekendEnabled : Boolean = sp.getBoolean("weekendSwitch",false);
    private var weekendShift : Int = sp.getString("weekendPref","0")?.toInt() ?: 0


    var myCalendarEnd: Calendar = Calendar.getInstance()
    var myCalendarStart : Calendar = Calendar.getInstance()

    private val _startDate = MutableLiveData<String>()
    val startDate : LiveData<String>
        get() = _startDate

    private val _endDate = MutableLiveData<String>()
    val endDate : LiveData<String>
        get() = _endDate

    var listRoomDatabase = MutableLiveData<List<TransactionListClass>>()

    private val repository : DatabaseRepository

    init {

        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        var myCalendar : Calendar = Calendar.getInstance()
        myCalendar = DateManipulation.resetToMidnight(myCalendar)

        myCalendarStart = DateManipulation.getStartOfMonth(myCalendar,startOfMonth, weekendEnabled,weekendShift)
        myCalendarEnd = myCalendar.clone() as Calendar

        _startDate.value = DateManipulation.getDateArray(myCalendarStart)
        _endDate.value = DateManipulation.getDateArray(myCalendar)

        updateTransactions()
    }

    fun updateTransactions() {
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

}