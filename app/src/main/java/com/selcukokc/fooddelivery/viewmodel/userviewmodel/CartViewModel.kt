package com.selcukokc.fooddelivery.viewmodel.userviewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.selcukokc.fooddelivery.model.CartMenu
import com.selcukokc.fooddelivery.service.FirebaseService


class CartViewModel(application: Application) : AndroidViewModel(application) {
    private var _menuListMutableLiveData = MutableLiveData<ArrayList<CartMenu>>()
    val menuListMutableLiveData: LiveData<ArrayList<CartMenu>>
        get() = _menuListMutableLiveData

    private val firebaseService = FirebaseService()

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

    fun selectedMenus(){
        _menuListMutableLiveData.postValue(cartItems)
    }


}