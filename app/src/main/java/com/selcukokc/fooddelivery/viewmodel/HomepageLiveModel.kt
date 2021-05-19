package com.selcukokc.fooddelivery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.selcukokc.fooddelivery.service.AppRepository

class HomepageLiveModel(application: Application) : AndroidViewModel(application) {
    var appRepository : AppRepository
    var loggedOutMutableLiveData: MutableLiveData<Boolean>


    init{
        appRepository = AppRepository(application)
        loggedOutMutableLiveData = MutableLiveData<Boolean>()

    }

    fun logout(){
        loggedOutMutableLiveData.postValue(true)
        appRepository.logout()

    }

}