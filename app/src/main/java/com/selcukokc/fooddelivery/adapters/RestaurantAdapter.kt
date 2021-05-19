package com.selcukokc.fooddelivery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.view.RestaurantListFragmentDirections


class RestaurantAdapter(private val mContext : Context, private val restaurantList: ArrayList<Restaurants>) : RecyclerView.Adapter<RestaurantAdapter.CardViewHolder>() {

    inner class CardViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var txtRestaurantName : TextView
        var txtRestaurantRating : TextView
        var txtCategoryName : TextView
        var imgFavorite : ImageView
        var imgRestaurantLogo : ImageView
        var cardView : CardView

        init{
            txtRestaurantName = view.findViewById(R.id.txtRestaurantName)
            txtRestaurantRating = view.findViewById(R.id.txtRestaurantRating)
            txtCategoryName = view.findViewById(R.id.txtCategoryName)
            imgFavorite = view.findViewById(R.id.imgFavorite)
            imgRestaurantLogo = view.findViewById(R.id.imgRestaurantLogo)
            cardView = view.findViewById(R.id.restaurantsCardView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.card_design_restaurants,null,false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.txtCategoryName.text = restaurantList[position].kategori
        holder.txtRestaurantName.text = restaurantList[position].restoranAd
        holder.txtRestaurantRating.text = restaurantList[position].puan.toString()


        holder.cardView.setOnClickListener {
           // val action = RestaurantListFragmentDirections.actionRestaurantListFragmentToRestaurantDetailFragment(restaurantList[position])
           // Navigation.findNavController(it).navigate(action)


        }



    }


}