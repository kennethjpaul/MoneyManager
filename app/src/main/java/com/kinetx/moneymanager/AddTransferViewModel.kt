package com.kinetx.moneymanager

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddTransferViewModel: ViewModel() {
    private val monthArray = arrayOf(
        "Jan", "Feb",
        "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    private val _sourceAccountImageButton = MutableLiveData<ImageButtonData>()
    val sourceAccountImageButton : LiveData<ImageButtonData>
        get() = _sourceAccountImageButton

    private val _destinationAccountImageButton = MutableLiveData<ImageButtonData>()
    val destinationAccountImageButton : LiveData<ImageButtonData>
        get() = _destinationAccountImageButton

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

        _sourceAccountImageButton.value = ImageButtonData(1,R.drawable.android,R.color.purple_500)
        _destinationAccountImageButton.value = ImageButtonData(1,R.drawable.android,R.color.teal_700)

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


    fun updateSource(varId: Long, varImgId: Int, varBgColor: Int) {
        _sourceAccountImageButton.value?.id = varId
        _sourceAccountImageButton.value?.imageId =varImgId
        _sourceAccountImageButton.value?.bgColor = varBgColor
    }

    fun updateDestination(varId: Long, varImgId: Int, varBgColor: Int) {
        _destinationAccountImageButton.value?.id = varId
        _destinationAccountImageButton.value?.imageId =varImgId
        _destinationAccountImageButton.value?.bgColor = varBgColor
    }
}