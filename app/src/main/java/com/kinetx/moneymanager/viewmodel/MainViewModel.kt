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
    private var currency : String = sp.getString("currency","CHF").toString()

    val exp = MutableLiveData<Float>()

    private val _selectedCurrency = MutableLiveData<String>()
    val selectedCurrency : LiveData<String>
        get() = _selectedCurrency

    private val _startDate = MutableLiveData<String>()
    val startDate : LiveData<String>
        get() = _startDate

    private val _endDate = MutableLiveData<String>()
    val endDate : LiveData<String>
        get() = _endDate

    private val _expenseMonth = MutableLiveData<Float>()
    val expenseMonth : LiveData<Float>
        get() = _expenseMonth

    private val _balanceMonth = MutableLiveData<Float>()
    val balanceMonth : LiveData<Float>
        get() = _balanceMonth

    private val _incomeMonth = MutableLiveData<Float>()
    val incomeMonth : LiveData<Float>
        get() = _incomeMonth

    private val _percentMonth = MutableLiveData<Float>()
    val percentMonth : LiveData<Float>
        get() = _percentMonth

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
        _percentMonth.value = 0.0f
        _incomeMonth.value  = 0.0f
        var myCalendar : Calendar = Calendar.getInstance()
        myCalendar = DateManipulation.resetToMidnight(myCalendar)
        _selectedCurrency.value = currency

        updateIncomeExpenseQuery(myCalendar)
        val s = DateManipulation.getStartOfMonth(myCalendar,startOfMonth)
        val e = DateManipulation.getEndOfMonth(myCalendar,startOfMonth)
        _startDate.value = DateManipulation.getDateArray(s)
        _endDate.value = DateManipulation.getDateArray(e)


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
        _incomeMonth.value = df.format(it.income).toFloat()
        _expenseMonth.value = df.format(it.expense).toFloat()
        _balanceMonth.value =  df.format(a).toFloat()
        val p = if (it.income ==0f) {
            0f
        } else {
            it.expense/ it.income *100
        }

        _percentMonth.value = df.format(p).toFloat()
    }

    fun updateCurrency() {
       currency  = sp.getString("currency","CHF").toString()
        _selectedCurrency.value = currency
    }

}