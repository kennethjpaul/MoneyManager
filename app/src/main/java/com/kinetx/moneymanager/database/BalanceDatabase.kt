package com.kinetx.moneymanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balance_table")
data class BalanceDatabase(
    @PrimaryKey(autoGenerate = true)
    var entryId: Long =0L,

    @ColumnInfo(name = "account_id")
    var accountId: Long = 0L,

    @ColumnInfo(name = "month_end")
    var monthEnd : Long = 0L,

    @ColumnInfo(name = "balance")
    var balance : Double = 0.0
)
