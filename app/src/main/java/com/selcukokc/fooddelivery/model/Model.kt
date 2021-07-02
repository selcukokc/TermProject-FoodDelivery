package com.selcukokc.fooddelivery.model

import android.net.Uri
import java.io.Serializable

data class User(
    val userId : String?,
    val name : String?,
    val surname : String?,
    val city : String?,
    val address : String?,
    val favorites : ArrayList<Restaurants>?
) : Serializable

data class Restaurants (
    val restaurantId : String?,
    val category : String?,
    val restaurantName : String?,
    val logoUrl : String?,
    val menuArr : ArrayList<Menu>?,
    val city : String?,
    val rating : Double?,
    val comments : ArrayList<String>?
) : Serializable

data class Menu(
    val menuId : String?,
    val title : String?,
    val description: String?,
    val price : Double?,
    val imageURL: String?
) : Serializable

data class CartMenu(
    val menuId : String,
    val title : String,
    val description: String,
    val price : Double,
    val amount: Int
) : Serializable



















