package com.kinetx.moneymanager.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.fragment.AccountTransactionFragmentArgs
import com.kinetx.moneymanager.viewmodel.AccountTransactionsVM

class AccountTransactionsVMF(val application: Application, val argList: AccountTransactionFragmentArgs): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountTransactionsVM::class.java))
        {
            return AccountTransactionsVM(application, argList) as T
        }
        throw IllegalArgumentException("AccountTransactionVMF: Unknown view model")
    }
}