package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.kinetx.moneymanager.database.BalanceDatabase
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.database.TransactionDatabase
import com.kinetx.moneymanager.dataclass.IncomeExpenseData
import com.kinetx.moneymanager.dataclass.TransactionChildList
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.helpers.CommonOperations
import com.kinetx.moneymanager.helpers.DateManipulation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.max

class MainViewModel(application: Application) : AndroidViewModel(application) {



    private val sp = PreferenceManager.getDefaultSharedPreferences(getApplication())
    private val startOfMonth : Int = sp.getString("startDayOfMonth","1")!!.toInt()
    private var currency : String = sp.getString("currency","CHF").toString()
    private var weekendEnabled : Boolean = sp.getBoolean("weekendSwitch",false);
    private var weekendShift : Int = sp.getString("weekendPref","0")?.toInt() ?: 0

    val exp = MutableLiveData<Float>()

    private val _selectedCurrency = MutableLiveData<String>()
    val selectedCurrency : LiveData<String>
        get() = _selectedCurrency

    private val _currentMonth = MutableLiveData<String>()
    val currentMonth : LiveData<String>
        get() = _currentMonth

    private val _currentMonthRange = MutableLiveData<String>()
    val currentMonthRange : LiveData<String>
        get() = _currentMonthRange

    private val _expenseMonth = MutableLiveData<String>()
    val expenseMonth : LiveData<String>
        get() = _expenseMonth

    private val _balanceMonth = MutableLiveData<Float>()
    val balanceMonth : LiveData<Float>
        get() = _balanceMonth

    private val _incomeMonth = MutableLiveData<String>()
    val incomeMonth : LiveData<String>
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

    var readAllAccounts : LiveData<List<CategoryDatabase>>
    var latestTransactions: LiveData<List<TransactionChildList>>

    private val repository : DatabaseRepository

