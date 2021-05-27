package com.selcukokc.fooddelivery.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.selcukokc.fooddelivery.R
import kotlinx.android.synthetic.main.fragment_restaurant_register.*

class RestaurantRegisterFragment : Fragment() {

    private lateinit var categoryAdapter: ArrayAdapter<CharSequence>
    private lateinit var cityAdapter: ArrayAdapter<CharSequence>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_register, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtGoToLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(RestaurantRegisterFragmentDirections.actionRestaurantRegisterFragmentToRestaurantLoginFragment())

        }

        categoryAdapter = activity?.let { ArrayAdapter.createFromResource(it?.baseContext,R.array.categories_array, android.R.layout.simple_list_item_1) } as ArrayAdapter<CharSequence>
        spinnerCategory.adapter = categoryAdapter

        cityAdapter = activity?.let { ArrayAdapter.createFromResource(it?.baseContext, R.array.cities_array, android.R.layout.simple_list_item_1) } as ArrayAdapter<CharSequence>
        spinnerCity.adapter = cityAdapter

    }

}