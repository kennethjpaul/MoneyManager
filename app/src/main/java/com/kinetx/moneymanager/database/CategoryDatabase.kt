package com.kinetx.moneymanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kinetx.moneymanager.enums.CategoryType

@Entity(tableName = "category_table")
data class CategoryDatabase(

    @PrimaryKey(autoGenerate = true)
    var categoryId : Long = 0L,

    @ColumnInfo(name="category_name")
    var categoryName : String = "",

    @ColumnInfo(name="category_type")
    var categoryType : CategoryType= CategoryType.INCOME,

    @ColumnInfo(name = "category_image")
    var categoryImage : Int = 0,

    @ColumnInfo(name = "category_color")
    var categoryColor : Int = 0
)
