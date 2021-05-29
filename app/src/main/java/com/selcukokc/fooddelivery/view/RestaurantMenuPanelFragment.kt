package com.selcukokc.fooddelivery.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantMenuPanelBinding

class RestaurantMenuPanelFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantMenuPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentRestaurantMenuPanelBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


}