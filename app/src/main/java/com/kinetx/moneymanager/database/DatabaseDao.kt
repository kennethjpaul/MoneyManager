package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DatabaseDao {

    @Insert
    fun insertCategory(category: CategoryDatabase)
    @Update
    fun updateCategory(category: CategoryDatabase)
    @Delete
    fun deleteCategory(category: CategoryDatabase)
    @Query("SELECT * FROM category_table")
    fun getAllCategory(): LiveData<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table WHERE category_type = 'income'")
    fun getAllIncomeCategory(): LiveData<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table WHERE category_type = 'expense'")
    fun getAllExpenseCategory(): LiveData<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table WHERE category_type = 'account'")
    fun getAllAccountCategory(): LiveData<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table ORDER BY categoryId DESC LIMIT 1")
    fun getLatestCategory(): CategoryDatabase?



    @Insert
    fun insertAccount(account : AccountDatabase)
    @Update
    fun updateAccount(account: AccountDatabase)
    @Delete
    fun deleteAccount(account: AccountDatabase)
    @Query("SELECT * FROM account_table")
    fun getAllAccounts(): LiveData<List<AccountDatabase>>
    @Query("SELECT * FROM account_table ORDER BY accountId DESC LIMIT 1")
    fun getLatestAccount(): AccountDatabase?


}