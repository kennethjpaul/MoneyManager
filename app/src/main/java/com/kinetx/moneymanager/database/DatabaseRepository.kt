package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData

class DatabaseRepository (private val databaseDao: DatabaseDao) {

    val readAllIncomeCategory : LiveData<List<CategoryDatabase>> = databaseDao.getAllIncomeCategory()
    val readAllExpeneCategory : LiveData<List<CategoryDatabase>> = databaseDao.getAllExpenseCategory()
    val readAllAccountCategory : LiveData<List<CategoryDatabase>> = databaseDao.getAllAccountCategory()

    suspend fun insertCategory(category: CategoryDatabase)
    {
        databaseDao.insertCategory(category)
    }

    suspend fun updateCategory(category: CategoryDatabase)
    {
        databaseDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: CategoryDatabase)
    {
        databaseDao.deleteCategory(category)
    }
}