package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kinetx.moneymanager.dataclass.CategoryListData
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
    @Query("SELECT category_name FROM category_table")
    fun getAllCategoryNames(): LiveData<List<String>>
    @Query("SELECT * FROM category_table WHERE category_type = 'INCOME'")
    fun getAllIncomeCategory(): LiveData<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table WHERE category_type =  'EXPENSE'")
    fun getAllExpenseCategory(): LiveData<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table WHERE category_type = 'ACCOUNT'")
    fun getAllAccountCategory(): LiveData<List<CategoryDatabase>>
    @Query("SELECT * FROM category_table ORDER BY categoryId DESC LIMIT 1")
    fun getLatestCategory(): CategoryDatabase?
    @Query("SELECT * FROM CATEGORY_TABLE where categoryId = :categoryId")
    suspend fun getCategory(categoryId: Long) : CategoryDatabase?
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


    @Query("SELECT * FROM transaction_table WHERE transactionId=:transactionId")
    suspend fun getTransactionById(transactionId:Long) : TransactionDatabase?
    @Query("WITH A AS (SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, date, comments FROM transaction_table WHERE transaction_type=:transactionType AND date >=:dateStart AND date <=:dateEnd) SELECT A.transactionId, A.category_name categoryOne,B.category_name categoryTwo,C.comments,C.date,C.amount FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getTransactionsAllAccountsAllCategories(transactionType: TransactionType, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>
    @Query("SELECT tt.transactionId, ct1.category_name AS categoryOne, ct2.category_name AS categoryTwo, tt.comments, tt.date, tt.amount FROM category_table ct1 JOIN transaction_table tt ON ct1.categoryId = tt.category_one OR ct1.categoryId = tt.category_two JOIN category_table ct2 ON ct2.categoryId = tt.category_two OR ct2.categoryId = tt.category_one WHERE tt.transaction_type = :transactionType AND ( ct1.categoryId = :categoryId OR ct2.categoryId = :categoryId ) AND tt.date BETWEEN :dateStart AND :dateEnd")
    fun getTransactionsAllAccountWithCategory(transactionType: TransactionType, categoryId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>
    @Query("WITH A AS (SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND category_one=:accountId AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND category_one=:accountId AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, date, comments FROM transaction_table WHERE transaction_type=:transactionType AND category_one=:accountId  AND date >=:dateStart AND date <=:dateEnd) SELECT A.transactionId, A.category_name categoryOne,B.category_name categoryTwo,C.comments,C.date,C.amount FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getTransactionsWithAccountAllCategory(transactionType: TransactionType, accountId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>
    @Query("WITH A AS (SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND category_one=:accountId AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND category_one=:accountId AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, date, comments FROM transaction_table WHERE transaction_type=:transactionType AND category_one=:accountId AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd) SELECT A.transactionId, A.category_name categoryOne,B.category_name categoryTwo,C.comments,C.date,C.amount FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getTransactionsWithAccountWithCategory(transactionType: TransactionType, accountId: Long, categoryId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>


    //@Query("WITH A AS (SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one), B AS(SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two), C AS (SELECT transactionId, amount, date, comments FROM transaction_table) SELECT A.transactionId, A.category_name categoryOne,B.category_name categoryTwo,C.comments,C.date,C.amount FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")\
    @Query("WITH Z AS (SELECT categoryId,category_name,category_image,category_color FROM category_table WHERE category_type='ACCOUNT')," +
            "A AS (SELECT SUM(amount) AS a, category_name AS b FROM transaction_table JOIN Z ON transaction_table.category_one=Z.categoryId WHERE transaction_type='INCOME' GROUP BY category_name)," +
            "B AS (SELECT SUM(amount) AS a, category_name AS b FROM transaction_table JOIN Z ON transaction_table.category_one=Z.categoryId WHERE transaction_type='EXPENSE' GROUP BY category_name)," +
            "C AS (SELECT SUM(amount) AS a, category_name AS b FROM transaction_table JOIN Z ON transaction_table.category_one=Z.categoryId WHERE transaction_type='TRANSFER' GROUP BY category_name)," +
            "D AS (SELECT SUM(amount) AS a, category_name AS b FROM transaction_table JOIN Z ON transaction_table.category_two=Z.categoryId WHERE transaction_type='TRANSFER' GROUP BY category_name)," +
            "E AS (SELECT A.b as m, A.a AS a, B.a AS b FROM A LEFT JOIN B ON A.b = B.b UNION SELECT B.b as m, A.a AS a, B.a AS b FROM B LEFT JOIN A ON A.b = B.b),\n" +
            "F AS (SELECT C.b as m, C.a AS c, D.a AS d FROM C LEFT JOIN D ON C.b = D.b UNION SELECT D.b as m, C.a AS c, D.a AS d FROM D LEFT JOIN C ON C.b = D.b),\n" +
            "K AS (SELECT E.m, E.a,E.b,F.c,F.d FROM E LEFT JOIN F ON E.m=F.m UNION SELECT F.m, E.a,E.b,F.c,F.d FROM F LEFT JOIN E ON E.m=F.m )"+
            "SELECT K.m AS categoryName, Z.category_image AS categoryImage, Z.category_color AS categoryColor, IFNULL(K.a,0)-IFNULL(K.b,0)-IFNULL(K.c,0)+IFNULL(K.d,0) AS amount FROM K,Z WHERE K.m=Z.category_name")
    fun getAccountSummary() : LiveData<List<CategoryListData>>
}