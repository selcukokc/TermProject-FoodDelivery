package com.selcukokc.fooddelivery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.service.AppRepository


class RestaurantRegisterViewModel(application: Application) : AndroidViewModel(application) {

    var appRepository : AppRepository
    var restaurantUserMutableLiveData: MutableLiveData<FirebaseUser>
    var restaurantUserInformationMutableLiveData: MutableLiveData<ArrayList<String>>



    init{
        appRepository = AppRepository(application)
        restaurantUserMutableLiveData = appRepository.getRestaurantUserMutableLiveData()
        restaurantUserInformationMutableLiveData = appRepository.getRestaurantInformationMutableLiveData()
    }



    fun restaurantRegister(email: String, password: String ,restaurantName: String, category: String, city: String,
    address: String, comments: ArrayList<String>, rating: Double, logo: String){
        appRepository.restaurantRegister(email, password, restaurantName, category, city, address, comments, rating, logo)
    }




}