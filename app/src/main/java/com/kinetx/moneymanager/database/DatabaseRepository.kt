package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData
import com.kinetx.moneymanager.dataclass.CategoryListData
import com.kinetx.moneymanager.dataclass.CategoryQueryData
import com.kinetx.moneymanager.dataclass.IncomeExpenseData
import com.kinetx.moneymanager.dataclass.TransactionListClass
import com.kinetx.moneymanager.enums.TransactionType

class DatabaseRepository (private val databaseDao: DatabaseDao) {

    val readAllIncomeCategory : LiveData<List<CategoryDatabase>> = databaseDao.getAllIncomeCategory()
    val readAllExpenseCategory : LiveData<List<CategoryDatabase>> = databaseDao.getAllExpenseCategory()
    val readAllAccountCategory : LiveData<List<CategoryDatabase>> = databaseDao.getAllAccountCategory()
    val readAllCategoryNames : LiveData<List<String>> = databaseDao.getAllCategoryNames()



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

    //suspend fun getAllCategoryNames : List<String>

    suspend fun getCategoryByName(categoryName : String) : CategoryDatabase?
    {
        return databaseDao.getCategoryByName(categoryName)
    }

    suspend fun getCategoryNameById(categoryId : Long) : String
    {
        return databaseDao.getCategoryNameById(categoryId)
    }

    suspend fun getCategory(categoryId : Long) : CategoryDatabase?
    {
        return databaseDao.getCategory(categoryId)
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

    suspend fun deleteTransactionsOfCategory(categoryId: Long)
    {
        databaseDao.deleteTransactionsOfCategory(categoryId)
    }

    fun getAllTransactions(dateStart: Long, dateEnd: Long) : List<TransactionListClass>
    {
        return databaseDao.getAllTransactions(dateStart,dateEnd)
    }

    suspend fun getTransactionById(transctionId : Long) : TransactionDatabase?
    {
        return databaseDao.getTransactionById(transctionId)
    }

    suspend fun getTransactionInitialBalance(categoryId: Long) : TransactionDatabase?
    {
        return databaseDao.getTransactionInitialBalance(categoryId)
    }

    suspend fun getTransactionsAllAccountsAllCategories(transactionType :TransactionType, dateStart :Long, dateEnd: Long) : List<TransactionListClass>
    {
       return databaseDao.getTransactionsAllAccountsAllCategories(transactionType, dateStart, dateEnd)
    }

    suspend fun getTransactionsAllAccountWithCategory(transactionType: TransactionType, categoryId: Long, dateStart: Long, dateEnd : Long) : List<TransactionListClass>
    {
        return databaseDao.getTransactionsAllAccountWithCategory(transactionType,categoryId,dateStart,dateEnd)
    }

    suspend fun getTransactionsWithAccountAllCategory(transactionType: TransactionType, accountId: Long, dateStart: Long, dateEnd : Long) : List<TransactionListClass>
    {
        return databaseDao.getTransactionsWithAccountAllCategory(transactionType,accountId,dateStart,dateEnd)
    }

    suspend fun getTransactionsWithAccountWithCategory(transactionType: TransactionType, accountId: Long, categoryId: Long, dateStart: Long, dateEnd : Long) : List<TransactionListClass>
    {
        return databaseDao.getTransactionsWithAccountWithCategory(transactionType,accountId,categoryId,dateStart,dateEnd)
    }

    suspend fun getAccountSummary() : List<CategoryListData>
    {
        return databaseDao.getAccountSummary()
    }

    fun getCategorySummary(dateStart: Long, dateEnd: Long) : List<CategoryQueryData>
    {
        return databaseDao.getCategorySummary(dateStart,dateEnd)
    }

    fun getIncomeExpenseSummary(dateStart: Long, dateEnd: Long) : IncomeExpenseData
    {
        return databaseDao.getIncomeExpenseSummary(dateStart,dateEnd)
    }
}