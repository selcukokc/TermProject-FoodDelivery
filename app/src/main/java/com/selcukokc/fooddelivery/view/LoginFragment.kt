package com.selcukokc.fooddelivery.view

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.viewmodel.LoginViewModel
import com.selcukokc.fooddelivery.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.GONE
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.userMutableLiveData.observe(this, Observer { firebaseUser ->

            if(firebaseUser!=null){
                view?.let { Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_homepageFragment) }
                Toast.makeText(context, "Giriş Yapıldı.", Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        txtGoToRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        btnLogin.setOnClickListener {
                val email = txtEmailLogin.text.toString().trim()
                val password = txtPasswordLogin.text.toString().trim()

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(context,"Lütfen tüm bilgileri eksiksiz girdiğinizden emin olunuz.", Toast.LENGTH_SHORT).show()
                }

                else
                    loginViewModel.login(email, password)

        }




    }


}