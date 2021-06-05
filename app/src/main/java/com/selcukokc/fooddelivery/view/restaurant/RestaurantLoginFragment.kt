package com.selcukokc.fooddelivery.view.restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantLoginBinding
import com.selcukokc.fooddelivery.viewmodel.restaurantviewmodel.RestaurantLoginViewModel



class RestaurantLoginFragment : Fragment() {

    private lateinit var restaurantLoginViewModel: RestaurantLoginViewModel
    private lateinit var binding: FragmentRestaurantLoginBinding

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
        binding = FragmentRestaurantLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToRegisterFragment.setOnClickListener {
            Navigation.findNavController(it).navigate(RestaurantLoginFragmentDirections.actionRestaurantLoginFragmentToRestaurantRegisterFragment())
        }

        binding.btnRestaurantLogin.setOnClickListener {
            val email = binding.txtRestaurantEmailLogin.text.toString().trim()
            val password = binding.txtRestPasswordLogin.text.toString().trim()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(context,"Lütfen tüm bilgileri eksiksiz girdiğinizden emin olunuz.", Toast.LENGTH_SHORT).show()
            }

            else
            restaurantLoginViewModel.restaurantLogin(email, password)
        }

    }

}