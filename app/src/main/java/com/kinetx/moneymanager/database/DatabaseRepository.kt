package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData
import com.kinetx.moneymanager.dataclass.CategoryListData
import com.kinetx.moneymanager.dataclass.CategoryQueryData
import com.kinetx.moneymanager.dataclass.IncomeExpenseData
import com.kinetx.moneymanager.dataclass.TransactionListClass
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.fragment.TransactionListFragmentArgs

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

    fun getTransactionsAllAccountsAllCategories(transactionType :TransactionType, dateStart :Long, dateEnd: Long) : LiveData<List<TransactionListClass>>
    {
       return databaseDao.getTransactionsAllAccountsAllCategories(transactionType, dateStart, dateEnd)
    }

    fun getTransactionsAllAccountWithCategory(transactionType: TransactionType, categoryId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>
    {
        return databaseDao.getTransactionsAllAccountWithCategory(transactionType,categoryId,dateStart,dateEnd)
    }

    suspend fun getTransactionsAllAccountWithCategoryList(transactionType: TransactionType, categoryId: Long, dateStart: Long, dateEnd : Long) : List<TransactionListClass>
    {
        return databaseDao.getTransactionsAllAccountWithCategoryList(transactionType,categoryId,dateStart,dateEnd)
    }

    fun getTransactionsWithAccountAllCategory(transactionType: TransactionType, accountId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>
    {
        return databaseDao.getTransactionsWithAccountAllCategory(transactionType,accountId,dateStart,dateEnd)
    }

    fun getTransactionsWithAccountWithCategory(transactionType: TransactionType, accountId: Long, categoryId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>
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

    fun getTransactionsLogic(argList: TransactionListFragmentArgs): LiveData<List<TransactionListClass>> {
        if (argList.accountId==-1L && argList.categoryId==-1L)
        {
            return databaseDao.getTransactionsAllAccountsAllCategories(argList.transactionType,argList.dateStart,argList.dateEnd)

        }
        else if (argList.accountId==-1L)
        {
            return databaseDao.getTransactionsAllAccountWithCategory(argList.transactionType,argList.categoryId,argList.dateStart,argList.dateEnd)
        }
        else if (argList.categoryId==-1L)
        {
            return databaseDao.getTransactionsWithAccountAllCategory(argList.transactionType,argList.accountId,argList.dateStart,argList.dateEnd)
        }
        else
        {
            return databaseDao.getTransactionsWithAccountWithCategory(argList.transactionType,argList.accountId,argList.categoryId,argList.dateStart,argList.dateEnd)
        }
    }

    suspend fun insertBalanceWithAccount(balanceDatabase: BalanceDatabase)
    {
        return databaseDao.insertBalanceWithAccount(balanceDatabase)
    }

    suspend fun getLatestBalanceWithAccount(accountId: Long) : BalanceDatabase
    {
        return databaseDao.getLatestBalanceWithAccount(accountId)
    }

    suspend fun getFirstTransactionWithAccount(accountId: Long): TransactionDatabase?
    {
        return databaseDao.getFirstTransactionWithAccount(accountId)
    }

    suspend fun getTransactionsWithAccountForDate(accountId: Long, dateStart: Long, dateEnd: Long) : List<TransactionDatabase>?
    {
        return databaseDao.getTransactionsWithAccountForDate(accountId, dateStart, dateEnd)
    }

    suspend fun deleteAllBalanceEntriesWithAccount(accountId: Long)
    {
        return databaseDao.deleteAllBalanceEntriesWithAccount(accountId)
    }

    suspend fun deleteBalanceEntriesWithAccountAfterDate(accountId: Long,date: Long)
    {
        return databaseDao.deleteBalanceEntriesWithAccountAfterDate(accountId, date)
    }
}