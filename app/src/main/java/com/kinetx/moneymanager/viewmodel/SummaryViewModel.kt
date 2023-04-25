package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.dataclass.CategoryListData
import com.kinetx.moneymanager.dataclass.IncomeExpenseData
import com.kinetx.moneymanager.helpers.DateManipulation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import kotlin.math.round

class SummaryViewModel (application: Application): AndroidViewModel(application)
{

    //private val sp = PreferenceManager.getDefaultSharedPreferences(getApplication())
    private val startOfMonth = 25

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

    private val _incomeCalculated = MutableLiveData<String>()
    val incomeCalculated : LiveData<String>
        get() = _incomeCalculated

    private val _expenseCalculated = MutableLiveData<String>()
    val expenseCalculated : LiveData<String>
        get() = _expenseCalculated

    private val _balanceCalculated = MutableLiveData<String>()
    val balanceCalculated : LiveData<String>
        get() = _balanceCalculated

    private val _isIncomeBalanceVisible = MutableLiveData<Int>()
    val isIncomeBalanceVisible : LiveData<Int>
        get() = _isIncomeBalanceVisible


    private val _incomeExpenseQuery = MutableLiveData<IncomeExpenseData>()
    val incomeExpenseQuery :LiveData<IncomeExpenseData>
        get() = _incomeExpenseQuery

    private val _categorySummaryQuery = MutableLiveData<List<CategoryListData>>()
    val categorySummaryQuery : LiveData<List<CategoryListData>>
        get() = _categorySummaryQuery

    private val repository : DatabaseRepository

    private var myCalendar : Calendar = Calendar.getInstance()
    private var curCalendar: Calendar = Calendar.getInstance()

    init
    {
        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        _fragmentTitle.value = "Summary"
        myCalendar  = DateManipulation.resetToMidnight(myCalendar)
        curCalendar = DateManipulation.resetToMidnight(curCalendar)

        _selectedType.value = 2

        _incomeCalculated.value = "0"
        _expenseCalculated.value = "0"
        _balanceCalculated.value = "0"
        _isIncomeBalanceVisible.value = View.VISIBLE

        updateCustomDateView(myCalendar)

        updateTransactions(myCalendar,2)

    }

    private fun updateTransactions(myCalendar: Calendar, i: Int) {

     //   var dateEndCalendar :Calendar = GregorianCalendar(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH))
     //   var dateStartCalendar :Calendar = GregorianCalendar(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH))
        this.myCalendar = DateManipulation.resetToMidnight(myCalendar)

        viewModelScope.launch(Dispatchers.IO)
        {
            when (i)
            {
                0->
                {
                    _incomeExpenseQuery.postValue(repository.getIncomeExpenseSummary(myCalendar.timeInMillis,myCalendar.timeInMillis))
                    _categorySummaryQuery.postValue(repository.getCategorySummary(myCalendar.timeInMillis,myCalendar.timeInMillis))
                }
                1->
                {
                    val dateEndCalendar: Calendar =
                        DateManipulation.getEndOfWeek(myCalendar)
                    val dateStartCalendar: Calendar =
                        DateManipulation.getStartOfWeek(myCalendar)

                    _incomeExpenseQuery.postValue(repository.getIncomeExpenseSummary(dateStartCalendar.timeInMillis,dateEndCalendar.timeInMillis))
                    _categorySummaryQuery.postValue(repository.getCategorySummary(dateStartCalendar.timeInMillis,dateEndCalendar.timeInMillis))
                }
                2 ->
                {
                    val dateEndCalendar: Calendar =
                        DateManipulation.getEndOfMonth(myCalendar, startOfMonth)
                    val dateStartCalendar: Calendar =
                        DateManipulation.getStartOfMonth(myCalendar, startOfMonth)

                    _incomeExpenseQuery.postValue(repository.getIncomeExpenseSummary(dateStartCalendar.timeInMillis,dateEndCalendar.timeInMillis))
                    _categorySummaryQuery.postValue(repository.getCategorySummary(dateStartCalendar.timeInMillis,dateEndCalendar.timeInMillis))
                }
                3->
                {
                    val dateEndCalendar :Calendar = GregorianCalendar(myCalendar.get(Calendar.YEAR),11,31)
                    val dateStartCalendar :Calendar = GregorianCalendar(myCalendar.get(Calendar.YEAR),0,1)
                    _incomeExpenseQuery.postValue(repository.getIncomeExpenseSummary(dateStartCalendar.timeInMillis,dateEndCalendar.timeInMillis))
                    _categorySummaryQuery.postValue(repository.getCategorySummary(dateStartCalendar.timeInMillis,dateEndCalendar.timeInMillis))
                }
            }
        }

    }


