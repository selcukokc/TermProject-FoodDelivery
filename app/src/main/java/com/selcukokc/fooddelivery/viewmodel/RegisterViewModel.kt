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
import com.selcukokc.fooddelivery.model.User
import com.selcukokc.fooddelivery.service.FirebaseService

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private var _userMutableLiveData = MutableLiveData<FirebaseUser>()
    val userMutableLiveData : LiveData<FirebaseUser>
        get() = _userMutableLiveData

    private var _userinfoMutableLiveData = MutableLiveData<User>()
    val userinfoMutableLiveData : LiveData<User>
        get() = _userinfoMutableLiveData

    private var firebaseService = FirebaseService()


    fun register(
        email: String,
        password: String,
        name: String,
        surname: String,
        city: String,
        favorites: ArrayList<Restaurants>,
        userAddress: String
    ){
        firebaseService.firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener (
            ContextCompat.getMainExecutor(getApplication()),
            {
                if (it.isSuccessful) {
                    val userID = firebaseService.firebaseAuth.currentUser?.uid
                    val documentReference = userID?.let { it1 ->
                        firebaseService.db.collection("Kullanıcılar")
                            .document(it1)
                    }  //bu dizinde kullanıcı verilerini tutacağız.

                    val user = User(userID, name, surname, city, userAddress, favorites)

                    val userinformation: MutableMap<String, Any> = HashMap()
                    userinformation["Ad"] = name
                    userinformation["Soyad"] = surname
                    userinformation["Şehir"] = city
                    userinformation["Favoriler"] = favorites
                    userinformation["Adres"] = userAddress



                    documentReference?.set(userinformation)?.addOnSuccessListener(OnSuccessListener {
                        _userMutableLiveData.postValue(firebaseService.firebaseAuth.currentUser)
                        _userinfoMutableLiveData.postValue(user)


                    })


                } else {
                    Toast.makeText(
                        getApplication(),
                        "Kayıt başarısız oldu: " + it.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

}