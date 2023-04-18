package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData
import com.kinetx.moneymanager.enums.TransactionType

class DatabaseRepository (private val databaseDao: DatabaseDao) {

    val readAllIncomeCategory : LiveData<List<CategoryDatabase>> = databaseDao.getAllIncomeCategory()
    val readAllExpenseCategory : LiveData<List<CategoryDatabase>> = databaseDao.getAllExpenseCategory()
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


    suspend fun insertTransaction(transaction : TransactionDatabase)
    {
        databaseDao.insertTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: TransactionDatabase)
    {
        databaseDao.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: TransactionDatabase)
    {
        databaseDao.deleteTransaction(transaction)
    }

    fun getTransactionsAllAccountsAllCategories(transactionType :TransactionType, dateStart :Long, dateEnd: Long) : LiveData<List<TransactionDatabase>>
    {
       return databaseDao.getTransactionsAllAccountsAllCategories(transactionType, dateStart, dateEnd)
    }

    fun getTransactionsAllAccountWithCategory(transactionType: TransactionType, categoryId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionDatabase>>
    {
        return databaseDao.getTransactionsAllAccountWithCategory(transactionType,categoryId,dateStart,dateEnd)
    }

    fun getTransactionsWithAccountAllCategory(transactionType: TransactionType, accountId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionDatabase>>
    {
        return databaseDao.getTransactionsWithAccountAllCategory(transactionType,accountId,dateStart,dateEnd)
    }

    fun getTransactionsWithAccountWithCategory(transactionType: TransactionType, accountId: Long, categoryId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionDatabase>>
    {
        return databaseDao.getTransactionsWithAccountWithCategory(transactionType,accountId,categoryId,dateStart,dateEnd)
    }
}