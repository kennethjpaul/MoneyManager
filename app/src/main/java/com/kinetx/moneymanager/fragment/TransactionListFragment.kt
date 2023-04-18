package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentTransactionListBinding
import com.kinetx.moneymanager.recyclerview.TransactionListAdapter
import com.kinetx.moneymanager.viewmodel.TransactionListViewModel
import com.kinetx.moneymanager.viewmodelfactory.TransactionListViewModelFactory


class TransactionListFragment : Fragment(), TransactionListAdapter.OnTransactionListListener {

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



        val adapter = TransactionListAdapter(this)
        binding.transactionListRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.transactionListRecyclerview.setHasFixedSize(true)
        binding.transactionListRecyclerview.adapter = adapter

        binding.lifecycleOwner = viewLifecycleOwner
        binding.transactionListViewModel = viewModel


        viewModel.readAllTransaction.observe(viewLifecycleOwner)
        {
            adapter.setData(it)
        }

        return binding.root
    }

    override fun onTransactionListLonClick(position: Int) {
        Toast.makeText(context, "Long Click", Toast.LENGTH_SHORT).show()
    }

}