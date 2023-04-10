package com.kinetx.moneymanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_table")
data class AccountDatabase(
    @PrimaryKey(autoGenerate = true)
    var accountId : Long = 0L,

    @ColumnInfo(name="account_name")
    var accountName : String = "",

    @ColumnInfo(name = "account_image")
    var accountImage : Int = 0,

    @ColumnInfo(name = "account_color")
    var accountColor : Int = 0
)
