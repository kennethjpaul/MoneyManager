package com.kinetx.moneymanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kinetx.moneymanager.databinding.FragmentAddTransferBinding


class AddTransferFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = "Add Transfer"
        val binding : FragmentAddTransferBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_transfer, container, false)
        return binding.root
    }

}