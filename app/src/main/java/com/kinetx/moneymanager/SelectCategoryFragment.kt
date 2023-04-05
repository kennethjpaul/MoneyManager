package com.kinetx.moneymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kinetx.moneymanager.databinding.FragmentSelectCategoryBinding
import java.util.ArrayList


class SelectCategoryFragment : Fragment() {

    private lateinit var binding : FragmentSelectCategoryBinding
    private lateinit var viewModel : SelectCategoryViewModel

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


        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_select_category, container, false)
        binding.selectCategoryRecyclerview.layoutManager = GridLayoutManager(context,4)
        binding.selectCategoryRecyclerview.setHasFixedSize(true)

        viewModel = ViewModelProvider(this).get(SelectCategoryViewModel::class.java)
        viewModel.updateData(argList)


        val adapter = SelectCategoryAdapter()
        binding.selectCategoryRecyclerview.adapter = adapter

        viewModel.itemList.observe(viewLifecycleOwner, Observer
        {
         it?.let{
                adapter.selectCategoryItemArray = it
            }
        })



        binding.selectCategorySelectButton.setOnClickListener()
        {
            val num = R.drawable.android
            val color = R.color.black
            val id : Long =1

            setFragmentResult("SelectCategory", bundleOf("transaction" to argList.transactionType, "category" to argList.actionType,"imgId" to num, "color" to color, "id" to id))

            view?.findNavController()?.navigateUp()
        }
        return binding.root
    }


}