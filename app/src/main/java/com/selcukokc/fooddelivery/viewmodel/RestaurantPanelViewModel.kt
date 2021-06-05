package com.selcukokc.fooddelivery.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.service.FirebaseService

class RestaurantPanelViewModel(application: Application) : AndroidViewModel(application) {

    private var _restLoggedOutMutableLiveData = MutableLiveData<Boolean>()
    val restLoggedOutMutableLiveData : LiveData<Boolean>
        get() = _restLoggedOutMutableLiveData

    private var _restaurantUserMutableLiveData = MutableLiveData<FirebaseUser>()
    val restaurantUserMutableLiveData : LiveData<FirebaseUser>
        get() = _restaurantUserMutableLiveData

    private var _restaurantInformationMutableLiveData = MutableLiveData<Restaurants>()
    val restaurantInformationMutableLiveData : LiveData<Restaurants>
        get() = _restaurantInformationMutableLiveData

    private var _restaurantInfoError = MutableLiveData<Boolean>()
    val restaurantInfoError : LiveData<Boolean>
        get() = _restaurantInfoError

    private var _restaurantInfoLoading = MutableLiveData<Boolean>()
    val restaurantInfoLoading : LiveData<Boolean>
        get() = _restaurantInfoLoading

    private val firebaseService = FirebaseService()

    init{
        _restaurantInfoLoading.postValue(true)
        _restaurantInfoError.postValue(false)

        if(firebaseService.restaurantFirebaseAuth.currentUser != null){
            _restaurantUserMutableLiveData.postValue(firebaseService.firebaseAuth.currentUser)
            _restLoggedOutMutableLiveData.postValue(false)
        }
    }

    fun logout(){
        firebaseService.restaurantFirebaseAuth.signOut()
        _restLoggedOutMutableLiveData.postValue(true)
    }

    fun restaurantInformation(){
        var restaurant : Restaurants?
        val userID = firebaseService.restaurantFirebaseAuth.currentUser?.uid
        userID?.let { id->
            val docRef = firebaseService.db.collection("Restoranlar").document(userID)
            docRef.get().addOnCompleteListener {

                if(it.isSuccessful){
                    val document = it.result

                    if(document!=null){
                        restaurant = Restaurants(userID, document.getString("Kategori"),document.getString("Ad"),
                            document.getString("Logo"),null, document.getString("Şehir"), null,
                            null)
                        _restaurantInformationMutableLiveData.postValue(restaurant)
                        _restaurantInfoError.postValue(false)
                        _restaurantInfoLoading.postValue(false)
                    } else {
                        Log.e("LOGGER", "No such document");
                    }


                } else {
                    Log.e("LOGGER", "get failed with ", it.exception);
                }

            }


        }


    }


}