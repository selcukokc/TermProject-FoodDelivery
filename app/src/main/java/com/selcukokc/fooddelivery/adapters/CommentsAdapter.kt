package com.selcukokc.fooddelivery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.databinding.CardDesignCommentsBinding
import com.selcukokc.fooddelivery.model.Comment

class CommentsAdapter(private val mContext: Context, private val commentList :ArrayList<Comment>):
    RecyclerView.Adapter<CommentsAdapter.CardViewHolder>() {

        inner class CardViewHolder(view: View): RecyclerView.ViewHolder(view){
            val binding = CardDesignCommentsBinding.bind(view)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.card_design_comments, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        with(holder){
            binding.txtUserName.text = commentList[position].userName
            binding.txtReview.text = commentList[position].comment
            binding.rating.rating = commentList[position].rating.toFloat()/2
        }
    }

    override fun getItemCount(): Int {
        return commentList.size
    }


}