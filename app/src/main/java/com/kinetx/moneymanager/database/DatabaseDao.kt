package com.kinetx.moneymanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kinetx.moneymanager.dataclass.CategoryListData
import com.kinetx.moneymanager.dataclass.CategoryQueryData
import com.kinetx.moneymanager.dataclass.IncomeExpenseData
import com.kinetx.moneymanager.dataclass.TransactionListClass
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
    suspend fun getCategoryByName(categoryName: String) : CategoryDatabase?
    @Query("SELECT category_name FROM CATEGORY_TABLE where categoryId= :categoryId")
    suspend fun getCategoryNameById(categoryId: Long) : String



    @Insert
    suspend fun insertTransaction(transaction: TransactionDatabase)
    @Update
    suspend fun updateTransaction(transaction: TransactionDatabase)
    @Delete
    suspend fun deleteTransaction(transaction: TransactionDatabase)


    @Query("DELETE FROM transaction_table WHERE category_one=:categoryId OR category_two=:categoryId")
    suspend fun deleteTransactionsOfCategory(categoryId: Long)


    @Query("WITH A AS (SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, transaction_type, date, comments FROM transaction_table WHERE date >=:dateStart AND date <=:dateEnd) SELECT A.transactionId, A.category_name categoryOne,B.category_name categoryTwo,C.transaction_type transactionType, C.comments,C.date,C.amount, B.category_image_string categoryImageString, B.category_color categoryColor FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getAllTransactions( dateStart: Long, dateEnd : Long ): List<TransactionListClass>

    @Query("SELECT * FROM transaction_table WHERE transactionId=:transactionId")
    suspend fun getTransactionById(transactionId:Long) : TransactionDatabase?
    @Query("SELECT * FROM transaction_table WHERE category_one=:categoryId AND transaction_type='BALANCE'")
    suspend fun getTransactionInitialBalance(categoryId: Long) : TransactionDatabase?

    @Query("WITH A AS (SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, transaction_type, date, comments FROM transaction_table WHERE transaction_type=:transactionType AND date >=:dateStart AND date <=:dateEnd) SELECT A.transactionId, A.category_name categoryOne,B.category_name categoryTwo,C.transaction_type transactionType, C.comments,C.date,C.amount, B.category_image_string categoryImageString, B.category_color categoryColor FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getTransactionsAllAccountsAllCategories(transactionType: TransactionType, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>

    @Query("WITH A AS (SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, transaction_type, date, comments FROM transaction_table WHERE transaction_type=:transactionType AND category_two=:categoryId  AND date >=:dateStart AND date <=:dateEnd) SELECT A.transactionId, A.category_name categoryOne,B.category_name categoryTwo,C.transaction_type transactionType,C.comments,C.date,C.amount, B.category_image_string categoryImageString, B.category_color categoryColor FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getTransactionsAllAccountWithCategory(transactionType: TransactionType, categoryId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>

    @Query("WITH A AS (SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, transaction_type, date, comments FROM transaction_table WHERE transaction_type=:transactionType AND category_two=:categoryId  AND date >=:dateStart AND date <=:dateEnd) SELECT A.transactionId, A.category_name categoryOne,B.category_name categoryTwo,C.transaction_type transactionType,C.comments,C.date,C.amount, B.category_image_string categoryImageString, B.category_color categoryColor FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    suspend fun getTransactionsAllAccountWithCategoryList(transactionType: TransactionType, categoryId: Long, dateStart: Long, dateEnd : Long) : List<TransactionListClass>

    @Query("WITH A AS (SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND category_one=:accountId AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND category_one=:accountId AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, transaction_type,date, comments FROM transaction_table WHERE transaction_type=:transactionType AND category_one=:accountId  AND date >=:dateStart AND date <=:dateEnd) SELECT A.transactionId, A.category_name categoryOne,B.category_name categoryTwo,C.transaction_type transactionType,C.comments,C.date,C.amount, B.category_image_string categoryImageString, B.category_color categoryColor FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getTransactionsWithAccountAllCategory(transactionType: TransactionType, accountId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>

    @Query("WITH A AS (SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one AND transaction_type=:transactionType AND category_one=:accountId AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), B AS(SELECT category_name, transactionId, category_image_string, category_color FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two AND transaction_type=:transactionType AND category_one=:accountId AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd), C AS (SELECT transactionId, amount, transaction_type, date, comments FROM transaction_table WHERE transaction_type=:transactionType AND category_one=:accountId AND category_two=:categoryId AND date >=:dateStart AND date <=:dateEnd) SELECT A.transactionId, A.category_name categoryOne,B.category_name categoryTwo,C.transaction_type transactionType,C.comments,C.date,C.amount, B.category_image_string categoryImageString, B.category_color categoryColor FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")
    fun getTransactionsWithAccountWithCategory(transactionType: TransactionType, accountId: Long, categoryId: Long, dateStart: Long, dateEnd : Long) : LiveData<List<TransactionListClass>>


    //@Query("WITH A AS (SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_one), B AS(SELECT category_name, transactionId FROM category_table C, transaction_table D WHERE C.categoryId=D.category_two), C AS (SELECT transactionId, amount, date, comments FROM transaction_table) SELECT A.transactionId, A.category_name categoryOne,B.category_name categoryTwo,C.comments,C.date,C.amount FROM A,B,C WHERE A.transactionId=B.transactionId AND A.transactionId=C.transactionId")\
    @Query("WITH Z AS (SELECT categoryId,category_name,category_image,category_color FROM category_table WHERE category_type='ACCOUNT')," +
            "A AS (SELECT SUM(amount) AS a, category_name AS b FROM transaction_table JOIN Z ON transaction_table.category_one=Z.categoryId WHERE transaction_type='INCOME' GROUP BY category_name)," +
            "B AS (SELECT SUM(amount) AS a, category_name AS b FROM transaction_table JOIN Z ON transaction_table.category_one=Z.categoryId WHERE transaction_type='EXPENSE' GROUP BY category_name)," +
            "C AS (SELECT SUM(amount) AS a, category_name AS b FROM transaction_table JOIN Z ON transaction_table.category_one=Z.categoryId WHERE transaction_type='TRANSFER' GROUP BY category_name)," +
            "D AS (SELECT SUM(amount) AS a, category_name AS b FROM transaction_table JOIN Z ON transaction_table.category_two=Z.categoryId WHERE transaction_type='TRANSFER' GROUP BY category_name)," +
            "DD AS (SELECT SUM(amount) AS e,  category_name AS m FROM transaction_table JOIN Z ON transaction_table.category_one=Z.categoryId WHERE transaction_type='BALANCE' GROUP BY category_name),"+
            "E AS (SELECT A.b as m, A.a AS a, B.a AS b FROM A LEFT JOIN B ON A.b = B.b UNION SELECT B.b as m, A.a AS a, B.a AS b FROM B LEFT JOIN A ON A.b = B.b),\n" +
            "F AS (SELECT C.b as m, C.a AS c, D.a AS d FROM C LEFT JOIN D ON C.b = D.b UNION SELECT D.b as m, C.a AS c, D.a AS d FROM D LEFT JOIN C ON C.b = D.b),\n" +
            "K AS (SELECT E.m, E.a,E.b,F.c,F.d FROM E LEFT JOIN F ON E.m=F.m UNION SELECT F.m, E.a,E.b,F.c,F.d FROM F LEFT JOIN E ON E.m=F.m ),"+
            "L AS (SELECT K.m,K.a,K.b,K.c,K.d, DD.e FROM K LEFT JOIN DD on K.m=DD.m UNION SELECT DD.m,K.a,K.b,K.c,K.d, DD.e FROM DD LEFT JOIN K ON K.m=DD.m)"+
            "SELECT L.m AS categoryName,Z.categoryId, Z.category_image AS categoryImage, Z.category_color AS categoryColor, IFNULL(L.e,0)+IFNULL(L.a,0)-IFNULL(L.b,0)-IFNULL(L.c,0)+IFNULL(L.d,0) AS amount FROM L,Z WHERE L.m=Z.category_name")
    suspend fun getAccountSummary() : List<CategoryListData>

    @Query("SELECT c.category_name AS categoryName, c.categoryId ,c.category_image_string AS categoryImage, c.category_color AS categoryColor, c.category_budget AS budget , SUM(d.amount) AS amount FROM transaction_table d JOIN category_table c on d.category_two=c.categoryId AND c.category_type='EXPENSE' WHERE d.transaction_type='EXPENSE' AND d.date BETWEEN :dateStart AND :dateEnd GROUP BY c.category_name")
    fun getCategorySummary(dateStart: Long, dateEnd : Long) : List<CategoryQueryData>

    @Query("SELECT SUM(CASE WHEN transaction_type='INCOME' THEN amount ELSE 0 END) AS income,SUM(CASE WHEN transaction_type='EXPENSE' THEN amount ELSE 0 END) AS expense FROM transaction_table WHERE date BETWEEN :dateStart AND :dateEnd")
    fun getIncomeExpenseSummary(dateStart: Long, dateEnd : Long): IncomeExpenseData



    @Query("SELECT * FROM balance_table WHERE account_id=:accountId ORDER BY month_end DESC LIMIT 1")
    suspend fun getLatestBalanceWithAccount(accountId: Long) : BalanceDatabase

    @Insert
    suspend fun insertBalanceWithAccount(balanceDatabase: BalanceDatabase)

    @Query("SELECT * FROM transaction_table WHERE transaction_type!='BALANCE' AND (category_one=:accountId OR category_two=:accountId) ORDER BY date LIMIT 1")
    suspend fun getFirstTransactionWithAccount(accountId: Long) : TransactionDatabase?


    @Query("SELECT * FROM transaction_table WHERE date<=:dateEnd AND date>=:dateStart AND (category_one=:accountId OR category_two=:accountId)")
    suspend fun getTransactionsWithAccountForDate(accountId: Long,dateStart: Long,dateEnd: Long) : List<TransactionDatabase>?

    @Query("DELETE FROM balance_table WHERE account_id=:accountId")
    suspend fun deleteAllBalanceEntriesWithAccount(accountId: Long)

    @Query("DELETE FROM balance_table WHERE account_id=:accountId AND month_end>=:date")
    suspend fun deleteBalanceEntriesWithAccountAfterDate(accountId: Long, date: Long)


    /// New and updated queries
    @Query("SELECT * FROM transaction_table WHERE (category_one=:accountId OR category_two=:accountId) AND date>=:dateStart AND date<=:dateEnd")
    suspend fun getAllTransactionsWithAccount(accountId: Long, dateStart: Long,dateEnd: Long) : List<TransactionDatabase>
}