package com.selcukokc.fooddelivery.viewmodel.userviewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.model.User
import com.selcukokc.fooddelivery.service.FirebaseService

class HomepageViewModel(application: Application) : AndroidViewModel(application) {

    private var _userMutableLiveData = MutableLiveData<FirebaseUser>()
    val userMutableLiveData : LiveData<FirebaseUser>
        get() = _userMutableLiveData

    private var _loggedOutMutableLiveData = MutableLiveData<Boolean>()
    val loggedOutMutableLiveData : LiveData<Boolean>
        get() = _loggedOutMutableLiveData

    private var _userInfoMutableLiveData = MutableLiveData<User>()
    val userInfoLiveData : LiveData<User>
        get() = _userInfoMutableLiveData

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

    fun getUserInformation(){
        var userid = firebaseService.firebaseAuth.currentUser?.uid
        userid?.let { firebaseService.db.collection("Kullanıcılar").document(it).get()
            .addOnCompleteListener {  task->

                if(task.isSuccessful){
                    val document = task.result
                    val user = User(userid, document.getString("Ad"),document.getString("Soyad"),null, null, null)
                    _userInfoMutableLiveData.postValue(user)
                }

                else {
                    Log.e("LOGGER", "get failed with ", task.exception);
                }



        } }


    }

}


