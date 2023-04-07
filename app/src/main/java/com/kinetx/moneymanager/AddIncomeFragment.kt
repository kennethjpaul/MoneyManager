package com.kinetx.moneymanager

import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kinetx.moneymanager.databinding.FragmentAddIncomeBinding


class AddIncomeFragment : Fragment(){

    lateinit var binding: FragmentAddIncomeBinding
    private  lateinit var viewModel : AddIncomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = "Add Income"
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_income, container, false)
        viewModel = ViewModelProvider(this).get(AddIncomeViewModel::class.java)

        binding.addIncomeViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setFragmentResultListener("SelectCategory")
        {key, bundle ->
            val varBgColor = bundle.getInt("itemColor")
            val varType = bundle.getString("category")
            val varImgId = bundle.getInt("itemImage")
            val varId = bundle.getLong("id")

            when(varType)
            {
                "account" -> viewModel.updateAccount(varId,varImgId,varBgColor)
                "category" -> viewModel.updateCategory(varId,varImgId,varBgColor)
            }

            Toast.makeText(activity,"The $varType is updated an ID $varId and with bg : $varImgId with $varBgColor ", Toast.LENGTH_LONG).show()
        }

        binding.addIncomeAddCategoryBtn.setOnClickListener()
        {
            val transactionType: String = "income"
            val actionType: String = "category"
            view?.findNavController()?.navigate(
                AddIncomeFragmentDirections.actionAddIncomeFragmentToSelectCategoryFragment(
                    transactionType,
                    actionType
                )
            )
        }

        binding.addIncomeAddAccountBtn.setOnClickListener()
        {
            val transactionType: String = "income"
            val actionType: String = "account"
            view?.findNavController()?.navigate(
                AddIncomeFragmentDirections.actionAddIncomeFragmentToSelectCategoryFragment(
                    transactionType,
                    actionType
                )
            )
        }


        binding.addIncomeAddDateBtn.setOnClickListener()
        {
            viewModel.datePick(it)
        }

        viewModel.accountImageButton.observe(viewLifecycleOwner){ data ->
            binding.addIncomeAddAccountBtn.setBackgroundResource(data.bgColor)
            binding.addIncomeAddAccountBtn.setImageResource(data.imageId)
        }

        viewModel.categoryImageButton.observe(viewLifecycleOwner){data ->
            binding.addIncomeAddCategoryBtn.setBackgroundResource(data.bgColor)
            binding.addIncomeAddCategoryBtn.setImageResource(data.imageId)
        }

        return binding.root
    }

}