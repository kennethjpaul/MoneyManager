package com.kinetx.moneymanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository

class CategorySummaryVM(application: Application):AndroidViewModel(application) {

    var readAllExpenseCategories : LiveData<List<CategoryDatabase>>
    var readAllIncomeCategories : LiveData<List<CategoryDatabase>>

    private val repository : DatabaseRepository

    init
    {
        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)
        readAllExpenseCategories = repository.readAllExpenseCategory
        readAllIncomeCategories = repository.readAllIncomeCategory
    }


}