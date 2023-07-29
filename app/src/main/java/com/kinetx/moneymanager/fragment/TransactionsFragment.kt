package com.kinetx.moneymanager.fragment

import android.os.Bundle
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
import com.kinetx.moneymanager.recyclerview.TransactionListAdapter
import com.kinetx.moneymanager.viewmodel.TransactionsViewModel
import com.kinetx.moneymanager.viewmodelfactory.TransactionsViewModelFactory


class TransactionsFragment : Fragment(), TransactionListAdapter.OnTransactionListListener {


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


        val adapter = TransactionListAdapter(this)
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


        viewModel.listTransformed.observe(viewLifecycleOwner)
        {
            adapter.setData(it)
        }


            // Inflate the layout for this fragment
        return binding.root
    }

    override fun onTransactionListLonClick(position: Int) {
        val transactionId = viewModel.listTransformed.value?.get(position)?.transactionId
        val transactionType = viewModel.listTransformed.value?.get(position)?.transactionType
        view?.findNavController()?.navigate(TransactionsFragmentDirections.actionTransactionsFragmentToAddTransactionFragment(transactionType!!,transactionId!!))
    }

}