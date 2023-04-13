package com.kinetx.moneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.databinding.FragmentSelectColorIconBinding
import com.kinetx.moneymanager.recyclerview.SelectIconAdapter
import com.kinetx.moneymanager.viewmodel.SelectColorIconViewModel
import com.kinetx.moneymanager.viewmodelfactory.SelectIconColorViewModelFactory


class SelectColorIconFragment : Fragment(),SelectIconAdapter.OnSelectIconListener {

    private lateinit var binding : FragmentSelectColorIconBinding
    private lateinit var viewModel : SelectColorIconViewModel
    private lateinit var argList : SelectColorIconFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        argList = SelectColorIconFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val viewModelFactory = SelectIconColorViewModelFactory(argList,application)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_select_color_icon,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SelectColorIconViewModel::class.java)


        binding.selectColorIconViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = SelectIconAdapter(this)
        binding.recyclerView.layoutManager = GridLayoutManager(context,4)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter


        viewModel.fragmentTitle.observe(viewLifecycleOwner){
            (activity as AppCompatActivity).supportActionBar?.title =it
        }

        viewModel.itemList.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onSelectIconClick(position: Int) {

        val item = viewModel.itemList.value?.get(position)
        val itemImage : Int = item?.iconImage!!
        val itemColor : Int = item.iconColor

        setFragmentResult("SelectColorIcon", bundleOf("colorIconType" to argList.colorIconType,"itemBackgroundColor" to itemColor, "itemBackgroundImage" to itemImage))
        view?.findNavController()?.navigateUp()
    }


}