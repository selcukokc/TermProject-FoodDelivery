package com.selcukokc.fooddelivery.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.databinding.CardDesignLastOrdersBinding
import com.selcukokc.fooddelivery.databinding.CardDesignOrderedMenuBinding
import com.selcukokc.fooddelivery.model.OrderedMenu

class OrderedMenusAdapter(private val menuList: ArrayList<OrderedMenu>) : RecyclerView.Adapter<OrderedMenusAdapter.CardViewHolder>() {

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = CardDesignOrderedMenuBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_design_ordered_menu, parent ,false)
        return CardViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        with(holder){
            binding.menuName.text = menuList[position].menuTitle
            binding.amountOfMenu.text = menuList[position].menuAmount.toString()
            binding.menuPrice.text = menuList[position].menuPrice.toString() + " TL"
        }

    }

    override fun getItemCount(): Int {
        return menuList.size
    }


}