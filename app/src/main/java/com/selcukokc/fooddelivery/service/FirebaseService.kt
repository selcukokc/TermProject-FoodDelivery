package com.selcukokc.fooddelivery.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseService{

    val firebaseAuth: FirebaseAuth
    val restaurantFirebaseAuth: FirebaseAuth
    val db: FirebaseFirestore

    init{
        firebaseAuth = FirebaseAuth.getInstance()
        restaurantFirebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }



}