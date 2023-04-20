package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kinetx.moneymanager.dataclass.ImageButtonData
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.database.TransactionDatabase
import com.kinetx.moneymanager.enums.CategoryType
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.fragment.AddTransactionFragmentArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTransactionViewModel(val argList: AddTransactionFragmentArgs, val application: Application): ViewModel() {

    private val monthArray = arrayOf(
        "Jan", "Feb",
        "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    val transactionAmount = MutableLiveData<String>()
    val transactionComment = MutableLiveData<String>()


    private val _selectedDay = MutableLiveData<String>()
    val selectedDay : LiveData<String>
        get() = _selectedDay

    private val _selectedMonth = MutableLiveData<String>()
    val selectedMonth : LiveData<String>
        get() = _selectedMonth

    private val _selectedYear = MutableLiveData<String>()
    val selectedYear : LiveData<String>
        get() = _selectedYear

    private val _categoryPositionOne = MutableLiveData<ImageButtonData>()
    val categoryPositionOne : LiveData<ImageButtonData>
        get() = _categoryPositionOne

    private val _categoryPositionOneText = MutableLiveData<String>()
    val categoryPositionOneText : LiveData<String>
        get() = _categoryPositionOneText

    private val _categoryPositionTwoText = MutableLiveData<String>()
    val categoryPositionTwoText : LiveData<String>
        get() = _categoryPositionTwoText

    private val _categoryPositionTwo = MutableLiveData<ImageButtonData>()
    val categoryPositionTwo : LiveData<ImageButtonData>
        get() = _categoryPositionTwo

    private val _currencySpinner = MutableLiveData<List<String>>()
    val currencySpinner : LiveData<List<String>>
        get() = _currencySpinner

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val myCalendar: Calendar = Calendar.getInstance()

    private val repository : DatabaseRepository

    init {

        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)
        myCalendar.set(Calendar.HOUR_OF_DAY,0)
        myCalendar.set(Calendar.MINUTE,0)
        myCalendar.set(Calendar.SECOND,0)
        myCalendar.set(Calendar.MILLISECOND,0)

        _currencySpinner.value = listOf("CHF","EUR","INR","USD")
        _categoryPositionOne.value = ImageButtonData(-1,
            R.drawable.help,java.lang.Long.decode("0xFF5d8aa8").toInt(),"", CategoryType.ACCOUNT)
        _categoryPositionTwo.value = ImageButtonData(-1,
            R.drawable.help,java.lang.Long.decode("0xFF5d8aa8").toInt(),"", CategoryType.ACCOUNT)
        transactionAmount.value = ""
        transactionComment.value=""
        when(argList.transactionType)
        {
            TransactionType.INCOME ->
            {
                _categoryPositionTwo.value!!.buttonType = CategoryType.INCOME
                _categoryPositionOneText.value = "Account"
                _categoryPositionTwoText.value = "Category"
                _fragmentTitle.value = "Add Income"
            }
            TransactionType.EXPENSE ->
            {
                _categoryPositionTwo.value!!.buttonType = CategoryType.EXPENSE
                _categoryPositionOneText.value = "Account"
                _categoryPositionTwoText.value = "Category"
                _fragmentTitle.value = "Add Expense"
            }
            TransactionType.TRANSFER->
            {
                _categoryPositionTwo.value!!.buttonType = CategoryType.ACCOUNT
                _categoryPositionOneText.value = "Source"
                _categoryPositionTwoText.value = "Destination"
                _fragmentTitle.value = "Add Transfer"
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


    fun updateCategoryPositionTwo(varId: Long, varImgId: Int, varBgColor: Int, itemTitle: String) {
        _categoryPositionTwo.value?.buttonId = varId
        _categoryPositionTwo.value?.buttonImage =varImgId
        _categoryPositionTwo.value?.buttonColor = varBgColor
        _categoryPositionTwo.value?.buttonTitle = itemTitle
    }

    fun updateCategoryPositionOne(varId: Long, varImgId: Int, varBgColor: Int, itemTitle: String) {
        _categoryPositionOne.value?.buttonId = varId
        _categoryPositionOne.value?.buttonImage =varImgId
        _categoryPositionOne.value?.buttonColor = varBgColor
        _categoryPositionOne.value?.buttonTitle = itemTitle
    }

    fun addTransaction(): Boolean {

        if (_categoryPositionOne.value?.buttonId==-1L)
        {
            Toast.makeText(application, "Select the ${_categoryPositionOneText.value}", Toast.LENGTH_SHORT).show()
            return false
        }

        if (_categoryPositionTwo.value?.buttonId==-1L)
        {
            Toast.makeText(application, "Select the ${_categoryPositionTwoText.value}", Toast.LENGTH_SHORT).show()
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
        val transaction = TransactionDatabase(0,transactionAmount.value!!.toFloat(),argList.transactionType,_categoryPositionOne.value!!.buttonId,categoryPositionTwo.value!!.buttonId,dateLong,transactionComment.value!!)

        addTransactionDao(transaction)

        return true
    }

    private fun addTransactionDao(transaction: TransactionDatabase) {

        viewModelScope.launch(Dispatchers.IO)
        {
            repository.insertTransaction(transaction)
        }
    }

}