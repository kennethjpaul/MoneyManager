package com.kinetx.moneymanager.dataclass

import com.kinetx.moneymanager.enums.TransactionType

data class TransactionChildList(val transactionId: Long, val categoryOne : String, val categoryTwo : String, val transactionColor: Int, val comments : String, val amount : Float, val categoryImageInt: Int, val categoryColor : Int, val transactionType: TransactionType)
