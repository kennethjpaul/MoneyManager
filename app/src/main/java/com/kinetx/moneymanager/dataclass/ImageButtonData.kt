package com.kinetx.moneymanager.dataclass

import com.kinetx.moneymanager.enums.CategoryType

data class ImageButtonData(var buttonId : Long, var buttonImage : Int, var buttonColor : Int, var buttonTitle: String, var buttonType : CategoryType)
