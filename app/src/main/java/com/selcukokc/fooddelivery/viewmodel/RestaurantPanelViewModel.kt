package com.selcukokc.fooddelivery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.service.AppRepository

class RestaurantPanelViewModel(application: Application) : AndroidViewModel(application) {

    var appRepository : AppRepository
    var restLoggedOutMutableLiveData : MutableLiveData<Boolean>
    var restaurantInformationMutableLiveData: MutableLiveData<Restaurants>

    init{
        appRepository = AppRepository(application)
        restLoggedOutMutableLiveData = appRepository.getRestaurantLoggedOutMutableLiveData()
        restaurantInformationMutableLiveData = appRepository.getRestaurantInformationMutableLiveData()
    }

    fun logout(){
        restLoggedOutMutableLiveData.postValue(true)
        appRepository.logout()
    }

    fun restaurantInformation(){
        appRepository.restaurantInformation()
    }



}