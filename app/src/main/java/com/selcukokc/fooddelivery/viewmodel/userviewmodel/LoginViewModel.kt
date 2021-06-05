package com.selcukokc.fooddelivery.viewmodel.userviewmodel

import android.app.Application
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.service.FirebaseService

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var _userMutableLiveData = MutableLiveData<FirebaseUser>()
    val userMutableLiveData : LiveData<FirebaseUser>
        get() = _userMutableLiveData

    private var firebaseService = FirebaseService()


    fun login(email: String, password: String){
        firebaseService.firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
            ContextCompat.getMainExecutor(getApplication()),
            {
                if (it.isSuccessful) {
                    _userMutableLiveData.postValue(firebaseService.firebaseAuth.currentUser)

                } else {
                    Toast.makeText(
                        getApplication(),
                        "Giriş başarısız oldu. " + it.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

            })

    }

}