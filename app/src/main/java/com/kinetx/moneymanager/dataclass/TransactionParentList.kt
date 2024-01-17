package com.kinetx.moneymanager.dataclass

data class TransactionParentList(val date : Long, val transactionChildList: List<TransactionChildList>, val amount : Float)