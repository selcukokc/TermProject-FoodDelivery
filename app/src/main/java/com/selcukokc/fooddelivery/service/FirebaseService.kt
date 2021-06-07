package com.selcukokc.fooddelivery.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseService{

    val firebaseAuth: FirebaseAuth
    val restaurantFirebaseAuth: FirebaseAuth
    val db: FirebaseFirestore
    val storage: FirebaseStorage
    val storageReference: StorageReference

    init{
        firebaseAuth = FirebaseAuth.getInstance()
        restaurantFirebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference
    }



}