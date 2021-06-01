package com.selcukokc.fooddelivery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.R


class MenuAdapter(private val mContext : Context, private val menuList: ArrayList<Menu>) : RecyclerView.Adapter<MenuAdapter.CardViewHolder>()  {

    inner class CardViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var txtMenuName : TextView
        var txtMenuDesc : TextView
        var txtMenuPrice : TextView
        var txtNumberOfMenu : TextView
        var imgBtnAdd : ImageButton

        init{
            txtMenuName = view.findViewById(R.id.txtMenuName)
            txtMenuDesc = view.findViewById(R.id.txtMenuDesc)
            txtMenuPrice = view.findViewById(R.id.txtMenuPrice)
            txtNumberOfMenu = view.findViewById(R.id.txtNumberOfMenu)
            imgBtnAdd = view.findViewById(R.id.imgBtnAdd)
        }

    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.card_design_menus,null,false)
            return CardViewHolder(view)
        }

        override fun getItemCount(): Int {
            return menuList.size
        }


        override fun onBindViewHolder(holder: MenuAdapter.CardViewHolder, position: Int) {



        }

        }