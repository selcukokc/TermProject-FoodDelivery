package com.selcukokc.fooddelivery.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.service.FirebaseService

class RestaurantLoginViewModel(application: Application) : AndroidViewModel(application) {

    private var _restaurantUserMutableLiveData = MutableLiveData<FirebaseUser>()
    val restaurantUserMutableLiveData : LiveData<FirebaseUser>
        get() = _restaurantUserMutableLiveData

    private var firebaseService = FirebaseService()


    fun restaurantLogin(email: String, password: String){
        firebaseService.restaurantFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
            ContextCompat.getMainExecutor(getApplication()),
            {
                if (it.isSuccessful) {
                    _restaurantUserMutableLiveData.postValue(firebaseService.restaurantFirebaseAuth.currentUser)

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