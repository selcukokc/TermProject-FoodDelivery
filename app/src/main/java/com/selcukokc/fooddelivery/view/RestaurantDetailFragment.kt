package com.selcukokc.fooddelivery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.selcukokc.fooddelivery.adapters.MenuAdapter
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.R
import kotlinx.android.synthetic.main.fragment_restaurant_detail.*

class RestaurantDetailFragment : Fragment() {
    private lateinit var menuList: ArrayList<Menu>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        menuList = ArrayList<Menu>()
        restaurantDetailRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        arguments?.let {

            txtRestaurantName.text = RestaurantDetailFragmentArgs.fromBundle(it).restaurant.restoranAd
            txtCategoryName.text = RestaurantDetailFragmentArgs.fromBundle(it).restaurant.kategori
            txtRestaurantRating.text = RestaurantDetailFragmentArgs.fromBundle(it).restaurant.puan.toString()


            menuList.add(RestaurantDetailFragmentArgs.fromBundle(it).restaurant.menuArr?.get(0))

        }



       var menuAdapter = context?.let { MenuAdapter(it, menuList) }

       restaurantDetailRv.adapter = menuAdapter

    }

}