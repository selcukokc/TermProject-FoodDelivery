package com.selcukokc.fooddelivery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.selcukokc.fooddelivery.databinding.FragmentRegisterBinding
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.viewmodel.RegisterViewModel


class RegisterFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.GONE
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        registerViewModel.userMutableLiveData.observe(this, Observer { firebaseUser ->
            if(firebaseUser != null){

                registerViewModel.userinfoMutableLiveData.observe(this, Observer { user ->

                    if (user != null){
                        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        view?.let { Navigation.findNavController(it).navigate(action) }
                    }

                })


            }


        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterScreenRegister.setOnClickListener {
            val email = binding.etMail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val name = binding.etName.text.toString().trim()
            val surname = binding.etSurname.text.toString().trim()
            val city = ""
            val favorites = ArrayList<Restaurants>()
            val address = ""

            if(email.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()){
                Toast.makeText(context, "Tüm bilgileri eksiksiz girdiğinizden emin olun.", Toast.LENGTH_SHORT).show()
            }
            else
                registerViewModel.register(email, password, name, surname, city, favorites, address)



        }


        binding.txtGoToLogin.setOnClickListener {
           Navigation.findNavController(it).navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())

        }

    }


}