package com.selcukokc.fooddelivery.viewmodel.userviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.service.FirebaseService

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private var _restaurantListMutableLiveData = MutableLiveData<ArrayList<Restaurants>>()
    val restaurantListMutableLiveData : LiveData<ArrayList<Restaurants>>
        get() = _restaurantListMutableLiveData

    private var _searchQueryMutableLiveData = MutableLiveData<String>()
    val searchQueryLiveData : LiveData<String>
        get() = _searchQueryMutableLiveData


    private var firebaseService = FirebaseService()

    fun getRestaurantResults(query: String){
        val list = ArrayList<Restaurants>()
        firebaseService.db.collection("Restoranlar").whereEqualTo("Ad", query).get().addOnSuccessListener { docs->
            for(doc in docs){
                    val restaurant = Restaurants(
                        doc.id,
                        doc.getString("Kategori"),
                        doc.getString("Ad"),
                        doc.getString("Logo"),
                        null,
                        doc.getString("Şehir"),
                        doc.getDouble("Puan"),
                        null
                    )
                    list.add(restaurant)

            }
            _restaurantListMutableLiveData.postValue(list)

        }

    }


    fun searchQuery(query: String){
        _searchQueryMutableLiveData.postValue(query)
    }

}