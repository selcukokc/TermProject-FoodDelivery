package com.selcukokc.fooddelivery.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_register.*
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.viewmodel.RegisterViewModel


class RegisterFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.GONE
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        registerViewModel.userMutableLiveData.observe(this, Observer { firebaseUser ->
            if(firebaseUser != null){  //FirebaseUser null olarak geliyor.

                Log.e("asdas","firebaseUser null degil")
                registerViewModel.userinfoMutableLiveData.observe(this, Observer { infoArray->

                    if (infoArray != null){
                        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        view?.let { Navigation.findNavController(it).navigate(action) }
                    }

                })


            }


        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegisterScreenRegister.setOnClickListener {
            val email = etMail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val name = etName.text.toString().trim()
            val surname = etSurname.text.toString().trim()
            val city = ""
            val favorites = ArrayList<String>()
            val address = ""

            if(email.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()){
                Toast.makeText(context, "Tüm bilgileri eksiksiz girdiğinizden emin olun.", Toast.LENGTH_SHORT).show()
            }
            else
                registerViewModel.register(email, password, name, surname, city, favorites, address)



        }


        txtGoToLogin.setOnClickListener {
           Navigation.findNavController(it).navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())

        }

    }


}