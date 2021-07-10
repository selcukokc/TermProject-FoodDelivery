package com.selcukokc.fooddelivery.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.databinding.CardDesignLastOrdersBinding
import com.selcukokc.fooddelivery.model.Order
import com.selcukokc.fooddelivery.model.OrderedMenu
import com.selcukokc.fooddelivery.viewmodel.userviewmodel.HomepageViewModel

class LastOrdersAdapter(private val mContext: Context, private val orderList: ArrayList<Order>,private val homepageViewModel: HomepageViewModel ): RecyclerView.Adapter<LastOrdersAdapter.CardViewHolder>(){

    inner class CardViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = CardDesignLastOrdersBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_design_last_orders, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        with(holder){
            if(orderList.isNotEmpty()) {
                holder.binding.txtRestaurantName.text = orderList[position].restaurantName
                holder.binding.restaurantID.text = orderList[position].restaurantID
            }
            val orderedMenusList: ArrayList<OrderedMenu> = orderList[position].orderedMenuList
            holder.binding.orderedMenuList.layoutManager = LinearLayoutManager(mContext)
            val orderedMenusAdapter = OrderedMenusAdapter(orderedMenusList)
            holder.binding.orderedMenuList.adapter = orderedMenusAdapter

            holder.binding.cartView.setOnClickListener {
                val builder = MaterialAlertDialogBuilder(mContext)
                builder.setTitle("Değerlendirme Yap")
                val customLayout = (mContext as Activity).layoutInflater.inflate(R.layout.comment_alert, null)
                builder.setView(customLayout)

                val rating = customLayout.findViewById<RatingBar>(R.id.ratingBar)
                val comment = customLayout.findViewById<EditText>(R.id.etComment)

                builder.setPositiveButton("Onayla") { _, _ ->
                    val restaurantID = binding.restaurantID.text.toString()
                    val commentText = comment.text.toString()

                    if(commentText.isNotEmpty() || rating.rating.toDouble() != 0.0) {
                        homepageViewModel.postComment(
                            restaurantID,
                            commentText,
                            rating.rating.toDouble() * 2
                        )
                        homepageViewModel.calculateRating(restaurantID)
                    }
                    else{
                        Toast.makeText(mContext, "Tüm alanları doldurduğunuzdan emin olun",Toast.LENGTH_SHORT).show()
                    }

                }

                builder.setNegativeButton("İptal") { _, _ ->

                }


                val dialog = builder.create();
                dialog.show();

            }
        }

    }

    override fun getItemCount(): Int {
        return orderList.size
    }


}