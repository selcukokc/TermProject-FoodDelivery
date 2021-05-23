package com.selcukokc.fooddelivery.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.viewmodel.RestaurantPanelViewModel
import kotlinx.android.synthetic.main.fragment_restaurant_panel.*


class RestaurantPanelFragment : Fragment() {

    private lateinit var restaurantPanelViewModel: RestaurantPanelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        restaurantPanelViewModel = ViewModelProvider(this).get(RestaurantPanelViewModel::class.java)
        restaurantPanelViewModel.restLoggedOutMutableLiveData.observe(this, Observer{
            if(it){
                Toast.makeText(context, "ÇIKIŞ YAPILDI", Toast.LENGTH_SHORT).show()
                val action = RestaurantPanelFragmentDirections.actionRestaurantPanelFragmentToRestaurantLoginFragment()
                view?.let{ it1 -> Navigation.findNavController(it1).navigate(action) }
            }


        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_restaurant_panel, container, false)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.restaurant_panel_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           R.id.restaurant_logout -> restaurantPanelViewModel.logout()

        }


        return true
    }




}