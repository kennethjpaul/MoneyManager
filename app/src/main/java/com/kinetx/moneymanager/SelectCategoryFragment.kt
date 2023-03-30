package com.kinetx.moneymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.kinetx.moneymanager.databinding.FragmentSelectCategoryBinding


class SelectCategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argList  = SelectCategoryFragmentArgs.fromBundle(requireArguments())

        var screenTitle: String =""

        when(argList.actionType)
        {
            "category" -> screenTitle = "Select Categories"
            "account" -> screenTitle = "Select Accounts"
        }

        (activity as AppCompatActivity).supportActionBar?.title = screenTitle
        Toast.makeText(activity, "The user came from ${argList.transactionType} for the action ${argList.actionType}", Toast.LENGTH_SHORT).show()
        val binding : FragmentSelectCategoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_category, container, false)

        binding.selectCategorySelectButton.setOnClickListener()
        {
            val num = 1
            val color = "#456789"
            val id : Long =1

            when (argList.transactionType)
            {
                "income" -> view?.findNavController()?.navigate(SelectCategoryFragmentDirections.actionSelectCategoryFragmentToAddIncomeFragment(argList.actionType,num,color,id))
                "expense"->view?.findNavController()?.navigate(SelectCategoryFragmentDirections.actionSelectCategoryFragmentToAddExpenseFragment(argList.actionType,num,color,id))
            }
        }
        return binding.root
    }


}