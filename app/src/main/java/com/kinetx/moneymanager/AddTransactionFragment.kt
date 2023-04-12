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
            binding.addTransactionCategoryOneBtn.setBackgroundColor(it.buttonColor)
            binding.addTransactionCategoryOneBtn.setImageResource(it.buttonImage)
            binding.transactionCategoryOneSelect.text = it.buttonTitle
        }

        viewModel.categoryPositionTwo.observe(viewLifecycleOwner)
        {
            binding.addTransactionCategoryTwoBtn.setBackgroundColor(it.buttonColor)
            binding.addTransactionCategoryTwoBtn.setImageResource(it.buttonImage)
            binding.transactionCategoryTwoSelect.text = it.buttonTitle
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
            val itemColor = bundle.getInt("itemColor")
            val varType = bundle.getString("category")
            val itemImage = bundle.getInt("itemImage")
            val itemId = bundle.getLong("itemId")
            val itemTitle= bundle.getString("itemTitle")!!

            when(varType)
            {
                "account" -> viewModel.updateCategoryPositionOne(itemId,itemImage,itemColor,itemTitle)
                "category" -> viewModel.updateCategoryPositionTwo(itemId,itemImage,itemColor,itemTitle)
                "source" -> viewModel.updateCategoryPositionOne(itemId,itemImage,itemColor,itemTitle)
                "destination" -> viewModel.updateCategoryPositionTwo(itemId,itemImage,itemColor,itemTitle)
            }

        }

        return binding.root
    }

}