    init {

        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        readAllAccounts = repository.readAllAccountCategory
        val m  = repository.getLatestTransactions().value?.map {
            TransactionChildList(it.transactionId,"","",1,it.transactionComment,it.transactionAmount.toFloat(),1,1,it.transactionType)
        }
        latestTransactions = Transformations.map(repository.getLatestTransactions()) { transactionList ->
            transactionList.map { transaction ->

                val transactionColor = when(transaction.transactionType)
                {
                    TransactionType.INCOME -> java.lang.Long.decode("0xFF1b365d").toInt()
                    TransactionType.EXPENSE -> java.lang.Long.decode("0xFF1b365d").toInt()
                    TransactionType.TRANSFER -> java.lang.Long.decode("0xFF1b365d").toInt()
                    TransactionType.BALANCE -> java.lang.Long.decode("0xFF1b365d").toInt()
                }
                val cal = Calendar.getInstance()
                cal.timeInMillis = transaction.transactionDate
                val date = DateManipulation.getDateArray(cal)

                TransactionChildList(
                    transaction.transactionId,
                    "", // Replace with appropriate values
                    date, // Replace with appropriate values
                    transactionColor, // Replace with appropriate values
                    transaction.transactionComment,
                    transaction.transactionAmount.toFloat(),
                    CommonOperations.getResourceInt(application,"help"), // Replace with appropriate values
                    java.lang.Long.decode("0xFF000000").toInt(), // Replace with appropriate values
                    transaction.transactionType
                )
            }
        }

        _expenseMonth.value = "0.0 $currency"
        _balanceMonth.value = 0.0f
        _percentMonth.value = 0.0f
        _incomeMonth.value  = "0.0 $currency"
        var myCalendar : Calendar = Calendar.getInstance()
        myCalendar = DateManipulation.resetToMidnight(myCalendar)
        _selectedCurrency.value = currency

        updateIncomeExpenseQuery(myCalendar)
        val s = DateManipulation.getStartOfMonth(myCalendar,startOfMonth, weekendEnabled,weekendShift)
        val e = DateManipulation.getEndOfMonth(myCalendar,startOfMonth, weekendEnabled, weekendShift)

        _currentMonthRange.value = DateManipulation.getDateArray(s) + " - " + DateManipulation.getDateArray(e)
        if (startOfMonth > 15)
        {
            _currentMonth.value = DateManipulation.getMonthArray(e)
        }
        else
        {
            _currentMonth.value = DateManipulation.getMonthArray(s)
        }

        _fragmentTitle.value = "Money Manager"

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun databaseQueries(accountList: List<CategoryDatabase>) {

        // Query for updating the balances table, it is on global scope for now
        GlobalScope.launch(Dispatchers.IO)
        {

            var today : Calendar = Calendar.getInstance()
            var date : Calendar = Calendar.getInstance()


            accountList.forEach { account ->
                val latestBalance = repository.getLatestBalanceWithAccount(account.categoryId)
                val firstTransaction = repository.getFirstTransactionWithAccount(account.categoryId)

                if (firstTransaction!=null)
                {
                    date.timeInMillis = max( firstTransaction.transactionDate, latestBalance.monthEnd)
                    date.add(Calendar.DAY_OF_MONTH,1)

                    var monthStart = DateManipulation.getStartOfMonth(
                        date,
                        startOfMonth,
                        weekendEnabled,
                        weekendShift
                    )
                    var monthEnd = DateManipulation.getEndOfMonth(
                        date,
                        startOfMonth,
                        weekendEnabled,
                        weekendShift
                    )

                    while (today > monthEnd){

                    var initialBalance  = latestBalance.balance

                    val transactions = repository.getTransactionsWithAccountForDate(account.categoryId, monthStart.timeInMillis, monthEnd.timeInMillis)
                    val income =
                        transactions?.filter { it.transactionType == TransactionType.INCOME }
                            ?.sumOf { it.transactionAmount } ?: 0.0

                    val expense =
                        transactions?.filter { it.transactionType == TransactionType.EXPENSE }
                            ?.sumOf { it.transactionAmount } ?: 0.0

                    val transferOut =
                        transactions?.filter { it.transactionType == TransactionType.TRANSFER && it.transactionCategoryOne == account.categoryId }
                            ?.sumOf { it.transactionAmount }
                        ?:0.0
                    val transferIn =
                        transactions?.filter { it.transactionType == TransactionType.TRANSFER && it.transactionCategoryTwo == account.categoryId }
                            ?.sumOf { it.transactionAmount }
                        ?:0.0


                    val newBalance = initialBalance + income - expense - transferOut + transferIn

                        val roundedValue = "%.2f".format(newBalance).toDouble()

                    val balanceDatabase = BalanceDatabase(0L, account.categoryId, monthEnd.timeInMillis, roundedValue)
                    repository.insertBalanceWithAccount(balanceDatabase)
                    monthEnd.add(Calendar.DAY_OF_MONTH,1)

                    monthStart = DateManipulation.getStartOfMonth(monthEnd,startOfMonth,weekendEnabled,weekendShift)
                    monthEnd =  DateManipulation.getEndOfMonth(monthEnd,startOfMonth,weekendEnabled,weekendShift)
                    }
                }



            }
        }

    }

    fun updateIncomeExpenseQuery(myCalendar: Calendar) {
        viewModelScope.launch(Dispatchers.IO)
        {
            val s = DateManipulation.getStartOfMonth(myCalendar,startOfMonth,weekendEnabled, weekendShift)
            val e = DateManipulation.getEndOfMonth(myCalendar,startOfMonth, weekendEnabled, weekendShift)
            _incomeExpenseQuery.postValue(repository.getIncomeExpenseSummary(s.timeInMillis,e.timeInMillis))
        }
    }


    fun updateIncomeExpense(it: IncomeExpenseData?) {

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN

        val a = it?.income!! - it.expense
        _incomeMonth.value = "${df.format(it.income)} $currency"
        _expenseMonth.value = "${df.format(it.expense)} $currency"
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