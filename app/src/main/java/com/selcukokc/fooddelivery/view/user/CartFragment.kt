package com.selcukokc.fooddelivery.view.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.selcukokc.fooddelivery.adapters.CartAdapter
import com.selcukokc.fooddelivery.databinding.FragmentCartBinding
import com.selcukokc.fooddelivery.view.BaseFragment
import com.selcukokc.fooddelivery.viewmodel.userviewmodel.CartViewModel


class CartFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var cartViewModel: CartViewModel
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var totalPrice: Double = 0.0

        binding.txtTotalPrice.text = "Toplam tutar: " + totalPrice.toString() + " TL"
        binding.cartMenuList.layoutManager = LinearLayoutManager(context)
        binding.cartMenuList.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        cartAdapter = context?.let { CartAdapter(it, arrayListOf(), cartViewModel) }!!

        cartViewModel.selectedMenus()

        cartViewModel.menuListMutableLiveData.observe(viewLifecycleOwner, Observer { list->

            if(CartViewModel.cartItems.isNotEmpty()) {
                totalPrice = 0.0
                cartAdapter = context?.let { CartAdapter(it, list, cartViewModel) }!!
                binding.cartMenuList.adapter = cartAdapter

                for(menu in list){
                    totalPrice += menu.price*menu.amount
                }

                binding.txtTotalPrice.text = "Toplam tutar: " + totalPrice.toString() + " TL"
            }
            else
                Log.e("LOGGER", "list is empty")

        })




    }

}