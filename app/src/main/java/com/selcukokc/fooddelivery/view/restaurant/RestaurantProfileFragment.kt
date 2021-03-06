package com.selcukokc.fooddelivery.view.restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantProfileBinding
import com.selcukokc.fooddelivery.view.BaseFragment

class RestaurantProfileFragment : BaseFragment() {

    override var bottomNavigationViewVisibility = View.GONE
    private lateinit var binding: FragmentRestaurantProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


}