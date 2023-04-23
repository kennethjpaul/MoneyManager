package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentBalancesBinding
import com.kinetx.moneymanager.recyclerview.CategoryListAdapter
import com.kinetx.moneymanager.viewmodel.BalancesViewModel
import com.kinetx.moneymanager.viewmodelfactory.BalancesViewModelFactory


class BalancesFragment : Fragment(), CategoryListAdapter.OnSelectCategoryListListener {

    lateinit var binding :FragmentBalancesBinding
    lateinit var viewModel: BalancesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val application = requireNotNull(this.activity).application
        val viewModelFactory = BalancesViewModelFactory(application)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_balances,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory).get(BalancesViewModel::class.java)

        binding.balanceViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        val adapter = CategoryListAdapter(this)
        binding.balancesRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.balancesRecyclerview.setHasFixedSize(true)
        binding.balancesRecyclerview.adapter = adapter

        viewModel.fragmentTitle.observe(viewLifecycleOwner){
            (activity as AppCompatActivity).supportActionBar?.title =it
        }

        viewModel.list.observe(viewLifecycleOwner)
        {
            adapter.setData(it)
        }

        return binding.root
    }

    override fun onSelectCategoryListClick(position: Int) {
        Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show()
    }

}