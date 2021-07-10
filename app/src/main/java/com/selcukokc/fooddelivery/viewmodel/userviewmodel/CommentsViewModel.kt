package com.selcukokc.fooddelivery.viewmodel.userviewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.selcukokc.fooddelivery.model.Comment
import com.selcukokc.fooddelivery.service.FirebaseService

class CommentsViewModel(application: Application): AndroidViewModel(application) {
    private var _commentsMutableLiveData = MutableLiveData<ArrayList<Comment>>()
    val commentsLiveData: LiveData<ArrayList<Comment>>
        get() = _commentsMutableLiveData

    private var _commentsLoading = MutableLiveData<Boolean>()
    val commentsLoading : LiveData<Boolean>
        get() = _commentsLoading

    val firebaseService = FirebaseService()

    init{
        _commentsLoading.value = true
    }

    fun getComments(restaurantID: String){
        val commentsList = ArrayList<Comment>()
        val collectionRef = firebaseService.db.collection("Restoranlar").
        document(restaurantID).collection("Değerlendirmeler")

        collectionRef.get().addOnSuccessListener { documents->

            for(doc in documents){
                val comment = Comment(doc["KullanıcıAdSoyad"].toString(), doc["Yorum"].toString(),doc["Puan"].toString().toDouble())
                commentsList.add(comment)
            }
            Log.e("cc", commentsList.size.toString())
            _commentsMutableLiveData.postValue(commentsList)
            _commentsLoading.value = false

        }.addOnFailureListener {
            Log.e("CommentsViewModel", it.toString())
        }

    }
}