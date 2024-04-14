package com.kinetx.moneymanager.fragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentBalancesBinding
import com.kinetx.moneymanager.dataclass.CategorySummaryListData
import com.kinetx.moneymanager.enums.CategoryType
import com.kinetx.moneymanager.helpers.CommonOperations
import com.kinetx.moneymanager.recyclerview.CategoryListAdapter
import com.kinetx.moneymanager.viewmodel.BalancesViewModel
import com.kinetx.moneymanager.viewmodelfactory.BalancesViewModelFactory


class BalancesFragment : Fragment(), CategoryListAdapter.OnSelectCategoryListListener {

    lateinit var binding :FragmentBalancesBinding
    lateinit var viewModel: BalancesViewModel
    lateinit var application : Application
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        application = requireNotNull(this.activity).application
        val viewModelFactory = BalancesViewModelFactory(application)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_balances,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory).get(BalancesViewModel::class.java)

        binding.balanceViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val adapter = CategoryListAdapter(this)
        binding.balancesRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.balancesRecyclerview.setHasFixedSize(true)
        binding.balancesRecyclerview.adapter = adapter


        binding.balancesCreateButton.setOnClickListener()
        {
            view?.findNavController()?.navigate(BalancesFragmentDirections.actionBalancesFragmentToCategoryFragment(false,-1L,"",R.drawable.help,R.color.teal_700,CategoryType.ACCOUNT,"help"))
        }

        viewModel.fragmentTitle.observe(viewLifecycleOwner){
            (activity as AppCompatActivity).supportActionBar?.title =it
        }

        viewModel.list.observe(viewLifecycleOwner)
        {
            val data = it.map {
                CategorySummaryListData(it.categoryName,it.categoryId,
                    CommonOperations.getResourceInt(application,it.categoryImage),it.categoryColor,it.amount)
            }
            adapter.setData(data)
        }

        return binding.root
    }

    override fun onSelectCategoryListClick(position: Int) {

        val item = viewModel.list.value?.get(position)!!
        val itemId : Long = item.categoryId
        val itemName : String = item.categoryName
        val itemIcon : Int = CommonOperations.getResourceInt(application, item.categoryImage)
        val itemColor : Int = item.categoryColor
        val itemType = CategoryType.ACCOUNT
        val itemImageString : String = item.categoryImage

        view?.findNavController()?.navigate(BalancesFragmentDirections.actionBalancesFragmentToCategoryFragment(true,itemId,itemName,itemIcon,itemColor,itemType,itemImageString))
    }

    override fun onResume() {
        super.onResume()
        viewModel.updatePage()
    }
}