    private fun updateCustomDateView(myCalendar: Calendar)
    {
        _selectedDay.value =    myCalendar.get(Calendar.DAY_OF_MONTH).toString()
        _selectedMonth.value = monthArray[myCalendar.get(Calendar.MONTH)]
        _selectedYear.value = myCalendar.get(Calendar.YEAR).toString()

        val tmp1 = DateManipulation.getStartOfWeek(myCalendar)
        _selectedWeekStart.value = tmp1.get(Calendar.DAY_OF_MONTH).toString()
        _selectedWeekStartMonth.value = monthArray[tmp1.get(Calendar.MONTH)]

        val tmp2 = DateManipulation.getEndOfWeek(myCalendar)
        _selectedWeekEnd.value = tmp2.get(Calendar.DAY_OF_MONTH).toString()
        _selectedWeekEndMonth.value = monthArray[tmp2.get(Calendar.MONTH)]
    }


    fun onRadioClick(radioGroup : RadioGroup, id : Int)
    {
        when(id)
        {
            R.id.summary_day_radio ->
            {
                _selectedType.value = 0
                _isIncomeBalanceVisible.value = View.GONE
                myCalendar = DateManipulation.copyDayMonthYear(myCalendar,curCalendar)
                updateCustomDateView(myCalendar)
                updateTransactions(myCalendar,0)
            }
            R.id.summary_week_radio ->
            {
                _selectedType.value = 1
                _isIncomeBalanceVisible.value = View.GONE
                myCalendar = DateManipulation.copyDayMonthYear(myCalendar,curCalendar)
                updateCustomDateView(myCalendar)
                updateTransactions(myCalendar,1)
            }
            R.id.summary_month_radio->
            {
                _selectedType.value = 2
                _isIncomeBalanceVisible.value = View.VISIBLE
                myCalendar = DateManipulation.copyDayMonthYear(myCalendar,curCalendar)
                updateCustomDateView(myCalendar)
                updateTransactions(myCalendar,2)
            }
            R.id.summary_year_radio->
            {
                _selectedType.value = 3
                _isIncomeBalanceVisible.value = View.VISIBLE
                myCalendar = DateManipulation.copyDayMonthYear(myCalendar,curCalendar)
                updateCustomDateView(myCalendar)
                updateTransactions(myCalendar,3)
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
                updateTransactions(myCalendar,0)
            }
            R.id.summary_week_radio->
            {
                myCalendar = DateManipulation.changeDayByN(myCalendar,7,direction)
                updateCustomDateView(myCalendar)
                updateTransactions(myCalendar,1)
            }
            R.id.summary_month_radio->
            {
                myCalendar = DateManipulation.changeMonthByN(myCalendar,1,direction)
                updateCustomDateView(myCalendar)
                updateTransactions(myCalendar,2)
            }
            R.id.summary_year_radio->
            {
                myCalendar = DateManipulation.changeYearByN(myCalendar,1,direction)
                updateCustomDateView(myCalendar)
                updateTransactions(myCalendar,3)
            }
        }


    }

    fun updateIncomeExpense(it: IncomeExpenseData?) {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN

        val a = it?.income!! - it.expense
        _incomeCalculated.value = df.format(it.income).toString()
        _expenseCalculated.value = df.format(it.expense).toString()
        _balanceCalculated.value = df.format(a).toString()

    }
}