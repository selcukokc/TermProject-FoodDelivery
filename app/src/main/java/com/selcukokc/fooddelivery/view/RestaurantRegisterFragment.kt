package com.selcukokc.fooddelivery.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.selcukokc.fooddelivery.R
import kotlinx.android.synthetic.main.fragment_restaurant_register.*

class RestaurantRegisterFragment : Fragment() {

    private val categories = ArrayList<String>()
    private lateinit var dataAdapter: ArrayAdapter<String>

    private val cities = ArrayList<String>()
    private lateinit var dataAdapter2: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categories.add("Burger")
        categories.add("Pizza")
        categories.add("Döner")
        categories.add("Kebap")
        categories.add("Pide, Lahmacun")
        categories.add("Çiğ Köfte")

        dataAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, android.R.id.text1, categories) }!!
        spinnerCategory.adapter = dataAdapter

        cities.add("İstanbul")
        cities.add("Ankara")
        cities.add("İzmir")
        cities.add("Bursa")
        cities.add("Adana")
        cities.add("Konya")
        cities.add("Gaziantep")

        dataAdapter2 = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, android.R.id.text1, cities) }!!
        spinnerCity.adapter = dataAdapter2







    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_register, container, false)
    }


}