package com.selcukokc.fooddelivery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.databinding.CardDesignRestaurantsBinding
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.util.downloadFromUrl
import com.selcukokc.fooddelivery.util.placeholderProgressBar
import com.selcukokc.fooddelivery.view.user.RestaurantListFragmentDirections
import com.selcukokc.fooddelivery.view.user.SearchFragmentDirections

class SearchAdapter(private val mContext : Context, private val restaurantList: ArrayList<Restaurants>) : RecyclerView.Adapter<SearchAdapter.CardViewHolder>() {

    inner class CardViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = CardDesignRestaurantsBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.CardViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.card_design_search,null,false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchAdapter.CardViewHolder, position: Int) {
        with(holder){
            binding.txtRestaurantName.text = restaurantList[position].restaurantName
            binding.txtCategoryName.text = restaurantList[position].category
            binding.txtRestaurantRating.text = restaurantList[position].rating.toString()
            binding.imgRestaurantLogo.downloadFromUrl(restaurantList[position].logoUrl.toString(), placeholderProgressBar(mContext))
            binding.restaurantID.text = restaurantList[position].restaurantId

            binding.restaurantsCardView.setOnClickListener {
                val action = SearchFragmentDirections.
                actionSearchFragmentToRestaurantDetailFragment(holder.binding.restaurantID.text.toString())
                Navigation.findNavController(it).navigate(action)
            }

        }


    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }


}