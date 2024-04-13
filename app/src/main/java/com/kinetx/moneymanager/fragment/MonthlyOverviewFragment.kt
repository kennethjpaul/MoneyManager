package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentMonthlyOverviewBinding
import com.kinetx.moneymanager.recyclerview.AccountListRV
import com.kinetx.moneymanager.viewmodel.MonthlyOverviewVM
import com.kinetx.moneymanager.viewmodelfactory.MonthlyOverviewVMF


class MonthlyOverviewFragment : Fragment() {

    lateinit var binding : FragmentMonthlyOverviewBinding
    lateinit var viewModel : MonthlyOverviewVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val viewModelFactory = MonthlyOverviewVMF(application)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_monthly_overview,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory)[MonthlyOverviewVM::class.java]

        binding.monthlyOverviewVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = AccountListRV()
        binding.monthlyOverviewRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.monthlyOverviewRecyclerView.setHasFixedSize(true)
        binding.monthlyOverviewRecyclerView.adapter = adapter


        viewModel.transactionQuery.observe(viewLifecycleOwner)
        {
           adapter.setData(it)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}