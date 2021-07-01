package com.selcukokc.fooddelivery.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.databinding.CardDesignMenusBinding
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.util.downloadFromUrl
import com.selcukokc.fooddelivery.util.placeholderProgressBar


class UserMenuAdapter (private val mContext : Context, private val menuList: ArrayList<Menu>)
    : RecyclerView.Adapter<UserMenuAdapter.CardViewHolder>(), OnClickImageListener {

    inner class CardViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = CardDesignMenusBinding.bind(view)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMenuAdapter.CardViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.card_design_menus,null,false)
        return CardViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserMenuAdapter.CardViewHolder, position: Int) {
      with(holder){
          binding.txtMenuTitle.text = menuList[position].title
          binding.txtMenuDesc.text = menuList[position].description
          binding.txtMenuPrice.text = menuList[position].price.toString() + "TL"
          menuList[position].imageURL?.let { binding.menuImg.downloadFromUrl(it, placeholderProgressBar(mContext)) }

          binding.btnAddToCart.setOnClickListener {



          }




      }



    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onClick() {
        TODO("Not yet implemented")
    }


}