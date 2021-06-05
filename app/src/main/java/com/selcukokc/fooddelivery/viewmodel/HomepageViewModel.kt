package com.selcukokc.fooddelivery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.service.FirebaseService

class HomepageViewModel(application: Application) : AndroidViewModel(application) {

    private var _userMutableLiveData = MutableLiveData<FirebaseUser>()
    val userMutableLiveData : LiveData<FirebaseUser>
        get() = _userMutableLiveData

    private var _loggedOutMutableLiveData = MutableLiveData<Boolean>()
    val loggedOutMutableLiveData : LiveData<Boolean>
        get() = _loggedOutMutableLiveData

    private var firebaseService = FirebaseService()

    init{
        if(firebaseService.firebaseAuth.currentUser != null){
            _userMutableLiveData.postValue(firebaseService.firebaseAuth.currentUser)
            _loggedOutMutableLiveData.postValue(false)
        }
    }

    fun logout(){
        firebaseService.firebaseAuth.signOut()
        _loggedOutMutableLiveData.postValue(true)
    }

}