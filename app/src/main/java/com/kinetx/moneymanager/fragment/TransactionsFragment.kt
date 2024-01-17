package com.kinetx.moneymanager.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentTransactionsBinding
import com.kinetx.moneymanager.dataclass.TransactionChildList
import com.kinetx.moneymanager.dataclass.TransactionParentList
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.helpers.CommonOperations
import com.kinetx.moneymanager.recyclerview.TransactionListAdapter
import com.kinetx.moneymanager.recyclerview.TransactionParentRV
import com.kinetx.moneymanager.viewmodel.TransactionsViewModel
import com.kinetx.moneymanager.viewmodelfactory.TransactionsViewModelFactory


class TransactionsFragment : Fragment(), TransactionParentRV.TransactionParentListener {


    lateinit var binding : FragmentTransactionsBinding
    lateinit var viewModel: TransactionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val viewModelFactory = TransactionsViewModelFactory(application)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_transactions,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory)[TransactionsViewModel::class.java]

        binding.transactionsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val adapter = TransactionParentRV(this)
        binding.transactionsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.transactionsRecyclerView.setHasFixedSize(true)
        binding.transactionsRecyclerView.adapter = adapter




        binding.transactionsDateStart.setOnClickListener()
        {
            viewModel.dateStartPick(it)
        }

        binding.transactionsDateEnd.setOnClickListener()
        {
            viewModel.dateEndPick(it)
        }

        viewModel.fragmentTitle.observe(viewLifecycleOwner)
        {
            (activity as AppCompatActivity).supportActionBar?.title = it
        }



        viewModel.listRoomDatabase.observe(viewLifecycleOwner)
        {
            val data = it.groupBy { it.date }.map {
            TransactionParentList(it.key, it.value.map {
                TransactionChildList(it.transactionId,it.categoryOne,it.categoryTwo,it.transactionType,it.comments,it.amount,
                    CommonOperations.getResourceInt(application,it.categoryImageString),it.categoryColor)
            }, it.value.filter { it.transactionType==TransactionType.EXPENSE }.map { it.amount }.sum() - it.value.filter { it.transactionType==TransactionType.INCOME }.map { it.amount }.sum() )
        }.sortedByDescending {
            it.date
        }
            adapter.setData(data)
        }


            // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateTransactions()
    }

    override fun onLongClickTransactionParent(position: Long, transactionType: TransactionType) {
        view?.findNavController()?.navigate(TransactionsFragmentDirections.actionTransactionsFragmentToAddTransactionFragment(transactionType,position))
    }

}