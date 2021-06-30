package com.selcukokc.fooddelivery.view.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.selcukokc.fooddelivery.adapters.RestaurantAdapter
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantListBinding
import kotlin.random.Random

class RestaurantListFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







    }


}