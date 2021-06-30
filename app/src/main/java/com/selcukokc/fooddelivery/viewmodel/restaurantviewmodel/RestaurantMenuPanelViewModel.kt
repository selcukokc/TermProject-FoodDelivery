package com.selcukokc.fooddelivery.viewmodel.restaurantviewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.service.FirebaseService
import java.util.*
import kotlin.collections.ArrayList

class RestaurantMenuPanelViewModel(applicaton : Application) : AndroidViewModel(applicaton) {

    private var _addMenuMutableLiveData = MutableLiveData<Menu>()
    val addMenuMutableLiveData : LiveData<Menu>
        get() = _addMenuMutableLiveData

    private var _restaurantMenuListMutableLiveData = MutableLiveData<ArrayList<Menu>>()
    val restaurantMenuListMutableLiveData : LiveData<ArrayList<Menu>>
        get() = _restaurantMenuListMutableLiveData

    private var _restaurantMenuError = MutableLiveData<Boolean>()
    val restaurantMenuError : LiveData<Boolean>
        get() = _restaurantMenuError

    private var _restaurantMenuLoading = MutableLiveData<Boolean>()
    val restaurantMenuLoading : LiveData<Boolean>
        get() = _restaurantMenuLoading

    private var _updateMenu = MutableLiveData<Boolean>()
    val updateMenu : LiveData<Boolean>
        get() = _updateMenu

    private var _deleteMenu = MutableLiveData<Boolean>()
    val deleteMenu : LiveData<Boolean>
        get() = _deleteMenu

    private var _menuImage = MutableLiveData<Uri>()
    val menuImage : LiveData<Uri>
        get() = _menuImage

    private val firebaseService = FirebaseService()

    init{
        _restaurantMenuLoading.postValue(true)
        _restaurantMenuError.postValue(false)
    }

    fun addMenu(title: String, description: String, price: Double, uri: Uri){
        val userID = firebaseService.restaurantFirebaseAuth.currentUser?.uid
        val randomKey = UUID.randomUUID().toString()
        val ref = firebaseService.storageReference.child("menu_images/$randomKey")

        ref.putFile(uri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    userID?.let { id ->

                        val menuInfo = hashMapOf(
                            "Başlık" to title,
                            "Detay" to description,
                            "Fiyat" to price,
                            "Görsel" to it.toString()
                        )

                        val docRef = firebaseService.db.collection("Restoranlar").document(id)
                            .collection("Menüler").document()

                        docRef.set(menuInfo)
                            .addOnSuccessListener {
                                val menuID = docRef.id
                                val menu = Menu(menuID, title, description, price, "")
                                _addMenuMutableLiveData.postValue(menu)
                            }
                            .addOnFailureListener { e -> Log.w("Firebase", "Error writing document", e) }

                    }
                }

            }
            .addOnFailureListener{
                Log.e("Firebase", "an error has occurred while putting image to storage")
            }


    }


    fun showMenus(){
        val userID = firebaseService.restaurantFirebaseAuth.currentUser?.uid
        val menuArrayList = arrayListOf<Menu>()
        userID?.let {
            firebaseService.db.collection("Restoranlar").document(it).collection("Menüler").get()
                .addOnSuccessListener { documents ->

                    for(document in documents){ 
                        val menu = Menu(document.id ,document.get("Başlık").toString(),
                            document.get("Detay").toString(), document.get("Fiyat").toString().toDouble(),
                            document.get("Görsel").toString())
                        menuArrayList.add(menu)
                    }

                    _restaurantMenuListMutableLiveData.postValue(menuArrayList)
                    _restaurantMenuLoading.postValue(false)

                }.addOnFailureListener { exception ->
                    Log.w("Firebase", "Error getting documents: ", exception)
                }
        }



    }

    fun updateMenu(id: String, title: String, description: String, price: Double){
        val userID = firebaseService.restaurantFirebaseAuth.currentUser?.uid
        val menuRef = userID?.let { userID
            firebaseService.db.collection("Restoranlar").document(userID).collection("Menüler")
                .document(id)
        }

        menuRef?.update(mapOf(
            "Başlık" to title,
            "Detay" to description,
            "Fiyat" to price
        ))?.addOnSuccessListener {
            _updateMenu.postValue(true)
        }?.addOnFailureListener {
            Toast.makeText(getApplication(), "Güncelleme hatası", Toast.LENGTH_SHORT).show()
        }


    }



    fun deleteMenu(id: String){
        val userID = firebaseService.restaurantFirebaseAuth.currentUser?.uid
        val menuRef = userID?.let { userID
            firebaseService.db.collection("Restoranlar").document(userID).collection("Menüler")
                .document(id)
        }

        menuRef?.delete()?.addOnSuccessListener {
            _deleteMenu.postValue(true)
        }

    }

    fun updateMenuImage(uri: Uri){
        _menuImage.postValue(uri)
    }

    fun getMenuImageUri(): Uri?{
        return _menuImage.value
    }

}