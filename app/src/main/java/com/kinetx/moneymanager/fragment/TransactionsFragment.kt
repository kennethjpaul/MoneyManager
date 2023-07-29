package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentTransactionsBinding
import com.kinetx.moneymanager.viewmodel.TransactionsViewModel
import com.kinetx.moneymanager.viewmodelfactory.TransactionsViewModelFactory


class TransactionsFragment : Fragment() {


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

            // Inflate the layout for this fragment
        return binding.root
    }

}