package com.kinetx.moneymanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kinetx.moneymanager.enums.TransactionType

@Entity(tableName = "transaction_table")
data class TransactionDatabase(

    @PrimaryKey(autoGenerate = true)
    val transactionId : Long = 0L,

    @ColumnInfo(name = "amount")
    val transactionAmount : Double = 0.0,

    @ColumnInfo(name="transaction_type")
    val transactionType : TransactionType,

    @ColumnInfo(name="category_one")
    var transactionCategoryOne : Long = 0L,

    @ColumnInfo(name="category_two")
    var transactionCategoryTwo : Long = 0L,

    @ColumnInfo(name="date")
    val transactionDate : Long = 0,

    @ColumnInfo(name="comments")
    val transactionComment : String =""
)
