package com.kinetx.moneymanager.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentAccountTransactionBinding
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.recyclerview.TransactionParentRV
import com.kinetx.moneymanager.viewmodel.AccountTransactionsVM
import com.kinetx.moneymanager.viewmodelfactory.AccountTransactionsVMF


class AccountTransactionFragment : Fragment(), TransactionParentRV.TransactionParentListener {

    lateinit var binding: FragmentAccountTransactionBinding
    lateinit var viewModel : AccountTransactionsVM
    lateinit var argList : AccountTransactionFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        argList = AccountTransactionFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val viewModelFactory = AccountTransactionsVMF(application, argList)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_transaction, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory)[AccountTransactionsVM::class.java]

        binding.accountTransactionsVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = TransactionParentRV(this)
        binding.accountTransactionsRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.accountTransactionsRecyclerview.setHasFixedSize(true)
        binding.accountTransactionsRecyclerview.adapter = adapter

        viewModel.listRoomDatabase.observe(viewLifecycleOwner)
        {
            adapter.setData(it)
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onLongClickTransactionParent(position: Long, transactionType: TransactionType) {
        Log.i("III","Teest")
    }

}