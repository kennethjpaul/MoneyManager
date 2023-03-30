package com.kinetx.moneymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.kinetx.moneymanager.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val binding : FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.mainAddExpenseBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_addExpenseFragment)
        )

        binding.mainAddIncomeBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_addIncomeFragment)
        )

        (activity as AppCompatActivity).supportActionBar?.title = "Money Manager"
        return binding.root
    }

}