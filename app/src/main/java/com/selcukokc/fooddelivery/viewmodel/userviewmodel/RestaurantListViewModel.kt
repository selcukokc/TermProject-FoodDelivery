package com.selcukokc.fooddelivery.viewmodel.userviewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.service.FirebaseService

class RestaurantListViewModel(application: Application) : AndroidViewModel(application) {

    private var _restaurantListMutableLiveData = MutableLiveData<ArrayList<Restaurants>>()
    val restaurantListMutableLiveData : LiveData<ArrayList<Restaurants>>
        get() = _restaurantListMutableLiveData

    private var _restaurantListLoading = MutableLiveData<Boolean>()
    val restaurantListLoading : LiveData<Boolean>
        get() = _restaurantListLoading

    private var firebaseService = FirebaseService()

    init{
        _restaurantListLoading.postValue(true)
    }

    fun getRestaurants(){
        val list = ArrayList<Restaurants>()
        firebaseService.db.collection("Restoranlar").get().addOnSuccessListener { documents ->

                for(document in documents){

                    val restaurant = Restaurants(document.id, document.getString("Kategori"),document.getString("Ad"),
                    document.getString("Logo"),null,document.getString("Şehir"),document.getDouble("Puan"), null)
                    list.add(restaurant)

                }

                _restaurantListMutableLiveData.postValue(list)
                _restaurantListLoading.postValue(false)


        }.addOnFailureListener { exception ->
            Log.w("Firebase", "Error getting documents: ", exception)
        }



    }



}