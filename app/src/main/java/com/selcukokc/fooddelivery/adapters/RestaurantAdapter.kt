package com.selcukokc.fooddelivery.adapters

import android.content.Context
import android.util.Log
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
import com.selcukokc.fooddelivery.databinding.CardDesignRestaurantsBinding
import com.selcukokc.fooddelivery.util.downloadFromUrl
import com.selcukokc.fooddelivery.util.placeholderProgressBar
import com.selcukokc.fooddelivery.view.user.HomepageFragmentDirections
import com.selcukokc.fooddelivery.view.user.RestaurantListFragmentDirections


class RestaurantAdapter(private val mContext : Context, private val restaurantList: ArrayList<Restaurants>) : RecyclerView.Adapter<RestaurantAdapter.CardViewHolder>() {

    inner class CardViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = CardDesignRestaurantsBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.card_design_restaurants,null,false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        with(holder){
            binding.txtRestaurantRating.text = restaurantList[position].rating.toString()
            binding.txtRestaurantName.text = restaurantList[position].restaurantName
            restaurantList[position].logoUrl?.let {
                binding.imgRestaurantLogo.downloadFromUrl(
                    it,
                    placeholderProgressBar(mContext))
            }
            binding.restaurantID.text = restaurantList[position].restaurantId
            binding.txtCategoryName.text = restaurantList[position].category

            binding.restaurantsCardView.setOnClickListener {
                val action = RestaurantListFragmentDirections.
                actionRestaurantListFragmentToRestaurantDetailFragment(holder.binding.restaurantID.text.toString())
                Navigation.findNavController(it).navigate(action)

              }

            }

        }




    }


