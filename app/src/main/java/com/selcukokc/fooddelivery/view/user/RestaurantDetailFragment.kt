package com.selcukokc.fooddelivery.view.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.selcukokc.fooddelivery.adapters.MenuAdapter
import com.selcukokc.fooddelivery.adapters.UserMenuAdapter
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantDetailBinding
import com.selcukokc.fooddelivery.util.downloadFromUrl
import com.selcukokc.fooddelivery.util.placeholderProgressBar
import com.selcukokc.fooddelivery.viewmodel.userviewmodel.RestaurantDetailViewModel


class RestaurantDetailFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantDetailBinding
    private lateinit var restaurantDetailViewModel: RestaurantDetailViewModel
    private lateinit var menuAdapter: UserMenuAdapter
    private lateinit var restaurantID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restaurantDetailViewModel = ViewModelProvider(this).get(RestaurantDetailViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.menuList.layoutManager = LinearLayoutManager(context)
        binding.menuList.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        menuAdapter = context?.let { UserMenuAdapter(it, arrayListOf()) }!!


        arguments?.let {
            restaurantID = RestaurantDetailFragmentArgs.fromBundle(it).restaurantID
            restaurantDetailViewModel.getDetailInfo(restaurantID)
            restaurantDetailViewModel.getMenus(restaurantID)
        }


        restaurantDetailViewModel.restaurantDetailInfoLiveData.observe(viewLifecycleOwner, Observer { restaurant ->
            binding.txtRestaurantName.text = restaurant.restaurantName.toString()
            binding.txtCategoryName.text = restaurant.category.toString()
            binding.txtRestaurantRating.text = restaurant.rating.toString()
            restaurant.logoUrl?.let {
                binding.imgRestaurantLogo.downloadFromUrl(it, placeholderProgressBar(requireContext()))
            }
        })



        restaurantDetailViewModel.detailLoadingLiveData.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.txtMenus.visibility = View.GONE
                binding.menuList.visibility = View.GONE
                binding.imgRestaurantLogo.visibility = View.GONE
                binding.imgComment.visibility = View.GONE
                binding.imgFavorite.visibility = View.GONE
                binding.txtCategoryName.visibility = View.GONE
                binding.txtRestaurantName.visibility = View.GONE
                binding.txtRestaurantRating.visibility = View.GONE
                binding.detailLoading.visibility = View.VISIBLE
            } else {
                binding.txtMenus.visibility = View.VISIBLE
                binding.menuList.visibility = View.VISIBLE
                binding.imgRestaurantLogo.visibility = View.VISIBLE
                binding.imgComment.visibility = View.VISIBLE
                binding.imgFavorite.visibility = View.VISIBLE
                binding.txtCategoryName.visibility = View.VISIBLE
                binding.txtRestaurantName.visibility = View.VISIBLE
                binding.txtRestaurantRating.visibility = View.VISIBLE
                binding.detailLoading.visibility = View.GONE
            }


        })


        restaurantDetailViewModel.menuMutableLiveData.observe(viewLifecycleOwner, Observer { menulist ->
            menuAdapter = context?.let { UserMenuAdapter(it, menulist) }!!
            binding.menuList.adapter = menuAdapter

        })



    }

}