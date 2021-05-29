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
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantRegisterBinding
import com.selcukokc.fooddelivery.viewmodel.RestaurantRegisterViewModel


class RestaurantRegisterFragment : Fragment() {

    private lateinit var restaurantRegisterViewModel: RestaurantRegisterViewModel
    private lateinit var binding: FragmentRestaurantRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restaurantRegisterViewModel = ViewModelProvider(this).get(RestaurantRegisterViewModel::class.java)

        restaurantRegisterViewModel.restaurantUserMutableLiveData.observe(this, Observer {  firebaseUser->
            if(firebaseUser != null){
                restaurantRegisterViewModel.restaurantUserInformationMutableLiveData.observe(this, Observer { arr->
                    if(arr != null){
                        val action = RestaurantRegisterFragmentDirections.actionRestaurantRegisterFragmentToRestaurantPanelFragment()
                        view?.let { Navigation.findNavController(it).navigate(action) }
                    }

                })

            }



        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtGoToLogin.setOnClickListener {
            Navigation.findNavController(it).
            navigate(RestaurantRegisterFragmentDirections.actionRestaurantRegisterFragmentToRestaurantLoginFragment())
        }


        binding.btnRegisterScreenRegister.setOnClickListener {
            val email = binding.etRestaurantMail.text.toString().trim()
            val password = binding.etRestaurantPassword.text.toString().trim()
            val restaurantName = binding.etRestaurantName.text.toString().trim()
            val address = binding.etRestaurantAddress.text.toString().trim()
            val category = binding.etCategory.text.toString().trim()
            val city = binding.etCity.text.toString().trim()
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