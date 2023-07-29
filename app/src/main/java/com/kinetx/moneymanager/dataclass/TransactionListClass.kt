package com.kinetx.moneymanager.dataclass

import com.kinetx.moneymanager.enums.TransactionType

data class TransactionListClass(val transactionId : Long, val categoryOne : String, val categoryTwo : String, val transactionType: TransactionType, val comments : String, val date : Long, val amount : Float )
