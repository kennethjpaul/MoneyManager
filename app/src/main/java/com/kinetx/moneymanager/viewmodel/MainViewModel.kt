package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.dataclass.IncomeExpenseData
import com.kinetx.moneymanager.helpers.DateManipulation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

class MainViewModel(application: Application) : AndroidViewModel(application) {



    private val sp = PreferenceManager.getDefaultSharedPreferences(getApplication())
    private val startOfMonth : Int = sp.getString("startDayOfMonth","1")!!.toInt()

    val exp = MutableLiveData<Float>()


    private val _expenseMonth = MutableLiveData<Float>()
    val expenseMonth : LiveData<Float>
        get() = _expenseMonth

    private val _balanceMonth = MutableLiveData<Float>()
    val balanceMonth : LiveData<Float>
        get() = _balanceMonth

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private var _incomeExpenseQuery = MutableLiveData<IncomeExpenseData>()
    val incomeExpenseQuery :LiveData<IncomeExpenseData>
        get() = _incomeExpenseQuery

    private val repository : DatabaseRepository

    init {

        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        _expenseMonth.value = 0.0f
        _balanceMonth.value = 0.0f

        var myCalendar : Calendar = Calendar.getInstance()
        myCalendar = DateManipulation.resetToMidnight(myCalendar)

        updateIncomeExpenseQuery(myCalendar)



        _fragmentTitle.value = "Money Manager"
    }

    fun updateIncomeExpenseQuery(myCalendar: Calendar) {
        viewModelScope.launch(Dispatchers.IO)
        {
            val s = DateManipulation.getStartOfMonth(myCalendar,startOfMonth)
            val e = DateManipulation.getEndOfMonth(myCalendar,startOfMonth)
            _incomeExpenseQuery.postValue(repository.getIncomeExpenseSummary(s.timeInMillis,e.timeInMillis))
        }
    }


    fun updateIncomeExpense(it: IncomeExpenseData?) {

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN

        val a = it?.income!! - it.expense
        _expenseMonth.value = it.expense
        _balanceMonth.value =  a

    }

}