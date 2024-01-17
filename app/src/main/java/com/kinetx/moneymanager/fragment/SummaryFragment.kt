package com.kinetx.moneymanager.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.databinding.FragmentSummaryBinding
import com.kinetx.moneymanager.dataclass.CategorySummaryListData
import com.kinetx.moneymanager.enums.TransactionType
import com.kinetx.moneymanager.helpers.CommonOperations
import com.kinetx.moneymanager.recyclerview.CategoryListAdapter
import com.kinetx.moneymanager.viewmodel.SummaryViewModel
import com.kinetx.moneymanager.viewmodelfactory.SummaryViewModelFactory


class SummaryFragment : Fragment(), CategoryListAdapter.OnSelectCategoryListListener {

    lateinit var binding : FragmentSummaryBinding
    lateinit var viewModel : SummaryViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val application = requireNotNull(this.activity).application
        val viewModelFactory = SummaryViewModelFactory(application)


        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_summary,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SummaryViewModel::class.java)


        binding.summaryViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val adapter = CategoryListAdapter(this)
        binding.summaryRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.summaryRecyclerview.setHasFixedSize(true)
        binding.summaryRecyclerview.adapter = adapter



        binding.summaryAdvanceLeftBtn.setOnClickListener()
        {
            val scope = binding.summaryScopeRadioGroup.checkedRadioButtonId
            viewModel.changeDate(-1,scope)
        }

        binding.summaryAdvanceRightBtn.setOnClickListener()
        {
            val scope = binding.summaryScopeRadioGroup.checkedRadioButtonId
            viewModel.changeDate(1,scope)
        }





        viewModel.fragmentTitle.observe(viewLifecycleOwner){
            (activity as AppCompatActivity).supportActionBar?.title =it
        }

        viewModel.incomeExpenseQuery.observe(viewLifecycleOwner){
            viewModel.updateIncomeExpense(it)
        }




        viewModel.categorySummaryQuery.observe(viewLifecycleOwner){

            viewModel.categorySummaryList =  it.sortedByDescending { it.amount }.map {
                CategorySummaryListData(it.categoryName,it.categoryId,CommonOperations.getResourceInt(application,it.categoryImage),it.categoryColor,it.amount)
            }
//            viewModel.updatePieChart(viewModel.categorySummaryList)
//            binding.summaryPieChart.data = viewModel.pieData
//            binding.summaryPieChart.invalidate()
            adapter.setData(viewModel.categorySummaryList)
        }


        return binding.root
    }

    override fun onSelectCategoryListClick(position: Int) {

        val categoryId  = viewModel.categorySummaryList[position].categoryId

        val(dateStart: Long, dateEnd: Long) = viewModel.getCategoryDetails()

        view?.findNavController()?.navigate(SummaryFragmentDirections.actionSummaryFragmentToTransactionListFragment(TransactionType.EXPENSE,-1,
            categoryId,dateStart,dateEnd))
    }

}