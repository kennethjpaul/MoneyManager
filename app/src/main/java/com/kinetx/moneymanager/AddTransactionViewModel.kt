package com.kinetx.moneymanager

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddTransactionViewModel: ViewModel() {

    private val monthArray = arrayOf(
        "Jan", "Feb",
        "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

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

    private val _dateButton = MutableLiveData<String>()
    val dateButton : LiveData<String>
        get() = _dateButton

    private val _currencySpinner = MutableLiveData<List<String>>()
    val currencySpinner : LiveData<List<String>>
        get() = _currencySpinner

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val myCalendar: Calendar = Calendar.getInstance()

    init {
        _dateButton.value = "${myCalendar.get(Calendar.DAY_OF_MONTH)} \n ${monthArray[myCalendar.get(
            Calendar.MONTH)]}\n ${myCalendar.get(Calendar.YEAR)}"
        _currencySpinner.value = listOf("CHF","EUR","INR","USD")
        _categoryPositionOne.value = ImageButtonData(1,R.drawable.android,R.color.purple_500)
        _categoryPositionTwo.value = ImageButtonData(1,R.drawable.android,R.color.teal_700)
        _categoryPositionOneText.value = "Account"
        _categoryPositionTwoText.value = "Category"
    }

    private val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayofMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, month)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayofMonth)
        updateDate(year, month, dayofMonth)
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

    private fun updateDate(year: Int, month: Int, dayofMonth: Int) {
        _dateButton.value = "$dayofMonth \n ${monthArray[month]} \n$year"
    }

    fun updateCategoryPositionTwo(varId: Long, varImgId: Int, varBgColor: Int) {
        _categoryPositionTwo.value?.id = varId
        _categoryPositionTwo.value?.imageId =varImgId
        _categoryPositionTwo.value?.bgColor = varBgColor
    }

    fun updateCategoryPositionOne(varId: Long, varImgId: Int, varBgColor: Int) {
        _categoryPositionOne.value?.id = varId
        _categoryPositionOne.value?.imageId =varImgId
        _categoryPositionOne.value?.bgColor = varBgColor
    }

    fun initializeLayout(argList: AddTransactionFragmentArgs) {
        _fragmentTitle.value = "Add ${argList.transactionType}"
        if (argList.transactionType=="transfer")
        {
            _categoryPositionOneText.value = "Source"
            _categoryPositionTwoText.value = "Destination"
        }
    }
}