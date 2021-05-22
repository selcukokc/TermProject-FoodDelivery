package com.selcukokc.fooddelivery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.service.AppRepository

class RestaurantLoginViewModel(application: Application) : AndroidViewModel(application) {

    var appRepository : AppRepository
    var restaurantUserMutableLiveData: MutableLiveData<FirebaseUser>

    init{
        appRepository = AppRepository(application)
        restaurantUserMutableLiveData = appRepository.getRestaurantUserMutableLiveData()
    }


    fun login(email: String, password: String){
        appRepository.login(email, password)
    }

}