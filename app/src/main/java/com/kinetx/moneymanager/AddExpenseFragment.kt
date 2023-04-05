package com.kinetx.moneymanager

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kinetx.moneymanager.databinding.FragmentAddExpenseBinding


class AddExpenseFragment : Fragment()
{


    lateinit var binding: FragmentAddExpenseBinding
    private lateinit var viewModel : AddExpenseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = "Add Expense"


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense, container, false)
        viewModel = ViewModelProvider(this).get(AddExpenseViewModel::class.java)

        binding.addExpenseViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setFragmentResultListener("SelectCategory")
        {key, bundle ->
            val varBgColor = bundle.getInt("color")
            val varType = bundle.getString("category")
            val varImgId = bundle.getInt("imgId")
            val varId = bundle.getLong("id")

            when(varType)
            {
                "account" -> viewModel.updateAccount(varId,varImgId,varBgColor)
                "category" -> viewModel.updateCategory(varId,varImgId,varBgColor)
            }

            Toast.makeText(activity,"The $varType is updated an ID $varId and with bg : $varImgId with $varBgColor ", Toast.LENGTH_LONG).show()
        }

        binding.addExpenseAddCategoryBtn.setOnClickListener()
        {
            val transactionType: String = "expense"
            val actionType: String = "category"
            view?.findNavController()?.navigate(
                AddExpenseFragmentDirections.actionAddExpenseFragmentToSelectCategoryFragment(
                    transactionType,
                    actionType
                )
            )
        }

        binding.addExpenseAddAccountBtn.setOnClickListener()
        {
            val transactionType: String = "expense"
            val actionType: String = "account"
            view?.findNavController()?.navigate(
                AddExpenseFragmentDirections.actionAddExpenseFragmentToSelectCategoryFragment(
                    transactionType,
                    actionType
                )
            )
        }

        binding.addExpenseAddDateBtn.setOnClickListener()
        {
            viewModel.datePick(it)
        }

        viewModel.accountImageButton.observe(viewLifecycleOwner){ data ->
            binding.addExpenseAddAccountBtn.setBackgroundResource(data.bgColor)
            binding.addExpenseAddAccountBtn.setImageResource(data.imageId)
        }

        viewModel.categoryImageButton.observe(viewLifecycleOwner){data ->
            binding.addExpenseAddCategoryBtn.setBackgroundResource(data.bgColor)
            binding.addExpenseAddCategoryBtn.setImageResource(data.imageId)
        }
        return binding.root
    }

}