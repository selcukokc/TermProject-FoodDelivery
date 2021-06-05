package com.selcukokc.fooddelivery.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.selcukokc.fooddelivery.databinding.FragmentProfileBinding
import com.selcukokc.fooddelivery.view.BaseFragment

class ProfileFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

}