package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentMonthlyBudgetBinding
import com.kinetx.moneymanager.viewmodel.MonthlyBudgetViewModel
import com.kinetx.moneymanager.viewmodelfactory.MonthlyBudgetViewModelFactory


class MonthlyBudgetFragment : Fragment() {

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

        // Inflate the layout for this fragment
        return binding.root
    }


}