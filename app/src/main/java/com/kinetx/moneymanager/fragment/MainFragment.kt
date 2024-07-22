package com.kinetx.moneymanager.fragment

import android.icu.util.Calendar
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
import com.kinetx.moneymanager.databinding.FragmentMainBinding
import com.kinetx.moneymanager.dataclass.TransactionChildList
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.helpers.DateManipulation
import com.kinetx.moneymanager.recyclerview.TransactionChildRV
import com.kinetx.moneymanager.viewmodel.MainViewModel
import com.kinetx.moneymanager.viewmodelfactory.MainViewModelFactory


class MainFragment : Fragment(), TransactionChildRV.TransactionChildListener {

    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val viewModelFactory = MainViewModelFactory(application)

        // Inflate the layout for this fragment
        val binding : FragmentMainBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_main, container, false)

        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)


        binding.mainViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        binding.mainAddExpenseCard.setOnClickListener()
        {
            view?.findNavController()?.navigate(
                MainFragmentDirections.actionMainFragmentToAddTransactionFragment(
                    TransactionType.EXPENSE,-1
                )
            )
        }

        binding.mainAddIncomeCard.setOnClickListener()
        {
            view?.findNavController()?.navigate(
                MainFragmentDirections.actionMainFragmentToAddTransactionFragment(
                    TransactionType.INCOME,-1
                )
            )
        }

        binding.mainAddTransferCard.setOnClickListener()
        {
            view?.findNavController()?.navigate(
                MainFragmentDirections.actionMainFragmentToAddTransactionFragment(
                    TransactionType.TRANSFER,-1
                )
            )
        }



        val adapter = TransactionChildRV(this)
        binding.mainLatestTransactions.layoutManager = LinearLayoutManager(context)
        binding.mainLatestTransactions.setHasFixedSize(true)
        binding.mainLatestTransactions.adapter = adapter

        viewModel.latestTransactions.observe(viewLifecycleOwner)
        {
            adapter.setData(it)
        }

        viewModel.fragmentTitle.observe(viewLifecycleOwner)
        {
            (activity as AppCompatActivity).supportActionBar?.title = it
        }

        viewModel.incomeExpenseQuery.observe(viewLifecycleOwner)
        {
            viewModel.updateIncomeExpense(it)
        }

        viewModel.readAllAccounts.observe(viewLifecycleOwner)
        {
            viewModel.databaseQueries(it)
        }




        return binding.root
    }

    override fun onResume() {
        super.onResume()
        var myCalendar : Calendar = Calendar.getInstance()
        myCalendar = DateManipulation.resetToMidnight(myCalendar)
        viewModel.updateIncomeExpenseQuery(myCalendar)
        viewModel.updateCurrency()
    }

    override fun onLongClickTransactionChild(position: Long, transactionType: TransactionType) {
        view?.findNavController()?.navigate(MainFragmentDirections.actionMainFragmentToAddTransactionFragment(transactionType,position))
    }
}