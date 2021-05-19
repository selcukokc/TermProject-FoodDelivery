package com.selcukokc.fooddelivery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.service.AppRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var appRepository : AppRepository
    var userMutableLiveData: MutableLiveData<FirebaseUser>


    init{
        appRepository = AppRepository(application)
        userMutableLiveData =  appRepository.getUserMutableLiveData()
    }


    fun login(email: String, password: String){
        appRepository.login(email, password)
    }

}