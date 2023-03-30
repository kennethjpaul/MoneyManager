package com.kinetx.moneymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.kinetx.moneymanager.databinding.FragmentAddExpenseBinding


class AddExpenseFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = "Add Expense"
        val binding : FragmentAddExpenseBinding  = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense, container, false)
        binding.addExpenseAddCategoryBtn.setOnClickListener()
        {
            val transactionType : String = "Expense"
            val actionType : String ="category"
            view?.findNavController()?.navigate(AddExpenseFragmentDirections.actionAddExpenseFragmentToSelectCategoryFragment(transactionType,actionType))
        }

        binding.addExpenseAddAccountBtn.setOnClickListener()
        {
            val transactionType : String = "Expense"
            val actionType : String ="account"
            view?.findNavController()?.navigate(AddExpenseFragmentDirections.actionAddExpenseFragmentToSelectCategoryFragment(transactionType,actionType))
        }
        return binding.root
    }


}