package com.kinetx.moneymanager

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddExpenseViewModel : ViewModel() {

    private val monthArray = arrayOf(
        "Jan", "Feb",
        "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    private val _accountImageButton = MutableLiveData<ImageButtonData>()
    val accountImageButton : LiveData<ImageButtonData>
        get() = _accountImageButton

    private val _categoryImageButton = MutableLiveData<ImageButtonData>()
    val categoryImageButton : LiveData<ImageButtonData>
        get() = _categoryImageButton

    private val _dateButton = MutableLiveData<String>()
    val dateButton : LiveData<String>
        get() = _dateButton

    private val _currencySpinner = MutableLiveData<List<String>>()
    val currencySpinner : LiveData<List<String>>
        get() = _currencySpinner

    private val myCalendar: Calendar = Calendar.getInstance()

    init {
        _dateButton.value = "${myCalendar.get(Calendar.DAY_OF_MONTH)} \n ${monthArray[myCalendar.get(Calendar.MONTH)]}\n ${myCalendar.get(Calendar.YEAR)}"
        _currencySpinner.value = listOf("CHF","EUR","INR","USD")

        _accountImageButton.value = ImageButtonData(1,R.drawable.android,R.color.purple_500)
        _categoryImageButton.value = ImageButtonData(1,R.drawable.android,R.color.teal_700)

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

    fun updateCategory(varId: Long, varImgId: Int, varBgColor: Int) {
        _categoryImageButton.value?.id = varId
        _categoryImageButton.value?.imageId =varImgId
        _categoryImageButton.value?.bgColor = varBgColor
    }

    fun updateAccount(varId: Long, varImgId: Int, varBgColor: Int) {
        _accountImageButton.value?.id = varId
        _accountImageButton.value?.imageId =varImgId
        _accountImageButton.value?.bgColor = varBgColor
    }
}