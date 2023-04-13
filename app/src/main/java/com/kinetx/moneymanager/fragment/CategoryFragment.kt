package com.kinetx.moneymanager.fragment

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
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentCategoryBinding
import com.kinetx.moneymanager.viewmodel.CategoryViewModel
import com.kinetx.moneymanager.viewmodelfactory.CategoryViewModelFactory


class CategoryFragment : Fragment() {

    lateinit var binding : FragmentCategoryBinding
    lateinit var viewModel : CategoryViewModel
    private lateinit var argList : CategoryFragmentArgs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


            argList  = CategoryFragmentArgs.fromBundle(requireArguments())

            val application = requireNotNull(this.activity).application
            val viewModelFactory = CategoryViewModelFactory(argList,application)

            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category,container,false)
            viewModel = ViewModelProvider(this,viewModelFactory).get(CategoryViewModel::class.java)


            binding.categoryViewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner


            viewModel.fragmentTitle.observe(viewLifecycleOwner){
                (activity as AppCompatActivity).supportActionBar?.title =it
            }

            viewModel.iconImageSource.observe(viewLifecycleOwner){
                binding.categoryIconButton.setImageResource(it)
            }

            viewModel.coloColorCode.observe(viewLifecycleOwner){
                binding.categoryColorButton.setBackgroundColor(it)
            }


            binding.categoryIconButton.setOnClickListener()
            {
                view?.findNavController()?.navigate(
                    CategoryFragmentDirections.actionCategoryFragmentToSelectColorIconFragment(
                        "icon"
                    )
                )
            }

            binding.categoryColorButton.setOnClickListener()
            {
                view?.findNavController()?.navigate(
                    CategoryFragmentDirections.actionCategoryFragmentToSelectColorIconFragment(
                        "color"
                    )
                )
            }

            binding.categoryAddButton.setOnClickListener()
            {

                var catType : String = if (argList.category=="account" || argList.itemType=="transfer") {
                    "account"
                } else {
                    argList.itemType
                }

                view?.findNavController()?.navigateUp()
            }


            setFragmentResultListener("SelectColorIcon")
            { _, bundle ->

                val colorIconType = bundle.getString("colorIconType")
                val itemBackgroundColor= bundle.getInt("itemBackgroundColor")
                val itemBackgroundImage = bundle.getInt("itemBackgroundImage")


                when(colorIconType)
                {
                    "icon" -> viewModel.updateIcon(itemBackgroundImage)
                    "color" -> viewModel.updateColor(itemBackgroundColor)
                }

            }

            return binding.root
    }

}