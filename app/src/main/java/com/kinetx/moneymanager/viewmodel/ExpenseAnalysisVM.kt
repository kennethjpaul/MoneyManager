package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.dataclass.TransactionListClass
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.helpers.DateManipulation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class ExpenseAnalysisVM(application: Application): AndroidViewModel(application) {

    private val sp = PreferenceManager.getDefaultSharedPreferences(getApplication())
    private val startOfMonth : Int = sp.getString("startDayOfMonth","1")!!.toInt()
    private var weekendEnabled : Boolean = sp.getBoolean("weekendSwitch",false);
    private var weekendShift : Int = sp.getString("weekendPref","0")?.toInt() ?: 0

    var myCalendarToday: Calendar = Calendar.getInstance()
    var myCalendarStart : Calendar = Calendar.getInstance()
    var myCalendarEnd : Calendar = Calendar.getInstance()

    private val _dateToday = MutableLiveData<String>()
    val dateToday : LiveData<String>
        get() = _dateToday

    private var categorySelected : CategoryDatabase = CategoryDatabase()

    val readAllExpenseCategory : LiveData<List<CategoryDatabase>>

    private val _expenseMonth = MutableLiveData<String>()
    val expenseMonth : LiveData<String>
        get() = _expenseMonth
    private val _expenseWeek = MutableLiveData<String>()
    val expenseWeek : LiveData<String>
        get() = _expenseWeek
    private val _expenseDay = MutableLiveData<String>()
    val expenseDay : LiveData<String>
        get() = _expenseDay


    private val _remainingMonth = MutableLiveData<String>()
    val remainingMonth : LiveData<String>
        get() = _remainingMonth
    private val _remainingWeek = MutableLiveData<String>()
    val remainingWeek : LiveData<String>
        get() = _remainingWeek
    private val _remainingDay = MutableLiveData<String>()
    val remainingDay : LiveData<String>
        get() = _remainingDay


    private val _percentMonth = MutableLiveData<String>()
    val percentMonth : LiveData<String>
        get() = _percentMonth
    private val _percentWeek = MutableLiveData<String>()
    val percentWeek : LiveData<String>
        get() = _percentWeek
    private val _percentDay = MutableLiveData<String>()
    val percentDay : LiveData<String>
        get() = _percentDay

    private val _budgetMonth = MutableLiveData<String>()
    val budgetMonth : LiveData<String>
        get() = _budgetMonth
    private val _budgetWeek = MutableLiveData<String>()
    val budgetWeek : LiveData<String>
        get() = _budgetWeek
    private val _budgetDay = MutableLiveData<String>()
    val budgetDay : LiveData<String>
        get() = _budgetDay

    private val _budgetDayMonth = MutableLiveData<String>()
    val budgetDayMonth : LiveData<String>
        get() = _budgetDayMonth
    private val _budgetDayWeek = MutableLiveData<String>()
    val budgetDayWeek : LiveData<String>
        get() = _budgetDayWeek
    private val _budgetDayDay = MutableLiveData<String>()
    val budgetDayDay : LiveData<String>
        get() = _budgetDayDay






    private val _categoryList = MutableLiveData<List<CategoryDatabase>>()
    val categoryList : LiveData<List<CategoryDatabase>>
        get() = _categoryList

    private val _categorySpinnerEntries = MutableLiveData<List<String>>()
    val categorySpinnerEntries : LiveData<List<String>>
        get() = _categorySpinnerEntries

    private val _transactionList = MutableLiveData<List<TransactionListClass>>()
    val transactionList : LiveData<List<TransactionListClass>>
        get() = _transactionList

    val categorySpinnerSelectedPosition = MutableLiveData<Int>()

    private val repository : DatabaseRepository


    init {

        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)
        myCalendarToday = DateManipulation.resetToMidnight(myCalendarToday)
        myCalendarStart = DateManipulation.getStartOfMonth(myCalendarToday,startOfMonth, weekendEnabled,weekendShift)
        myCalendarEnd = DateManipulation.getEndOfMonth(myCalendarToday,startOfMonth, weekendEnabled,weekendShift)
        _dateToday.value = DateManipulation.getDateArray(myCalendarToday)
        readAllExpenseCategory = repository.readAllExpenseCategory
        categorySpinnerSelectedPosition.value = 0
        _percentMonth.value = "0"
        _percentWeek.value = "0"
        _percentDay.value = "0"
    }




    fun updateSpinner(it: List<CategoryDatabase>?) {
        _categoryList.value = it?.sortedBy { it.categoryName }
        _categorySpinnerEntries.value = _categoryList.value?.map { it.categoryName }

        categorySelected = _categoryList.value?.get(categorySpinnerSelectedPosition.value!!)!!

        updateAnalysis(categorySelected.categoryId,myCalendarStart.timeInMillis,myCalendarToday.timeInMillis)
    }

    private fun updateAnalysis(categoryId: Long?, dateStart: Long, dateEnd: Long) {
        viewModelScope.launch(Dispatchers.IO)
        {
            _transactionList.postValue(repository.getTransactionsAllAccountWithCategoryList(TransactionType.EXPENSE,categoryId!!,dateStart,dateEnd))
        }
    }

    companion object {
        private const val MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000
        private const val DEFAULT_REMAINING = "\u221E"
    }

    private fun setDefaults()
    {
        _budgetMonth.value = DEFAULT_REMAINING
        _budgetWeek.value = DEFAULT_REMAINING
        _budgetDay.value = DEFAULT_REMAINING

        _budgetDayMonth.value = DEFAULT_REMAINING
        _budgetDayWeek.value = DEFAULT_REMAINING
        _budgetDayDay.value = DEFAULT_REMAINING

        _remainingMonth.value = DEFAULT_REMAINING
        _remainingWeek.value = DEFAULT_REMAINING
        _remainingDay.value = DEFAULT_REMAINING

        _percentMonth.value = "0.0"
        _percentWeek.value = "0.0"
        _percentDay.value = "0.0"


    }

    fun updateData(transactions: List<TransactionListClass>?) {

        transactions ?: return

        val startOfWeek = DateManipulation.getStartOfWeek(myCalendarToday)
        val endOfWeek = DateManipulation.getEndOfWeek(myCalendarToday)

        val formatter = DecimalFormat("#.##")


        val totalMonth = transactions.map { it.amount }.sum()
        val totalWeek =
            transactions.filter { it.date >= startOfWeek.timeInMillis }.map { it.amount }.sum()
        val totalDay =
            transactions.filter { it.date == myCalendarToday.timeInMillis }.map { it.amount }.sum()

        _expenseMonth.value = formatter.format(totalMonth)
        _expenseWeek.value = formatter.format(totalWeek)
        _expenseDay.value = formatter.format(totalDay)

        setDefaults()

        if (categorySelected.categoryBudget!=0f) {

            val budgetMonth = categorySelected.categoryBudget
            val budgetDay = budgetMonth / myCalendarToday.getActualMaximum(Calendar.DAY_OF_MONTH)
            val budgetWeek = budgetDay * 7


            val remainingDaysMonth =
                ((myCalendarEnd.timeInMillis - myCalendarToday.timeInMillis) / MILLISECONDS_IN_DAY).toInt() + 1
            val remainingDaysWeek =
                ((endOfWeek.timeInMillis - myCalendarToday.timeInMillis) / MILLISECONDS_IN_DAY).toInt() + 1

            val tmp1 = budgetMonth - totalMonth
            val tmp2 = budgetWeek - totalWeek
            val tmp3 = budgetDay - totalDay


            val remainingMonthDay = tmp1 / remainingDaysMonth
            val remainingWeekDay = tmp2 / remainingDaysWeek

            _remainingMonth.value = formatter.format(tmp1)
            _remainingWeek.value  = formatter.format(tmp2)
            _remainingDay.value  = formatter.format(tmp3)

            _percentMonth.value  = (100-(tmp1/budgetMonth*100)).toInt().toString()
            _percentWeek.value  = (100-(tmp2/budgetWeek*100)).toInt().toString()
            _percentDay.value  = (100-(tmp3/budgetDay*100)).toInt().toString()


            _budgetMonth.value=formatter.format(budgetMonth)
            _budgetWeek.value=formatter.format(budgetWeek)
            _budgetDay.value=formatter.format(budgetDay)

            _budgetDayMonth.value=formatter.format(remainingMonthDay)
            _budgetDayWeek.value=formatter.format(remainingWeekDay)


        }



    }

    fun updateSelectedCategory(position: Int) {
        categorySelected = _categoryList.value?.get(position)!!
        updateAnalysis(categorySelected.categoryId,myCalendarStart.timeInMillis,myCalendarToday.timeInMillis)
    }


}