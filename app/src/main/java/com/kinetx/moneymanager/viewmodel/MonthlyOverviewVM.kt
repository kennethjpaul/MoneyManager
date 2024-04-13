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
import com.kinetx.moneymanager.dataclass.AccountListData
import com.kinetx.moneymanager.dataclass.CategoryListData
import com.kinetx.moneymanager.dataclass.TransactionListClass
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.helpers.CommonOperations
import com.kinetx.moneymanager.helpers.DateManipulation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MonthlyOverviewVM(application: Application):AndroidViewModel(application) {

    val app = application
    private val sp = PreferenceManager.getDefaultSharedPreferences(getApplication())
    private val startOfMonth : Int = sp.getString("startDayOfMonth","1")!!.toInt()
    private var weekendEnabled : Boolean = sp.getBoolean("weekendSwitch",false);
    private var weekendShift : Int = sp.getString("weekendPref","0")?.toInt() ?: 0


    var myCalendarToday: Calendar = Calendar.getInstance()
    var myCalendarStart : Calendar = Calendar.getInstance()

    private val _startDate = MutableLiveData<String>()
    val startDate : LiveData<String>
        get() = _startDate

    private val _endDate = MutableLiveData<String>()
    val endDate : LiveData<String>
        get() = _endDate

    private val _transactionQuery = MutableLiveData<List<AccountListData>>()
    val transactionQuery : LiveData<List<AccountListData>>
        get() = _transactionQuery



    private val repository : DatabaseRepository

    init {
        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        myCalendarToday = DateManipulation.resetToMidnight(myCalendarToday)
        myCalendarStart = DateManipulation.getStartOfMonth(myCalendarToday,startOfMonth, weekendEnabled,weekendShift)

        _startDate.value = DateManipulation.getDateArray(myCalendarStart)
        _endDate.value = DateManipulation.getDateArray(myCalendarToday)

        updateAccounts()

    }

    private fun updateAccounts() {
        viewModelScope.launch(Dispatchers.IO)
        {
            val k = repository.getAllTransactions(myCalendarStart.timeInMillis,myCalendarToday.timeInMillis)
            val accountBalance = repository.getAccountSummary()

            val categoryIncomeExpense = k.groupBy { it.categoryOne }.map { entry ->
                val sums = entry.value.groupBy { it.transactionType }.mapValues { (_, transactions) ->
                    transactions.map { it.amount }.sum()
                }
                val income = sums[TransactionType.INCOME] ?: 0f
                val expense = sums[TransactionType.EXPENSE] ?: 0f
                val transfer = sums[TransactionType.TRANSFER] ?: 0f
                val others = k.filter { it.categoryTwo == entry.key }.map { it.amount }.sum()
                entry.key to (income + others to expense + transfer)
            }.toMap()

            _transactionQuery.postValue(accountBalance.map { balance->
                val(income,expense) = categoryIncomeExpense[balance.categoryName] ?:(0f to 0f)
                AccountListData(balance.categoryName,balance.categoryId,CommonOperations.getResourceInt(app,balance.categoryImage),balance.categoryColor,
                    CommonOperations.roundNumber(balance.amount,2) ,CommonOperations.roundNumber(income,2), CommonOperations.roundNumber(expense,2))
            })
        }
    }

}