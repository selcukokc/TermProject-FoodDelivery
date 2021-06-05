package com.selcukokc.fooddelivery.view.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.selcukokc.fooddelivery.adapters.MenuAdapter
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantDetailBinding


class RestaurantDetailFragment : Fragment() {
    private lateinit var menuList: ArrayList<Menu>
    private lateinit var binding: FragmentRestaurantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        menuList = ArrayList<Menu>()
        binding.restaurantDetailRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        arguments?.let {

           // binding.txtRestaurantName.text = RestaurantDetailFragmentArgs.fromBundle(it).restaurant.restoranAd
           // binding.txtCategoryName.text = RestaurantDetailFragmentArgs.fromBundle(it).restaurant.kategori
           // binding.txtRestaurantRating.text = RestaurantDetailFragmentArgs.fromBundle(it).restaurant.puan.toString()


           // menuList.add(RestaurantDetailFragmentArgs.fromBundle(it).restaurant.menuArr?.get(0))

        }



       var menuAdapter = context?.let { MenuAdapter(it, menuList) }

       binding.restaurantDetailRv.adapter = menuAdapter

    }

}