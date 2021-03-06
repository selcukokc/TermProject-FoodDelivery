package com.selcukokc.fooddelivery.viewmodel.userviewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.selcukokc.fooddelivery.model.Order
import com.selcukokc.fooddelivery.model.OrderedMenu
import com.selcukokc.fooddelivery.model.User
import com.selcukokc.fooddelivery.service.FirebaseService

class HomepageViewModel(application: Application) : AndroidViewModel(application) {

    private var _userMutableLiveData = MutableLiveData<FirebaseUser>()
    val userMutableLiveData : LiveData<FirebaseUser>
        get() = _userMutableLiveData

    private var _loggedOutMutableLiveData = MutableLiveData<Boolean>()
    val loggedOutMutableLiveData : LiveData<Boolean>
        get() = _loggedOutMutableLiveData

    private var _userInfoMutableLiveData = MutableLiveData<User>()
    val userInfoLiveData : LiveData<User>
        get() = _userInfoMutableLiveData

    private val _orderListMutableLiveData = MutableLiveData<ArrayList<Order>>()
    val orderListLiveData : LiveData<ArrayList<Order>>
        get() = _orderListMutableLiveData

    private val _loadingOrdersMutableLiveData = MutableLiveData<Boolean>()
    val loadingOrdersLiveData : LiveData<Boolean>
        get() = _loadingOrdersMutableLiveData

    private var firebaseService = FirebaseService()

    init{
        _loadingOrdersMutableLiveData.value = true

        if(firebaseService.firebaseAuth.currentUser != null){
            _userMutableLiveData.postValue(firebaseService.firebaseAuth.currentUser)
            _loggedOutMutableLiveData.postValue(false)
        }
    }


    fun logout(){
        firebaseService.firebaseAuth.signOut()
        _loggedOutMutableLiveData.postValue(true)
    }

    fun getUserInformation(){
        val userid = firebaseService.firebaseAuth.currentUser?.uid
        userid?.let { firebaseService.db.collection("Kullan??c??lar").document(it).get()
            .addOnCompleteListener {  task->

                if(task.isSuccessful){
                    val document = task.result
                    val user = User(userid, document.getString("Ad"),document.getString("Soyad"),null, null, null)
                    _userInfoMutableLiveData.postValue(user)
                }

                else {
                    Log.e("LOGGER", "get failed with ", task.exception);
                }



        } }


    }


    fun getLastOrders(){
        val orderList = ArrayList<Order>()
        val menuList = ArrayList<OrderedMenu>()
        val userID = firebaseService.firebaseAuth.currentUser?.uid
        val collectionRef = userID?.let { firebaseService.db.collection("Kullan??c??lar").document(it).collection("Sipari??ler") }

        collectionRef?.get()
            ?.addOnSuccessListener { documents->
                for(doc in documents){

                    collectionRef.document(doc.id).collection("Men??ler").get().addOnSuccessListener { docs->
                        for(item in docs){
                            val menu = OrderedMenu(
                                item.get("Men??Ad").toString(),
                                item.get("Adet").toString().toInt(),
                                item.get("Fiyat").toString().toDouble()
                            )
                            menuList.add(menu)
                        }

                        val order = Order(doc.get("RestoranID").toString(),doc.get("RestoranAd").toString(),menuList)
                        orderList.add(order)
                        _orderListMutableLiveData.value = orderList
                    }

                }

            }
        _loadingOrdersMutableLiveData.value = false
    }


    fun postComment(restaurantID: String, comment: String, rating: Double){
        val restaurantDocRef = firebaseService.db.collection("Restoranlar").document(restaurantID)
        val reviewDocRef = restaurantDocRef.collection("De??erlendirmeler").document()
        val userID = firebaseService.firebaseAuth.currentUser?.uid

        userID?.let { firebaseService.db.collection("Kullan??c??lar").document(it).get()
            .addOnSuccessListener { doc->
                val userFullName = doc["Ad"].toString() + " " + doc["Soyad"].toString()

                val commentInfo = hashMapOf(
                    "Kullan??c??AdSoyad" to userFullName,
                    "Kullan??c??ID" to userID,
                    "Yorum" to comment,
                    "Puan" to rating
                )

                reviewDocRef.set(commentInfo).addOnSuccessListener {
                    Toast.makeText(getApplication() as Context, "De??erlendirme yap??ld??", Toast.LENGTH_SHORT).show()
                }

        } }


    }


    fun calculateRating(restaurantID: String){
        var total = 0.0
        var average: Double
        var counter = 0
        val collectionRef = firebaseService.db.collection("Restoranlar")
            .document(restaurantID).collection("De??erlendirmeler")


        collectionRef.get().addOnSuccessListener { docs->
            for(doc in docs){
                total += doc["Puan"].toString().toDouble()
                counter++
            }

            average = total/counter
            firebaseService.db.collection("Restoranlar").document(restaurantID).update("Puan",average)

        }


    }

}


