package com.selcukokc.fooddelivery.view.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.selcukokc.fooddelivery.adapters.CommentsAdapter
import com.selcukokc.fooddelivery.databinding.FragmentCommentsBinding
import com.selcukokc.fooddelivery.viewmodel.userviewmodel.CommentsViewModel

class CommentsFragment : Fragment() {
    private lateinit var commentsViewModel: CommentsViewModel
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var binding: FragmentCommentsBinding
    private lateinit var restaurantID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("CommentsFragment", "onCreate çalıştı")

        commentsViewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)
        arguments?.let {
            restaurantID = CommentsFragmentArgs.fromBundle(it).restaurantID
            commentsViewModel.getComments(restaurantID)
            Log.e("restoranID: ", restaurantID)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.commentsList.layoutManager = LinearLayoutManager(context)
        binding.commentsList.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        commentsAdapter = context?.let { CommentsAdapter(it, arrayListOf()) }!!

        commentsViewModel.commentsLoading.observe(viewLifecycleOwner, {
            if(it){
                binding.commentsLoading.visibility = View.VISIBLE
                binding.commentsList.visibility = View.INVISIBLE

            } else {
                binding.commentsLoading.visibility = View.INVISIBLE
                binding.commentsList.visibility = View.VISIBLE
            }

        })

        commentsViewModel.commentsLiveData.observe(viewLifecycleOwner, { commentsList ->
                Log.e("CommentsListSize", commentsList.size.toString() )
                commentsAdapter = context?.let { CommentsAdapter(it, commentsList) }!!
                binding.commentsList.adapter = commentsAdapter
        })

    }


}