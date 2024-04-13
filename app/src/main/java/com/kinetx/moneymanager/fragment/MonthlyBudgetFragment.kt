package com.kinetx.moneymanager.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentMonthlyBudgetBinding
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.recyclerview.CategoryBudgetRV
import com.kinetx.moneymanager.viewmodel.MonthlyBudgetViewModel
import com.kinetx.moneymanager.viewmodelfactory.MonthlyBudgetViewModelFactory


class MonthlyBudgetFragment : Fragment(), CategoryBudgetRV.OnSelectCategoryBudgetListener {

    lateinit var binding : FragmentMonthlyBudgetBinding
    lateinit var viewModel: MonthlyBudgetViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val viewModelFactory = MonthlyBudgetViewModelFactory(application)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_monthly_budget,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory)[MonthlyBudgetViewModel::class.java]

        binding.monthlyBudgetViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = CategoryBudgetRV(this)
        binding.monthlyBudgetRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.monthlyBudgetRecyclerView.setHasFixedSize(true)
        binding.monthlyBudgetRecyclerView.adapter = adapter


        viewModel.categorySummaryQuery.observe(viewLifecycleOwner)
        {
            viewModel.updateRecyclerView(it)
        }

        viewModel.categorySummary.observe(viewLifecycleOwner)
        {
            adapter.setData(it)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onSelectCategoryBudgetClick(position: Int) {
        val categoryId  = viewModel.categorySummary.value?.get(position)?.categoryId!!
        val dateStart = viewModel.myCalendarStart
        val dateEnd= viewModel.myCalendarEnd
        view?.findNavController()?.navigate(MonthlyBudgetFragmentDirections.actionMonthlyBudgetFragmentToTransactionListFragment(TransactionType.EXPENSE,-1,categoryId,dateStart.timeInMillis,dateEnd.timeInMillis))
    }

    override fun onResume() {
        super.onResume()
        viewModel.databaseQuery()
    }
}