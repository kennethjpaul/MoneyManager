package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData

class DatabaseRepository (private val databaseDao: DatabaseDao) {

    val readAllIncomeCategory : LiveData<List<CategoryDatabase>> = databaseDao.getAllIncomeCategory()

    suspend fun insertCategory(category: CategoryDatabase)
    {
        databaseDao.insertCategory(category)
    }


}