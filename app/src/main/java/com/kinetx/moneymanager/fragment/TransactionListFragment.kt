package com.kinetx.moneymanager.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentTransactionListBinding
import com.kinetx.moneymanager.dataclass.TransactionChildList
import com.kinetx.moneymanager.dataclass.TransactionParentList
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.helpers.CommonOperations
import com.kinetx.moneymanager.recyclerview.TransactionListAdapter
import com.kinetx.moneymanager.recyclerview.TransactionParentRV
import com.kinetx.moneymanager.viewmodel.TransactionListViewModel
import com.kinetx.moneymanager.viewmodelfactory.TransactionListViewModelFactory


class TransactionListFragment : Fragment(), TransactionParentRV.TransactionParentListener {

    private lateinit var binding : FragmentTransactionListBinding
    private lateinit var argList : TransactionListFragmentArgs
    private lateinit var viewModel: TransactionListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        argList = TransactionListFragmentArgs.fromBundle(requireArguments())
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_transaction_list,container,false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = TransactionListViewModelFactory(argList, application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(TransactionListViewModel::class.java)



        val adapter = TransactionParentRV(this)
        binding.transactionListRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.transactionListRecyclerview.setHasFixedSize(true)
        binding.transactionListRecyclerview.adapter = adapter

        binding.lifecycleOwner = viewLifecycleOwner
        binding.transactionListViewModel = viewModel


        viewModel.listTransformed.observe(viewLifecycleOwner)
        {
            val data = it.groupBy { it.date }.map {
                TransactionParentList(it.key, it.value.map {
                    TransactionChildList(it.transactionId,it.categoryOne,it.categoryTwo,it.transactionType,it.comments,it.amount,CommonOperations.getResourceInt(application,it.categoryImageString),it.categoryColor)
                }, it.value.filter { it.transactionType==TransactionType.EXPENSE }.map { it.amount }.sum() - it.value.filter { it.transactionType==TransactionType.INCOME }.map { it.amount }.sum())
            }.sortedByDescending {
                it.date
            }
            adapter.setData(data)
        }

       viewModel.listTransactionAmount.observe(viewLifecycleOwner)
        {
            viewModel.setTotal()
        }

        viewModel.fragmentTitle.observe(viewLifecycleOwner)
        {
            (activity as AppCompatActivity).supportActionBar?.title = it
        }


        return binding.root
    }


    override fun onLongClickTransactionParent(position: Long, transactionType: TransactionType) {
        view?.findNavController()?.navigate(TransactionListFragmentDirections.actionTransactionListFragmentToAddTransactionFragment(transactionType,position))
    }

}