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
import com.selcukokc.fooddelivery.adapters.CartAdapter
import com.selcukokc.fooddelivery.databinding.FragmentCartBinding
import com.selcukokc.fooddelivery.model.Order
import com.selcukokc.fooddelivery.model.OrderedMenu
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
    ): View {
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


        cartViewModel.menuListMutableLiveData.observe(viewLifecycleOwner,  { menuList->
            if(menuList.isNotEmpty())
                cartViewModel.getRestaurantName(menuList[0].restaurantID.toString())

            if(CartViewModel.cartItems.isNotEmpty()) {
                totalPrice = 0.0
                cartAdapter = context?.let { CartAdapter(it, menuList, cartViewModel) }!!
                binding.cartMenuList.adapter = cartAdapter

                for(menu in menuList){
                    totalPrice += menu.price*menu.amount
                }

                binding.txtTotalPrice.text = "Toplam tutar: " + totalPrice.toString() + " TL"
            }
            else {
                cartAdapter = context?.let { CartAdapter(it, arrayListOf(), cartViewModel) }!!
                binding.cartMenuList.adapter = cartAdapter
            }
        })


        binding.btnOrder.setOnClickListener {
            cartViewModel.restaurantNameLivedata.observe(viewLifecycleOwner, { restaurantName ->
             cartViewModel.menuListMutableLiveData.observe(viewLifecycleOwner,  { cartList ->
                 if(cartList.isNotEmpty()) {

                         val orderedMenuList = ArrayList<OrderedMenu>()
                         for (cartItem in cartList) {
                             val menu = OrderedMenu(
                                 cartItem.title,
                                 cartItem.amount,
                                 cartItem.price
                             )
                             orderedMenuList.add(menu)
                         }

                         val order = Order(cartList[0].restaurantID, restaurantName ,orderedMenuList)
                         cartViewModel.storeOrders(order)
                         cartViewModel.removeAllMenus()
                         Toast.makeText(context, "Siparişiniz onaylandı", Toast.LENGTH_SHORT).show()

                    }
                })
            })

        }

    }

}