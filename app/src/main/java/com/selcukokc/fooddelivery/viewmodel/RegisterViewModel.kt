package com.selcukokc.fooddelivery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.model.User
import com.selcukokc.fooddelivery.service.AppRepository

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    var appRepository : AppRepository
    var userMutableLiveData: MutableLiveData<FirebaseUser>
    var userinfoMutableLiveData: MutableLiveData<User>



    init{
        appRepository = AppRepository(application)
        userMutableLiveData = appRepository.getUserMutableLiveData()
        userinfoMutableLiveData = appRepository.getUserInfoMutableLiveData()
    }



    fun register(email: String, password: String ,name: String, surname: String, city: String,
                 favorites: ArrayList<Restaurants>, userAddress: String){
        appRepository.register(email, password, name, surname, city, favorites, userAddress)
    }

}