package com.kinetx.moneymanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class TransactionDatabase(

    @PrimaryKey(autoGenerate = true)
    val transactionId : Long = 0L,

    @ColumnInfo(name = "amount")
    val transactionAmount : Float = 0.0f,

    @ColumnInfo(name="category_one")
    val transactionCategoryOne : Long = 0L,

    @ColumnInfo(name="category_two")
    val transactionCategoryTwo : Long = 0L,

    @ColumnInfo(name="date")
    val transactionDate : Int = 0,

    @ColumnInfo(name="comments")
    val transactionComment : String =""
)
