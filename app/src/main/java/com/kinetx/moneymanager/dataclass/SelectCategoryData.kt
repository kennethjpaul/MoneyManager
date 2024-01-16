package com.kinetx.moneymanager.dataclass

import androidx.room.ColumnInfo
import com.kinetx.moneymanager.enums.CategoryType

data class SelectCategoryData(
    var categoryId : Long = 0L,
    var categoryName : String = "",
    var categoryType : CategoryType = CategoryType.INCOME,
    var categoryImage : Int = 0,
    var categoryImageString : Int = 0,
    var categoryColor : Int = 0
)
