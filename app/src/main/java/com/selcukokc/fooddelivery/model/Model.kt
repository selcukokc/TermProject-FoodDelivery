package com.selcukokc.fooddelivery.model

import java.io.Serializable

data class User(
    val userId : String?,
    val ad : String?,
    val soyad : String?,
    val sehir : String?,
    val adres : String?,
    val favoriler : ArrayList<Restaurants>?
) : Serializable{

}

data class Restaurants (
    val restoranId : String?,
    val kategori : String?,
    val restoranAd : String?,
    val logoUrl : String?,
    val menuArr : ArrayList<Menu>,
    val sehirler : ArrayList<String>?,
    val puan : Double?,
    val yorumlar : ArrayList<String>?
) : Serializable {

}

data class Menu(
    val menuId : String?,
    val menuAd : String?,
    val menuAciklama: String?,
    val menuFiyat : Double?
) : Serializable {

}



















