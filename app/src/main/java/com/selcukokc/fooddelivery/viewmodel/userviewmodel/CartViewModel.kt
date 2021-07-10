package com.selcukokc.fooddelivery.viewmodel.userviewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.selcukokc.fooddelivery.model.CartMenu
import com.selcukokc.fooddelivery.model.Order
import com.selcukokc.fooddelivery.service.FirebaseService
import java.util.*
import kotlin.collections.ArrayList


class CartViewModel(application: Application) : AndroidViewModel(application) {
    private var _menuListMutableLiveData = MutableLiveData<ArrayList<CartMenu>>()
    val menuListMutableLiveData: LiveData<ArrayList<CartMenu>>
        get() = _menuListMutableLiveData

    private var _restaurantNameMutableLivedata = MutableLiveData<String>()
    val restaurantNameLivedata: LiveData<String>
        get() = _restaurantNameMutableLivedata

    val firebaseService = FirebaseService()

    companion object{
        var cartItems = ArrayList<CartMenu>()
    }


    fun addToCart(menu: CartMenu){
        cartItems.add(menu)
        _menuListMutableLiveData.postValue(cartItems)
        Toast.makeText(getApplication(),"Sepete eklendi", Toast.LENGTH_SHORT).show()
    }

    fun removeFromCart(menu: CartMenu){
        cartItems.remove(menu)
        _menuListMutableLiveData.postValue(cartItems)
        Toast.makeText(getApplication(),"Ürün silindi", Toast.LENGTH_SHORT).show()
    }

    fun removeAllMenus(){
        cartItems.clear()
        _menuListMutableLiveData.postValue(cartItems)
    }

    fun selectedMenus(){
        _menuListMutableLiveData.postValue(cartItems)
    }

    fun getRestaurantName(restaurantID: String){
        firebaseService.db.collection("Restoranlar").document(restaurantID).get().addOnSuccessListener {
            _restaurantNameMutableLivedata.value = it["Ad"].toString()
        }
    }


    fun storeOrders(order: Order){

        val userID = firebaseService.firebaseAuth.currentUser?.uid.toString()
        val collectionRef = firebaseService.db.collection("Kullanıcılar").document(userID)
            .collection("Siparişler")
        val orderInfo = hashMapOf(
            "RestoranID" to order.restaurantID,
            "RestoranAd" to order.restaurantName
        )
        val uid = UUID.randomUUID().toString()
        collectionRef.document(uid).set(orderInfo).addOnSuccessListener {
            Log.e("CartViewModel", "Kayıt başarıyla tamamlandı")
        }

        Log.e("CartViewModel", order.orderedMenuList.size.toString())
        if(order.orderedMenuList.isNotEmpty()) {
            for (menu in order.orderedMenuList) {

                val menuInfo = hashMapOf(
                    "MenüAd" to menu.menuTitle,
                    "Adet" to menu.menuAmount,
                    "Fiyat" to menu.menuPrice
                )

                collectionRef.document(uid).collection("Menüler").document().set(menuInfo).addOnSuccessListener {
                    Log.e("CartViewModel", "Kayıt başarıyla tamamlandı")
                }

            }
        }
    }



}