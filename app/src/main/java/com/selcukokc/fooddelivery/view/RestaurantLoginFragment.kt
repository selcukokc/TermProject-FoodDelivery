package com.selcukokc.fooddelivery.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.viewmodel.RestaurantLoginViewModel
import kotlinx.android.synthetic.main.fragment_restaurant_login.*


class RestaurantLoginFragment : Fragment() {

    private lateinit var restaurantLoginViewModel: RestaurantLoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restaurantLoginViewModel = ViewModelProvider(this).get(RestaurantLoginViewModel::class.java)
        restaurantLoginViewModel.restaurantUserMutableLiveData.observe(this, Observer { firebaseUser ->
            if(firebaseUser != null){
               view?.let { Navigation.findNavController(it).navigate(RestaurantLoginFragmentDirections.actionRestaurantLoginFragmentToRestaurantPanelFragment()) }
                Toast.makeText(context, "Giriş Yapıldı.", Toast.LENGTH_SHORT).show()
            }


        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRestaurantLogin.setOnClickListener {
            val email = txtRestaurantEmailLogin.text.toString().trim()
            val password = txtRestPasswordLogin.text.toString().trim()
            restaurantLoginViewModel.restaurantLogin(email, password)

        }

    }

}