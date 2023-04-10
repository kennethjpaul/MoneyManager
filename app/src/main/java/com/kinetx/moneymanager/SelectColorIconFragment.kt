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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kinetx.moneymanager.databinding.FragmentSelectColorIconBinding


class SelectColorIconFragment : Fragment(),SelectCategoryAdapter.OnSelectCategoryListener {

    private lateinit var binding : FragmentSelectColorIconBinding
    private lateinit var viewModel : SelectColorIconViewModel
    private lateinit var argList : SelectColorIconFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_color_icon,container,false)
        viewModel = ViewModelProvider(this).get(SelectColorIconViewModel::class.java)
        argList = SelectColorIconFragmentArgs.fromBundle(requireArguments())

        binding.selectColorIconViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.initializeLayout(argList)

        val adapter = SelectCategoryAdapter(this)
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

    override fun onSelectCategoryClick(position: Int) {

        val item = viewModel.itemList.value?.get(position)
        val itemImage : Int = item?.itemImage!!
        val itemColor : Int = item.itemColor

        setFragmentResult("SelectColorIcon", bundleOf("colorIconType" to argList.colorIconType,"itemBackgroundColor" to itemColor, "itemBackgroundImage" to itemImage))
        view?.findNavController()?.navigateUp()
    }

    override fun onSelectCategoryLongClick(position: Int) {
        //Toast.makeText(context, "long clicked", Toast.LENGTH_SHORT).show()
    }

}