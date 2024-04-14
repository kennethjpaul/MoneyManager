package com.kinetx.moneymanager.fragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.SelectCategoryAdapter
import com.kinetx.moneymanager.database.CategoryDatabase
import com.kinetx.moneymanager.databinding.FragmentCategorySummaryBinding
import com.kinetx.moneymanager.dataclass.SelectCategoryData
import com.kinetx.moneymanager.enums.CategoryType
import com.kinetx.moneymanager.helpers.CommonOperations
import com.kinetx.moneymanager.viewmodel.CategorySummaryVM
import com.kinetx.moneymanager.viewmodelfactory.CategorySummaryVMF


class CategorySummaryFragment : Fragment(), SelectCategoryAdapter.OnSelectCategoryListener{

    lateinit var binding :FragmentCategorySummaryBinding
    lateinit var viewModel : CategorySummaryVM
    lateinit var application : Application
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        application = requireNotNull(this.activity).application
        val viewModelFactory = CategorySummaryVMF(application)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_category_summary,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory)[CategorySummaryVM::class.java]

        binding.categorySummaryVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val adapterExpense = SelectCategoryAdapter(this, "expense")
        binding.categorySummaryExpenseCardRecyclerview.layoutManager = GridLayoutManager(context,3)
        binding.categorySummaryExpenseCardRecyclerview.setHasFixedSize(true)
        binding.categorySummaryExpenseCardRecyclerview.adapter = adapterExpense

        val adapterIncome = SelectCategoryAdapter(this, "income")
        binding.categorySummaryIncomeCardRecyclerview.layoutManager = GridLayoutManager(context,3)
        binding.categorySummaryIncomeCardRecyclerview.setHasFixedSize(true)
        binding.categorySummaryIncomeCardRecyclerview.adapter = adapterIncome


        viewModel.readAllIncomeCategories.observe(viewLifecycleOwner)
        {
            val data = it.map {
                SelectCategoryData(it.categoryId,it.categoryName,it.categoryType,it.categoryImage,
                    CommonOperations.getResourceInt(application,it.categoryImageString),it.categoryColor)
            }
            adapterIncome.setData(data)
        }

        viewModel.readAllExpenseCategories.observe(viewLifecycleOwner)
        {
            val data = it.map {
                SelectCategoryData(it.categoryId,it.categoryName,it.categoryType,it.categoryImage,
                    CommonOperations.getResourceInt(application,it.categoryImageString),it.categoryColor)
            }
            adapterExpense.setData(data)
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onSelectCategoryClick(position: Int, identifier: String) {
        var item = CategoryDatabase()
        var itemType = CategoryType.INCOME
        when(identifier)
        {
            "expense" -> {
                item = viewModel.readAllExpenseCategories.value?.get(position)!!
                itemType = CategoryType.EXPENSE
            }
            "income" -> {
                item = viewModel.readAllIncomeCategories.value?.get(position)!!
                itemType = CategoryType.INCOME
            }
        }

        val itemId : Long = item.categoryId
        val itemName : String = item.categoryName
        val itemIcon : Int = CommonOperations.getResourceInt(application, item.categoryImageString)
        val itemColor : Int = item.categoryColor

        val itemImageString : String = item.categoryImageString
        view?.findNavController()?.navigate(CategorySummaryFragmentDirections.actionCategorySummaryFragmentToCategoryFragment(true,itemId,itemName,itemIcon,itemColor,itemType,itemImageString))

    }


    override fun onSelectCategoryLongClick(position: Int) {
        Log.i("III","Not yet")
    }

}