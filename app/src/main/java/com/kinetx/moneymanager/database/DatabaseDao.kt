package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kinetx.moneymanager.dataclass.TransactionListClass
import com.kinetx.moneymanager.enums.CategoryType
import com.kinetx.moneymanager.enums.TransactionType

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: CategoryDatabase)
    @Update(onConflict = OnConflictStrategy.IGNORE)
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
    @Query("SELECT * FROM CATEGORY_TABLE where category_name = :categoryName")
    fun getCategoryByName(categoryName: String) : CategoryDatabase?
    @Query("SELECT category_name FROM CATEGORY_TABLE where categoryId= :categoryId")
    suspend fun getCategoryNameById(categoryId: Long) : String



    @Insert
    suspend fun insertTransaction(transaction: TransactionDatabase)
    @Update
    suspend fun updateTransaction(transaction: TransactionDatabase)
    @Delete
    suspend fun deleteTransaction(transaction: TransactionDatabase)

    @Query("WITH A AS (SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, date, comments FROM transaction_table WHERE transaction_type=:transactionType AND date >=:dateStart AND date <=:dateEnd) SELECT A.category_name categoryOne,B.category_name categoryTwo,C.comments,C.date,C.amount FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getTransactionsAllAccountsAllCategories(transactionType: TransactionType, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>
    @Query("WITH A AS (SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, date, comments FROM transaction_table WHERE transaction_type=:transactionType AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd) SELECT A.category_name categoryOne,B.category_name categoryTwo,C.comments,C.date,C.amount FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getTransactionsAllAccountWithCategory(transactionType: TransactionType, categoryId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>
    @Query("WITH A AS (SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND category_one=:accountId AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND category_one=:accountId AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, date, comments FROM transaction_table WHERE transaction_type=:transactionType AND category_one=:accountId  AND date >=:dateStart AND date <=:dateEnd) SELECT A.category_name categoryOne,B.category_name categoryTwo,C.comments,C.date,C.amount FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getTransactionsWithAccountAllCategory(transactionType: TransactionType, accountId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>
    @Query("WITH A AS (SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND category_one=:accountId AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND category_one=:accountId AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, date, comments FROM transaction_table WHERE transaction_type=:transactionType AND category_one=:accountId AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd) SELECT A.category_name categoryOne,B.category_name categoryTwo,C.comments,C.date,C.amount FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getTransactionsWithAccountWithCategory(transactionType: TransactionType, accountId: Long, categoryId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>
    @Query("WITH A AS (SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one), B AS(SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two), C AS (SELECT transactionId, amount, date, comments FROM transaction_table) SELECT A.category_name categoryOne,B.category_name categoryTwo,C.comments,C.date,C.amount FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun testingQuery() : LiveData<List<TransactionListClass>>
}