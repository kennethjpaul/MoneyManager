package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface DatabaseDao {

    @Insert
    suspend fun insertCategory(category: CategoryDatabase)
    @Update
    suspend fun updateCategory(category: CategoryDatabase)
    @Delete
    suspend fun deleteCategory(category: CategoryDatabase)

    @Query("SELECT * FROM category_table")
    fun getAllCategory(): LiveData<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table WHERE category_type = 'INCOME'")
    fun getAllIncomeCategory(): LiveData<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table WHERE category_type =  'EXPENSE'")
    fun getAllExpenseCategory(): LiveData<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table WHERE category_type = 'ACCOUNT'")
    fun getAllAccountCategory(): LiveData<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table ORDER BY categoryId DESC LIMIT 1")
    fun getLatestCategory(): CategoryDatabase?
    @Query("SELECT * FROM CATEGORY_TABLE where categoryId = :categoryId")
    fun getCategory(categoryId: Long) : CategoryDatabase?




    @Insert
    suspend fun insertTransaction(transaction: TransactionDatabase)
    @Update
    suspend fun updateTransaction(transaction: TransactionDatabase)
    @Delete
    suspend fun deleteTransaction(transaction: TransactionDatabase)

    @Query("SELECT * FROM transaction_table")
    fun getAllTransaction() : LiveData<List<TransactionDatabase>>

}