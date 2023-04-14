package com.kinetx.moneymanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kinetx.moneymanager.fragment.SelectCategoryFragmentArgs
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.database.DatabaseDao
import com.kinetx.moneymanager.database.DatabaseMain
import com.kinetx.moneymanager.database.DatabaseRepository
import com.kinetx.moneymanager.enums.CategoryType

class SelectCategoryViewModel (val database: DatabaseDao, application: Application, argList: SelectCategoryFragmentArgs) : AndroidViewModel(application) {

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    lateinit var readAllCategories : LiveData<List<CategoryDatabase>>
    private val repository : DatabaseRepository

    init {
        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)


        when(argList.categoryType)
        {
            CategoryType.INCOME ->
            {
                readAllCategories = repository.readAllIncomeCategory
                _fragmentTitle.value = "Select Income Category"
            }
            CategoryType.EXPENSE->
            {
                readAllCategories = repository.readAllExpeneCategory
                _fragmentTitle.value = "Select Expense Category"

            }
            CategoryType.ACCOUNT->
            {
                readAllCategories = repository.readAllAccountCategory
                _fragmentTitle.value = "Select Account"
            }
        }


    }


}
