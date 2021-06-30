package com.selcukokc.fooddelivery.view.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.api.Distribution
import com.selcukokc.fooddelivery.adapters.MenuAdapter
import com.selcukokc.fooddelivery.adapters.RestaurantAdapter
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantListBinding
import com.selcukokc.fooddelivery.viewmodel.userviewmodel.RestaurantListViewModel
import kotlin.random.Random

class RestaurantListFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantListBinding
    private lateinit var restaurantListViewModel: RestaurantListViewModel
    private lateinit var restaurantAdapter : RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restaurantListViewModel = ViewModelProvider(this).get(RestaurantListViewModel::class.java)


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

        restaurantListViewModel.restaurantListLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.restaurantListLoading.visibility = View.VISIBLE
                binding.restaurantList.visibility = View.GONE
            } else {
                binding.restaurantListLoading.visibility = View.GONE
                binding.restaurantList.visibility = View.VISIBLE
            }

        })

        binding.restaurantList.layoutManager = LinearLayoutManager(context)
        binding.restaurantList.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        restaurantAdapter = context?.let { RestaurantAdapter(it, arrayListOf()) }!!

        restaurantListViewModel.getRestaurants()

        restaurantListViewModel.restaurantListMutableLiveData.observe(viewLifecycleOwner, Observer { restaurantList ->
            if(restaurantList.isNotEmpty()){
                restaurantAdapter = context?.let { RestaurantAdapter(it, restaurantList) }!!
                binding.restaurantList.adapter = restaurantAdapter
            }

            else{
                Toast.makeText(context, "Gösterilecek restoran bulunamadi", Toast.LENGTH_SHORT).show()
            }


        })


    }


}