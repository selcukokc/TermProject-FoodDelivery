package com.selcukokc.fooddelivery.view.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.selcukokc.fooddelivery.adapters.LastOrdersAdapter
import com.selcukokc.fooddelivery.databinding.FragmentHomepageBinding
import com.selcukokc.fooddelivery.view.BaseFragment
import com.selcukokc.fooddelivery.viewmodel.userviewmodel.HomepageViewModel


class HomepageFragment : BaseFragment() {
    private lateinit var binding: FragmentHomepageBinding
    private lateinit var lastOrdersAdapter: LastOrdersAdapter
    override var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var homepageViewModel: HomepageViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homepageViewModel = ViewModelProvider(this).get(HomepageViewModel::class.java)

        homepageViewModel.loggedOutMutableLiveData.observe(this, Observer<Boolean>{
            if(it){
                Toast.makeText(context, "ÇIKIŞ YAPILDI", Toast.LENGTH_SHORT).show()
                val action = HomepageFragmentDirections.actionHomepageFragmentToLoginFragment()
                view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }

            }

        })

        homepageViewModel.userInfoLiveData.observe(this, Observer {
            binding.txtNameSurname.text = it.name + " " + it.surname

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

        homepageViewModel.loadingOrdersLiveData.observe(viewLifecycleOwner, {
            if(it){
                binding.progressBar.visibility = View.VISIBLE
                binding.orderList.visibility = View.INVISIBLE
            }
            else{
                binding.progressBar.visibility = View.INVISIBLE
                binding.orderList.visibility = View.VISIBLE
            }
        })

        homepageViewModel.getUserInformation()
        homepageViewModel.getLastOrders()

        binding.orderList.layoutManager = LinearLayoutManager(context)
        binding.orderList.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        lastOrdersAdapter = context?.let { LastOrdersAdapter(it, arrayListOf(), homepageViewModel) }!!
        binding.orderList.adapter = lastOrdersAdapter

        binding.btnLogOut.setOnClickListener {
            homepageViewModel.logout()
        }

        binding.btnListRestaurants.setOnClickListener {
            Navigation.findNavController(it).navigate(HomepageFragmentDirections.actionHomepageFragmentToRestaurantListFragment())
        }



        homepageViewModel.orderListLiveData.observe(viewLifecycleOwner, { orderList ->
            lastOrdersAdapter = context?.let { LastOrdersAdapter(it, orderList, homepageViewModel) }!!
            binding.orderList.adapter = lastOrdersAdapter

        })
    }



}