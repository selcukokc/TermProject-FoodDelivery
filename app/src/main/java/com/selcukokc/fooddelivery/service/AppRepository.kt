package com.selcukokc.fooddelivery.service

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.view.LoginFragment
import com.selcukokc.fooddelivery.view.LoginFragmentDirections
import com.selcukokc.fooddelivery.view.MainActivity
import kotlinx.android.synthetic.main.fragment_login.view.*
import java.util.zip.Inflater
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AppRepository(val application: Application){

    private var userMutableLiveData : MutableLiveData<FirebaseUser>
    private var userinfoMutableLiveData : MutableLiveData<Array<String>>
    private var loggedOutMutableLiveData: MutableLiveData<Boolean>
    private var firebaseAuth: FirebaseAuth
    private var db: FirebaseFirestore



    init{
        userMutableLiveData = MutableLiveData()
        userinfoMutableLiveData = MutableLiveData()
        loggedOutMutableLiveData = MutableLiveData()
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        if(firebaseAuth.currentUser != null){
            userMutableLiveData.postValue(firebaseAuth.currentUser)
            loggedOutMutableLiveData.postValue(false)
        }
    }




    fun register(
            email: String,
            password: String,
            name: String,
            surname: String,
            city: String,
            favorites: ArrayList<String>,
            userAddress: String
    ){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener (
                ContextCompat.getMainExecutor(application),
            {
                if (it.isSuccessful) {

                    val userID = firebaseAuth.currentUser?.uid
                    val documentReference = userID?.let { it1 ->
                        db.collection("Kullanıcılar")
                                .document(it1)
                    }  //bu dizinde kullanıcı verilerini tutacağız.


                    val arr = arrayOf(name, surname)


                    val userinformation: MutableMap<String, Any> = HashMap()
                    userinformation["Ad"] = name
                    userinformation["Soyad"] = surname
                    userinformation["Şehir"] = city
                    userinformation["Favoriler"] = favorites
                    userinformation["Adres"] = userAddress



                    documentReference?.set(userinformation)?.addOnSuccessListener(OnSuccessListener {
                        userMutableLiveData.postValue(firebaseAuth.currentUser)
                        userinfoMutableLiveData.postValue(arr)


                    })


                } else {
                    Toast.makeText(
                            application,
                            "Kayıt başarısız oldu: " + it.exception?.message,
                            Toast.LENGTH_LONG
                    ).show()
                }
            })

    }


    fun login(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(ContextCompat.getMainExecutor(application),
            {
                  if (it.isSuccessful) {
                      userMutableLiveData.postValue(firebaseAuth.currentUser)

                  } else {
                      Toast.makeText(
                              application,
                              "Giriş başarısız oldu. " + it.exception?.message,
                              Toast.LENGTH_LONG
                      ).show()
                  }

              })


    }


    fun logout(){
        firebaseAuth.signOut()
        loggedOutMutableLiveData.postValue(true)
    }


    fun getUserMutableLiveData(): MutableLiveData<FirebaseUser>{
        return userMutableLiveData
    }

    fun getUserInfoMutableLiveData(): MutableLiveData<Array<String>>{
        return userinfoMutableLiveData
    }

}
