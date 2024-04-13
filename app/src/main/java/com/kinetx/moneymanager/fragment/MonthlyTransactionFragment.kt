package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentMonthlyTransactionBinding
import com.kinetx.moneymanager.viewmodel.MonthlyTransactionViewModel
import com.kinetx.moneymanager.viewmodelfactory.MonthlyTransactionViewModelFactory


class MonthlyTransactionFragment : Fragment() {

    lateinit var binding : FragmentMonthlyTransactionBinding
    lateinit var viewModel: MonthlyTransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val viewModelFactory = MonthlyTransactionViewModelFactory(application)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_monthly_transaction,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory)[MonthlyTransactionViewModel::class.java]

        binding.monthlyTransactionViewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Inflate the layout for this fragment
        return binding.root
    }

}