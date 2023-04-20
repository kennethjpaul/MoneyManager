package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentBalancesBinding
import com.kinetx.moneymanager.viewmodel.BalancesViewModel
import com.kinetx.moneymanager.viewmodelfactory.BalancesViewModelFactory


class BalancesFragment : Fragment() {

    lateinit var binding :FragmentBalancesBinding
    lateinit var viewModel: BalancesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val application = requireNotNull(this.activity).application
        val viewModelFactory = BalancesViewModelFactory(application)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_balances,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory).get(BalancesViewModel::class.java)

        binding.balanceViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.fragmentTitle.observe(viewLifecycleOwner){
            (activity as AppCompatActivity).supportActionBar?.title =it
        }

        return binding.root
    }

}