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

class SelectCategoryViewModel (val database: DatabaseDao, application: Application, argList: SelectCategoryFragmentArgs) : AndroidViewModel(application) {

    private val _fragmentTitle = MutableLiveData<String>()
    val fragmentTitle : LiveData<String>
        get() = _fragmentTitle

    lateinit var readAllCategories : LiveData<List<CategoryDatabase>>
    private val repository : DatabaseRepository

    init {
        val userDao = DatabaseMain.getInstance(application).databaseDao
        repository = DatabaseRepository(userDao)


        if (argList.categoryType=="category" && argList.transactionType=="expense")
        {
            readAllCategories = repository.readAllExpeneCategory
            _fragmentTitle.value = "Select category"

        }

        if (argList.categoryType=="category" && argList.transactionType=="income")
        {
            readAllCategories = repository.readAllIncomeCategory
            _fragmentTitle.value = "Select category"


        }

        if (argList.categoryType=="account" || argList.transactionType=="transfer")
        {
            readAllCategories = repository.readAllAccountCategory
            _fragmentTitle.value = "Select account"


        }

    }


}
