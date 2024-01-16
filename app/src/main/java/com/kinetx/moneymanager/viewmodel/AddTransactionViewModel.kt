package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kinetx.moneymanager.dataclass.ImageButtonData
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.database.TransactionDatabase
import com.kinetx.moneymanager.enums.CategoryType
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.fragment.AddTransactionFragmentArgs
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTransactionViewModel(val argList: AddTransactionFragmentArgs, val application: Application): ViewModel() {

    private val monthArray = arrayOf(
        "Jan", "Feb",
        "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    val transactionAmount = MutableLiveData<String>()
    val transactionComment = MutableLiveData<String>()

    val transaction = MutableLiveData<TransactionDatabase>()

    private val _selectedDay = MutableLiveData<String>()
    val selectedDay : LiveData<String>
        get() = _selectedDay

    private val _selectedMonth = MutableLiveData<String>()
    val selectedMonth : LiveData<String>
        get() = _selectedMonth

    private val _selectedYear = MutableLiveData<String>()
    val selectedYear : LiveData<String>
        get() = _selectedYear

    private val _isAddBtnVisible = MutableLiveData<Int>()
    val isAddBtnVisible : LiveData<Int>
        get() = _isAddBtnVisible

    private val _isUpdateDeleteBtnVisible = MutableLiveData<Int>()
    val isUpdateDeleteBtnVisible : LiveData<Int>
        get() = _isUpdateDeleteBtnVisible

    private val _categoryPositionOne = MutableLiveData<CategoryDatabase>()
    val categoryPositionOne : LiveData<CategoryDatabase>
        get() = _categoryPositionOne

    private val _buttonPositionOneText = MutableLiveData<String>()
    val buttonPositionOneText : LiveData<String>
        get() = _buttonPositionOneText



    private val _categoryPositionTwo = MutableLiveData<CategoryDatabase>()
    val categoryPositionTwo : LiveData<CategoryDatabase>
        get() = _categoryPositionTwo

    private val _buttonPositionTwoText = MutableLiveData<String>()
    val buttonPositionTwoText : LiveData<String>
        get() = _buttonPositionTwoText


    private val _currencySpinner = MutableLiveData<List<String>>()
    val currencySpinner : LiveData<List<String>>
        get() = _currencySpinner

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val myCalendar: Calendar = Calendar.getInstance()

    private val repository : DatabaseRepository

    val ttt = MutableLiveData<CategoryDatabase>()
    var titleStringStart : String = ""

    init {

        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        myCalendar.set(Calendar.HOUR_OF_DAY,0)
        myCalendar.set(Calendar.MINUTE,0)
        myCalendar.set(Calendar.SECOND,0)
        myCalendar.set(Calendar.MILLISECOND,0)

        _currencySpinner.value = listOf("CHF","EUR","INR","USD")

        _categoryPositionOne.value = CategoryDatabase(
            -1,
            "",
            CategoryType.ACCOUNT,
            R.drawable.help,
            "help",
            java.lang.Long.decode("0xFF000000").toInt()
        )
        _categoryPositionTwo.value = CategoryDatabase(
            -1,
            "",
            CategoryType.ACCOUNT,
            R.drawable.help,
            "help",
            java.lang.Long.decode("0xFF000000").toInt()
        )

        if(argList.transactionId==-1L)
        {

            transactionAmount.value = ""
            transactionComment.value = ""
            titleStringStart = "Add"
            _isAddBtnVisible.value = View.VISIBLE
            _isUpdateDeleteBtnVisible.value = View.GONE

        }
        else
        {
            viewModelScope.launch (Dispatchers.IO)
            {
                transaction.postValue(repository.getTransactionById(argList.transactionId))
            }

            titleStringStart = "Edit"
            _isAddBtnVisible.value = View.GONE
            _isUpdateDeleteBtnVisible.value = View.VISIBLE
        }

       // viewModelScope.launch(Dispatchers.IO) {
       //     ttt.postValue(repository.getCategoryByName("Main"))
       // }


        when(argList.transactionType)
        {
            TransactionType.INCOME ->
            {
                _categoryPositionTwo.value!!.categoryType = CategoryType.INCOME
                _buttonPositionOneText.value = "Account"
                _buttonPositionTwoText.value = "Category"
                _fragmentTitle.value = "$titleStringStart Income Transaction"
            }
            TransactionType.EXPENSE ->
            {
                _categoryPositionTwo.value!!.categoryType = CategoryType.EXPENSE
                _buttonPositionOneText.value = "Account"
                _buttonPositionTwoText.value = "Category"
                _fragmentTitle.value = "$titleStringStart Expense Transaction"
            }
            TransactionType.TRANSFER->
            {
                _categoryPositionTwo.value!!.categoryType = CategoryType.ACCOUNT
                _buttonPositionOneText.value = "Source"
                _buttonPositionTwoText.value = "Destination"
                _fragmentTitle.value = "$titleStringStart Transfer Transaction"
            }
            else ->
            {
                _categoryPositionTwo.value!!.categoryType = CategoryType.EXPENSE
                _buttonPositionOneText.value = "Account"
                _buttonPositionTwoText.value = "Category"
                _fragmentTitle.value = "$titleStringStart Expense Transaction"
            }
        }


        _selectedDay.value = myCalendar.get(Calendar.DAY_OF_MONTH).toString()
        _selectedMonth.value = monthArray[myCalendar.get(
            Calendar.MONTH)]
        _selectedYear.value = myCalendar.get(Calendar.YEAR).toString()

    }

    private val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayofMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, month)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayofMonth)
        _selectedDay.value = dayofMonth.toString()
        _selectedMonth.value = monthArray[month]
        _selectedYear.value = year.toString()
    }

    fun datePick(it: View?) {
        if (it != null) {
            DatePickerDialog(
                it.context,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


    fun addTransaction(): Boolean {




        if (_categoryPositionOne.value?.categoryId==-1L)
        {
            Toast.makeText(application, "Select the ${_buttonPositionOneText.value}", Toast.LENGTH_SHORT).show()
            return false
        }

        if (_categoryPositionOne.value?.categoryId==-1L)
        {
            Toast.makeText(application, "Select the ${_buttonPositionTwoText.value}", Toast.LENGTH_SHORT).show()
            return false
        }

        if (transactionAmount.value=="")
        {
            Toast.makeText(application, "Enter an amount", Toast.LENGTH_SHORT).show()
            return false
        }

        if (transactionAmount.value?.toFloat()==0.0f)
        {
            Toast.makeText(application, "Enter an amount greater than zero", Toast.LENGTH_SHORT).show()
            return false
        }

        var dateLong : Long = myCalendar.timeInMillis
        val transaction = TransactionDatabase(0,transactionAmount.value!!.toFloat(),argList.transactionType,_categoryPositionOne.value!!.categoryId,categoryPositionTwo.value!!.categoryId,dateLong,transactionComment.value!!)

        addTransactionDao(transaction)

        return true
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addTransactionDao(transaction: TransactionDatabase) {

        GlobalScope.launch(Dispatchers.IO)
        {
            repository.insertTransaction(transaction)
        }
    }


    fun updateTransaction() : Boolean
    {
        if (transactionAmount.value=="")
        {
            Toast.makeText(application, "Enter an amount", Toast.LENGTH_SHORT).show()
            return false
        }

        if (transactionAmount.value?.toFloat()==0.0f)
        {
            Toast.makeText(application, "Enter an amount greater than zero", Toast.LENGTH_SHORT).show()
            return false
        }

        var dateLong : Long = myCalendar.timeInMillis
        val transaction = TransactionDatabase(argList.transactionId,transactionAmount.value!!.toFloat(),argList.transactionType,_categoryPositionOne.value!!.categoryId,categoryPositionTwo.value!!.categoryId,dateLong,transactionComment.value!!)
        updateTransactionDao(transaction)
        return true
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun updateTransactionDao(transaction: TransactionDatabase) {
        GlobalScope.launch(Dispatchers.IO)
        {
            repository.updateTransaction(transaction)
        }
    }


    fun deleteTransaction()
    {
        var dateLong : Long = myCalendar.timeInMillis
        val transaction = TransactionDatabase(argList.transactionId,transactionAmount.value!!.toFloat(),argList.transactionType,_categoryPositionOne.value!!.categoryId,categoryPositionTwo.value!!.categoryId,dateLong,transactionComment.value!!)
        deleteTransactionDao(transaction)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun deleteTransactionDao(transaction: TransactionDatabase) {
        GlobalScope.launch(Dispatchers.IO)
        {
            repository.deleteTransaction(transaction)
        }
    }

    fun categoryUpdate(itemId: Long, categoryPosition : Int) {

        when(categoryPosition)
        {
            1->transaction.value?.transactionCategoryOne = itemId
            2->transaction.value?.transactionCategoryTwo = itemId
        }

        viewModelScope.launch(Dispatchers.IO) {

            Log.i("Date","coroutine $itemId $categoryPosition")
            when(categoryPosition)
            {
                1 ->
                {
                    _categoryPositionOne.postValue(repository.getCategory(itemId))

                }
                2 ->
                {
                    _categoryPositionTwo.postValue(repository.getCategory(itemId))

                }
            }
        }

    }

    fun setCalendar(cal: Calendar) {

        myCalendar.set(Calendar.YEAR, cal.get(Calendar.YEAR))
        myCalendar.set(Calendar.MONTH, cal.get(Calendar.MONTH))
        myCalendar.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH))
        _selectedDay.value = cal.get(Calendar.DAY_OF_MONTH).toString()
        _selectedMonth.value = monthArray[cal.get(Calendar.MONTH)]
        _selectedYear.value = cal.get(Calendar.YEAR).toString()
    }

}