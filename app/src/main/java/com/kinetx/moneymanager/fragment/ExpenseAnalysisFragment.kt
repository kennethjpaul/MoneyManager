package com.kinetx.moneymanager.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentExpenseAnalysisBinding
import com.kinetx.moneymanager.viewmodel.ExpenseAnalysisVM
import com.kinetx.moneymanager.viewmodelfactory.ExpenseAnalysisVMF


class ExpenseAnalysisFragment : Fragment() {

    lateinit var binding : FragmentExpenseAnalysisBinding
    lateinit var viewModel : ExpenseAnalysisVM


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ExpenseAnalysisVMF(application)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_expense_analysis,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory)[ExpenseAnalysisVM::class.java]

        binding.expenseAnalysisVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner




        viewModel.readAllExpenseCategory.observe(viewLifecycleOwner)
        {
            viewModel.updateSpinner(it)
        }


        viewModel.categorySpinnerEntries.observe(viewLifecycleOwner)
        {
            binding.expenseAnalysisSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.updateSelectedCategory(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }


        viewModel.transactionList.observe(viewLifecycleOwner)
        {
            viewModel.updateData(it)

        }

        // Inflate the layout for this fragment
        return binding.root
    }

}