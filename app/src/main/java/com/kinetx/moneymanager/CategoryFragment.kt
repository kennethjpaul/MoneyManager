package com.kinetx.moneymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kinetx.moneymanager.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {

    lateinit var binding : FragmentCategoryBinding
    lateinit var viewModel : CategoryViewModel
    private lateinit var argList : CategoryFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

            argList  = CategoryFragmentArgs.fromBundle(requireArguments())

            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category,container,false)
            viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

            viewModel.initializeLayout(argList)

            binding.categoryViewModel = viewModel
            binding.lifecycleOwner = this


            (activity as AppCompatActivity).supportActionBar?.title =viewModel.fragmentTitle.value

            viewModel.fragmentTitle.observe(viewLifecycleOwner){
                (activity as AppCompatActivity).supportActionBar?.title =it
            }


            viewModel.iconImageSource.observe(viewLifecycleOwner){
                binding.categoryIconButton.setImageResource(it)
            }

            viewModel.coloColorCode.observe(viewLifecycleOwner){
                binding.categoryColorButton.setBackgroundResource(it)
            }

            return binding.root
    }

}