package com.kinetx.moneymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kinetx.moneymanager.databinding.FragmentAddTransactionBinding


class AddTransactionFragment : Fragment() {

    private lateinit var binding : FragmentAddTransactionBinding
    private lateinit var viewModel : AddTransactionViewModel
    private  lateinit var argList : AddTransactionFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_transaction, container, false)
        viewModel = ViewModelProvider(this).get(AddTransactionViewModel::class.java)
        argList = AddTransactionFragmentArgs.fromBundle(requireArguments())

        binding.addTransactionViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.initializeLayout(argList)







        viewModel.fragmentTitle.observe(viewLifecycleOwner)
        {
            (activity as AppCompatActivity).supportActionBar?.title = it
        }

        viewModel.categoryPositionOne.observe(viewLifecycleOwner)
        {
            binding.addTransactionCategoryOneBtn.setBackgroundResource(it.bgColor)
            binding.addTransactionCategoryOneBtn.setImageResource(it.imageId)
        }

        viewModel.categoryPositionTwo.observe(viewLifecycleOwner)
        {
            binding.addTransactionCategoryTwoBtn.setBackgroundResource(it.bgColor)
            binding.addTransactionCategoryTwoBtn.setImageResource(it.imageId)
        }





        binding.addTransactionAddDateBtn.setOnClickListener()
        {
            viewModel.datePick(it)
        }

        binding.addTransactionCategoryOneBtn.setOnClickListener()
        {
            val categoryType : String = viewModel.categoryPositionOneText.value?.lowercase()!!
            view?.findNavController()?.navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToSelectCategoryFragment(argList.transactionType,categoryType))
        }

        binding.addTransactionCategoryTwoBtn.setOnClickListener()
        {
            val categoryType : String = viewModel.categoryPositionTwoText.value?.lowercase()!!
            view?.findNavController()?.navigate(AddTransactionFragmentDirections.actionAddTransactionFragmentToSelectCategoryFragment(argList.transactionType,categoryType))
        }







        setFragmentResultListener("SelectCategory")
        { _, bundle ->
            val varBgColor = bundle.getInt("itemColor")
            val varType = bundle.getString("category")
            val varImgId = bundle.getInt("itemImage")
            val varId = bundle.getLong("id")

            when(varType)
            {
                "account" -> viewModel.updateCategoryPositionOne(varId,varImgId,varBgColor)
                "category" -> viewModel.updateCategoryPositionTwo(varId,varImgId,varBgColor)
                "source" -> viewModel.updateCategoryPositionOne(varId,varImgId,varBgColor)
                "destination" -> viewModel.updateCategoryPositionTwo(varId,varImgId,varBgColor)
            }

        }

        return binding.root
    }

}