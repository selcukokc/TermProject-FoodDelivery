package com.selcukokc.fooddelivery.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.service.FirebaseService


class RestaurantRegisterViewModel(application: Application) : AndroidViewModel(application) {

    private var _restaurantUserMutableLiveData = MutableLiveData<FirebaseUser>()
    val restaurantUserMutableLiveData : LiveData<FirebaseUser>
        get() = _restaurantUserMutableLiveData

    private var _restaurantUserInfoMutableLiveData = MutableLiveData<Restaurants>()
    val restaurantUserInfoMutableLiveData : LiveData<Restaurants>
        get() = _restaurantUserInfoMutableLiveData

    private var firebaseService = FirebaseService()


    fun restaurantRegister(email: String, password: String, restaurantName: String, category: String, city: String, address: String,
                           comments: ArrayList<String>, rating: Double, logo: String){
        var restaurant: Restaurants?
        firebaseService.restaurantFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( ContextCompat.getMainExecutor(getApplication()),{
            if(it.isSuccessful){
                val userID = firebaseService.restaurantFirebaseAuth.currentUser?.uid
                val documentReference = userID?.let { it1 ->
                    firebaseService.db.collection("Restoranlar")
                        .document(it1)
                }

                restaurant = Restaurants(userID, category, restaurantName, logo, null, city, rating, null)

                val userinformation: MutableMap<String, Any> = HashMap()
                userinformation["Ad"] = restaurantName
                userinformation["Kategori"] = category
                userinformation["Şehir"] = city
                userinformation["Adres"] = address
                userinformation["Yorumlar"] = comments
                userinformation["Puan"] = rating
                userinformation["Logo"] = logo

                documentReference?.set(userinformation)?.addOnSuccessListener(OnSuccessListener {
                    _restaurantUserMutableLiveData.postValue(firebaseService.restaurantFirebaseAuth.currentUser)
                    _restaurantUserInfoMutableLiveData.postValue(restaurant)

                })
            } else {
                Toast.makeText(
                    getApplication(),
                    "Kayıt başarısız oldu: " + it.exception?.message,
                    Toast.LENGTH_LONG
                ).show()
            }

        }


        )

    }




}