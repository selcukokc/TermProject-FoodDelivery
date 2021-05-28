package com.selcukokc.fooddelivery.service

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_login.view.*


class AppRepository(val application: Application){

    private var userMutableLiveData : MutableLiveData<FirebaseUser>
    private var userinfoMutableLiveData : MutableLiveData<Array<String>>
    private var loggedOutMutableLiveData: MutableLiveData<Boolean>
    private var firebaseAuth: FirebaseAuth
    private var db: FirebaseFirestore

    private var restaurantUserMutableLiveData: MutableLiveData<FirebaseUser>
    private var restaurantFirebaseAuth: FirebaseAuth
    private var restLoggedOutMutableLiveData: MutableLiveData<Boolean>
    private var restaurantInformationMutableLiveData: MutableLiveData<ArrayList<String>>
    private var restaurantUserInfoMutableLiveData: MutableLiveData<Array<String>>

    init{
        userMutableLiveData = MutableLiveData()
        userinfoMutableLiveData = MutableLiveData()
        loggedOutMutableLiveData = MutableLiveData()
        firebaseAuth = FirebaseAuth.getInstance()

        restaurantUserMutableLiveData = MutableLiveData()
        restaurantFirebaseAuth = FirebaseAuth.getInstance()
        restLoggedOutMutableLiveData = MutableLiveData()
        db = FirebaseFirestore.getInstance()

        restaurantInformationMutableLiveData = MutableLiveData()
        restaurantUserInfoMutableLiveData = MutableLiveData()

        if(firebaseAuth.currentUser != null){
            userMutableLiveData.postValue(firebaseAuth.currentUser)
            loggedOutMutableLiveData.postValue(false)
        }

        if(restaurantFirebaseAuth.currentUser != null){
            restaurantUserMutableLiveData.postValue(firebaseAuth.currentUser)
            restLoggedOutMutableLiveData.postValue(false)
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

    fun restaurantRegister(email: String, password: String, restaurantName: String, category: String, city: String, address: String,
    comments: ArrayList<String>, rating: Double, logo: String){
        restaurantFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( ContextCompat.getMainExecutor(application),{
            if(it.isSuccessful){
                val userID = restaurantFirebaseAuth.currentUser?.uid
                val documentReference = userID?.let { it1 ->
                    db.collection("Restoranlar")
                        .document(it1)
                }

                val arrInfo = arrayOf(restaurantName, category, city, address)


                val userinformation: MutableMap<String, Any> = HashMap()
                userinformation["Ad"] = restaurantName
                userinformation["Kategori"] = category
                userinformation["Şehir"] = city
                userinformation["Adres"] = address
                userinformation["Yorumlar"] = comments
                userinformation["Puan"] = rating
                userinformation["Logo"] = logo

                documentReference?.set(userinformation)?.addOnSuccessListener(OnSuccessListener {
                    restaurantUserMutableLiveData.postValue(restaurantFirebaseAuth.currentUser)
                    restaurantUserInfoMutableLiveData.postValue(arrInfo)

                })
            } else {
                Toast.makeText(
                    application,
                    "Kayıt başarısız oldu: " + it.exception?.message,
                    Toast.LENGTH_LONG
                ).show()
            }

        }


        )

    }


    fun restaurantLogin(email: String, password: String){
        restaurantFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(ContextCompat.getMainExecutor(application),
            {
                if (it.isSuccessful) {
                    restaurantUserMutableLiveData.postValue(restaurantFirebaseAuth.currentUser)

                } else {
                    Toast.makeText(
                        application,
                        "Giriş başarısız oldu. " + it.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

            })

    }

    fun restaurantInformation(){
        var list = ArrayList<String>()
        val userID=restaurantFirebaseAuth.currentUser?.uid
        userID?.let { id->
            val docRef = db.collection("Restoranlar").document("BVBE3PuZKTaCMwvKz2lO")
            docRef.get().addOnCompleteListener {
                if(it.isSuccessful){
                    val document = it.result
                     if(document!=null){
                         document.getString("RestoranAd")?.let { it1 -> list.add(it1) }
                         document.getString("logo")?.let{ it2 -> list.add(it2) }
                     } else {
                         Log.e("LOGGER", "No such document");
                     }

                    restaurantInformationMutableLiveData.postValue(list)


                } else {
                    Log.e("LOGGER", "get failed with ", it.exception);
                }

            }


        }

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

    fun getRestaurantUserMutableLiveData(): MutableLiveData<FirebaseUser>{
        return restaurantUserMutableLiveData
    }

    fun getRestaurantLoggedOutMutableLiveData(): MutableLiveData<Boolean>{
        return  restLoggedOutMutableLiveData
    }

    fun getRestaurantInformationMutableLiveData(): MutableLiveData<ArrayList<String>>{
        return restaurantInformationMutableLiveData
    }


}
