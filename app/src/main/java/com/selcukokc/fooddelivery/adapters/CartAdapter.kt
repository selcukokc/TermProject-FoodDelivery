package com.selcukokc.fooddelivery.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.databinding.CardDesignCartBinding
import com.selcukokc.fooddelivery.model.CartMenu
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.viewmodel.userviewmodel.CartViewModel

class CartAdapter(private val mContext: Context, private val menuList: ArrayList<CartMenu>, private val cartViewModel: CartViewModel) : RecyclerView.Adapter<CartAdapter.CardViewHolder>() {

    inner class CardViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = CardDesignCartBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.card_design_cart,null,false)
        return CardViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
       with(holder){
           binding.txtMenuTitle.text = menuList[position].title.toString()
           binding.txtMenuDesc.text = menuList[position].description.toString()
           binding.txtAmountOfMenu.text = menuList[position].amount.toString()
           binding.menuID.text = menuList[position].menuId.toString()
           val totalPrice = menuList[position].price.toString().toDouble() * menuList[position].amount.toString().toDouble()
           binding.txtPrice.text = totalPrice.toString() + "TL"
           binding.restaurantID.text = menuList[position].restaurantID

           binding.btnRemoveItem.setOnClickListener {
               val menu = CartMenu(binding.restaurantID.text.toString(), binding.menuID.text.toString(),binding.txtMenuTitle.text.toString(),binding.txtMenuDesc.text.toString()
                   ,totalPrice,binding.txtAmountOfMenu.text.toString().toInt())
               cartViewModel.removeFromCart(menu)
               cartViewModel.selectedMenus()
           }

       }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }


}
