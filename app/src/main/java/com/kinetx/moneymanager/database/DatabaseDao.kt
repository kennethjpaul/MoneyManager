package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {

    @Insert
    suspend fun insertCategory(category: CategoryDatabase)
    @Update
    suspend fun updateCategory(category: CategoryDatabase)
    @Delete
    fun deleteCategory(category: CategoryDatabase)

    @Query("SELECT * FROM category_table")
    fun getAllCategory(): Flow<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table WHERE category_type = 'income'")
    fun getAllIncomeCategory(): Flow<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table WHERE category_type = 'expense'")
    fun getAllExpenseCategory(): Flow<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table WHERE category_type = 'account'")
    fun getAllAccountCategory(): Flow<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table ORDER BY categoryId DESC LIMIT 1")
    fun getLatestCategory(): CategoryDatabase?

}