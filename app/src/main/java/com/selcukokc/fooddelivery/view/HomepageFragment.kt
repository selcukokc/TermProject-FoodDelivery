package com.selcukokc.fooddelivery.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.viewmodel.HomepageLiveModel
import kotlinx.android.synthetic.main.fragment_homepage.*

class HomepageFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var homepageLiveModel: HomepageLiveModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homepageLiveModel = ViewModelProvider(this).get(HomepageLiveModel::class.java)

        homepageLiveModel.loggedOutMutableLiveData.observe(this, Observer<Boolean>{
            if(it){
                Toast.makeText(context, "ÇIKIŞ YAPILDI", Toast.LENGTH_SHORT).show()
                val action = HomepageFragmentDirections.actionHomepageFragmentToLoginFragment()
                view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }

            }

        })






    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_homepage, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnLogOut.setOnClickListener {
            homepageLiveModel.logout()

        }

        btnListRestaurants.setOnClickListener {
            Navigation.findNavController(it).navigate(HomepageFragmentDirections.actionHomepageFragmentToRestaurantListFragment())

        }
    }



}