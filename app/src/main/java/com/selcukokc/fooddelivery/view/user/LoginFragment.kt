package com.selcukokc.fooddelivery.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.databinding.FragmentLoginBinding
import com.selcukokc.fooddelivery.view.BaseFragment
import com.selcukokc.fooddelivery.viewmodel.userviewmodel.LoginViewModel



class LoginFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.GONE
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

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

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.txtGoToRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.btnLogin.setOnClickListener {
                val email = binding.txtEmailLogin.text.toString().trim()
                val password = binding.txtPasswordLogin.text.toString().trim()

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(context,"Lütfen tüm bilgileri eksiksiz girdiğinizden emin olunuz.", Toast.LENGTH_SHORT).show()
                }

                else
                    loginViewModel.login(email, password)

        }




    }


}