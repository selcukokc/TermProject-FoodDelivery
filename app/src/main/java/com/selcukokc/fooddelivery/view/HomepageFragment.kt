package com.selcukokc.fooddelivery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.selcukokc.fooddelivery.databinding.FragmentHomepageBinding
import com.selcukokc.fooddelivery.viewmodel.HomepageLiveModel


class HomepageFragment : BaseFragment() {
    private lateinit var binding: FragmentHomepageBinding

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
        binding = FragmentHomepageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnLogOut.setOnClickListener {
            homepageLiveModel.logout()

        }

        binding.btnListRestaurants.setOnClickListener {
            Navigation.findNavController(it).navigate(HomepageFragmentDirections.actionHomepageFragmentToRestaurantListFragment())

        }
    }



}