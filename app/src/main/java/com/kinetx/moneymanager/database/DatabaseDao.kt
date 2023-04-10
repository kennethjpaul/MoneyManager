package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DatabaseDao {

    @Insert
    fun insertCategory(category: CategoryDatabase)

    @Insert
    fun insertAccount(account : AccountDatabase)

    @Update
    fun updateCategory(category: CategoryDatabase)

    @Update
    fun updateAccount(account: AccountDatabase)

    @Delete
    fun deleteCategory(category: CategoryDatabase)

    @Delete
    fun deleteAccount(account: AccountDatabase)

    @Query("SELECT * FROM category_table")
    fun getAllCategory(): LiveData<List<CategoryDatabase>>

    @Query("SELECT * FROM account_table")
    fun getAllAccounts(): LiveData<List<AccountDatabase>>

    @Query("SELECT * FROM category_table ORDER BY categoryId DESC LIMIT 1")
    fun getLatestCategory(): CategoryDatabase?

    @Query("SELECT * FROM account_table ORDER BY accountId DESC LIMIT 1")
    fun getLatestAccount(): AccountDatabase?


}