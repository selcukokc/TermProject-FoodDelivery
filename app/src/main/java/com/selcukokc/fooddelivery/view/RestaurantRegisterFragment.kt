package com.selcukokc.fooddelivery.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.viewmodel.RestaurantRegisterViewModel
import kotlinx.android.synthetic.main.fragment_restaurant_register.*

class RestaurantRegisterFragment : Fragment() {

    private lateinit var restaurantRegisterViewModel: RestaurantRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("ERROR", "oncreate is working!")
        restaurantRegisterViewModel = ViewModelProvider(this).get(RestaurantRegisterViewModel::class.java)

        restaurantRegisterViewModel.userRestaurantMutableLiveData.observe(this, Observer {  firebaseUser->
            if(firebaseUser != null){
                restaurantRegisterViewModel.restaurantUserInformationMutableLiveData.observe(this, Observer { arr->
                    if(arr != null){
                        val action = RestaurantRegisterFragmentDirections.actionRestaurantRegisterFragmentToRestaurantPanelFragment()
                        view?.let { Navigation.findNavController(it).navigate(action) }
                    }
                    else
                    Log.e("ERROR", "array is null!")

                })

            }

            else{
                Log.e("ERROR", "Firebase user is null!")
            }


        })


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
            Navigation.findNavController(it).
            navigate(RestaurantRegisterFragmentDirections.actionRestaurantRegisterFragmentToRestaurantLoginFragment())
        }


        btnRegisterScreenRegister.setOnClickListener {
            val email = etRestaurantMail.text.toString().trim()
            val password = etRestaurantPassword.text.toString().trim()
            val restaurantName = etRestaurantName.text.toString().trim()
            val address = etRestaurantAddress.text.toString().trim()
            val category = etCategory.text.toString().trim()
            val city = etCity.text.toString().trim()
            val rating = 0.0
            val comments = ArrayList<String>()
            val logo = ""


            if(email.isEmpty() || password.isEmpty() || restaurantName.isEmpty() || address.isEmpty()
                || category.isEmpty() || city.isEmpty()){
                    Toast.makeText(context, "Lütfen tüm bilgileri eksiksiz girdiğinizden emin olunuz", Toast.LENGTH_SHORT).show()
            }

            else {
                restaurantRegisterViewModel.restaurantRegister(
                    email, password, restaurantName,
                    category, city, address, comments, rating, logo
                )
            }

        }

    }

}