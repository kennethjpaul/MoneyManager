package com.kinetx.moneymanager.viewmodel

import android.app.Application
import android.view.View
import android.widget.RadioGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository

class PlotTransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    private val _accountSpinnerEntries = MutableLiveData<List<String>>()
    val accountSpinnerEntries : LiveData<List<String>>
        get() = _accountSpinnerEntries

    private val _categorySpinnerIsVisible = MutableLiveData<Int>()
    val categorySpinnerIsVisible : LiveData<Int>
        get() = _categorySpinnerIsVisible

    private val _categorySpinnerEntries = MutableLiveData<List<String>>()
    val categorySpinnerEntries : LiveData<List<String>>
        get() = _categorySpinnerEntries


    var accountDbList : LiveData<List<CategoryDatabase>>
    var incomeDbList : LiveData<List<CategoryDatabase>>
    var expenseDbList : LiveData<List<CategoryDatabase>>
    private val repository : DatabaseRepository

    init {
        _fragmentTitle.value = "Select the plot"
        _categorySpinnerIsVisible.value = View.GONE

        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)

        accountDbList = repository.readAllAccountCategory
        incomeDbList = repository.readAllIncomeCategory
        expenseDbList = repository.readAllExpenseCategory


    }

    fun onRadioClick(radioGroup : RadioGroup, id : Int)
    {
        _accountSpinnerEntries.value = populateSpinner(accountDbList.value)
        when(id)
        {
            R.id.plot_category_income ->
            {
                _categorySpinnerEntries.value = populateSpinner(incomeDbList.value)
                _categorySpinnerIsVisible.value = View.VISIBLE
            }
            R.id.plot_category_expense ->
            {
                _categorySpinnerEntries.value = populateSpinner(expenseDbList.value)
                _categorySpinnerIsVisible.value = View.VISIBLE
            }
            R.id.plot_category_transfer ->
            {
                _categorySpinnerIsVisible.value = View.GONE
            }
        }
    }

    private fun populateSpinner (list : List<CategoryDatabase>?): List<String> {
        val l = mutableListOf("All")

        if (list != null) {
            for (i in list) {
                l.add(i.categoryName)
            }
        }

        return l
    }



}