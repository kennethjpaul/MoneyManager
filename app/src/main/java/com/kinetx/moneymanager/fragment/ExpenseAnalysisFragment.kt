package com.kinetx.moneymanager.fragment

import android.graphics.Color
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
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
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

        binding.expenseAnalysisCombinedChart.description.isEnabled = false;
        binding.expenseAnalysisCombinedChart.setBackgroundColor(Color.WHITE);
        binding.expenseAnalysisCombinedChart.setDrawGridBackground(false);
        binding.expenseAnalysisCombinedChart.setDrawBarShadow(false);
        binding.expenseAnalysisCombinedChart.isHighlightFullBarEnabled = false;
        binding.expenseAnalysisCombinedChart.isScaleYEnabled = false

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

        viewModel.chartData.observe(viewLifecycleOwner)
        {
            binding.expenseAnalysisCombinedChart.data = it
            binding.expenseAnalysisCombinedChart.invalidate()
            Log.i("III","Here")
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}