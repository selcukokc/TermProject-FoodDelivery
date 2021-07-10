package com.selcukokc.fooddelivery.viewmodel.userviewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.service.FirebaseService

class RestaurantDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var _restaurantDetailInfoMutableLiveData = MutableLiveData<Restaurants>()
    val restaurantDetailInfoLiveData: LiveData<Restaurants>
        get() = _restaurantDetailInfoMutableLiveData

    private var _menuMutableLiveData = MutableLiveData<ArrayList<Menu>>()
    val menuMutableLiveData: LiveData<ArrayList<Menu>>
        get() = _menuMutableLiveData

    private var _detailLoadingMutableLiveData = MutableLiveData<Boolean>()
    val detailLoadingLiveData: LiveData<Boolean>
        get() = _detailLoadingMutableLiveData

    private var _menuLoadingMutableLiveData = MutableLiveData<Boolean>()
    val menuLoadingLiveData: LiveData<Boolean>
        get() = _menuLoadingMutableLiveData

    private var firebaseService = FirebaseService()

    init {
        _detailLoadingMutableLiveData.postValue(true)
        _menuLoadingMutableLiveData.postValue(true)
    }

    fun getMenus(restaurantID: String){
        val menuArrayList = arrayListOf<Menu>()

        firebaseService.db.collection("Restoranlar").document(restaurantID).collection("Menüler").get()
            .addOnSuccessListener { documents ->

                for(document in documents){
                    val menu = Menu(restaurantID, document.id ,document.get("Başlık").toString(),
                        document.get("Detay").toString(), document.get("Fiyat").toString().toDouble(),
                        document.get("Görsel").toString())
                    menuArrayList.add(menu)
                }

                _menuMutableLiveData.postValue(menuArrayList)
                _menuLoadingMutableLiveData.postValue(false)

            }.addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
            }
    }




    fun getDetailInfo(restaurantID: String){
        var restaurant : Restaurants?
        firebaseService.db.collection("Restoranlar").document(restaurantID).get().addOnCompleteListener {
          if(it.isSuccessful) {
              val document = it.result

            if(document!=null){
                restaurant = Restaurants(restaurantID, document.getString("Kategori"),document.getString("Ad"),
                    document.getString("Logo"),null, document.getString("Şehir"), document.getDouble("Puan"),
                    null)

                _restaurantDetailInfoMutableLiveData.postValue(restaurant)
                _detailLoadingMutableLiveData.postValue(false)


            } else {
                Log.e("LOGGER", "No such document");
            }


          } else {
              Log.e("LOGGER", "get failed with ", it.exception);
          }


        }


    }


}