package com.selcukokc.fooddelivery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.selcukokc.fooddelivery.databinding.FragmentFirstBinding



class FirstFragment : BaseFragment() {
    private lateinit var binding: FragmentFirstBinding
    override var bottomNavigationViewVisibility = View.GONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userOption.setOnClickListener {
            Navigation.findNavController(it).navigate(FirstFragmentDirections.actionFirstFragmentToLoginFragment())
        }

        binding.restaurantOption.setOnClickListener {
            Navigation.findNavController(it).navigate(FirstFragmentDirections.actionFirstFragmentToRestaurantLoginFragment())
        }

    }



}