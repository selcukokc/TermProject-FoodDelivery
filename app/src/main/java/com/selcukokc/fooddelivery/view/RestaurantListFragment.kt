package com.selcukokc.fooddelivery.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.selcukokc.fooddelivery.adapters.RestaurantAdapter
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.model.Restaurants
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantListBinding
import kotlin.random.Random

class RestaurantListFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val menuId = Random.nextInt().toString()
        val m1 = Menu(menuId, "Big King® Menü", "Big King® + Büyük Boy Patates + 33cl Coca-Cola",29.99)


        val arrMenuList = ArrayList<Menu>()
        arrMenuList.add(m1)



        val restourantId = Random.nextInt().toString()
        val r1 = Restaurants(restourantId, "Burger", "Burger King", "https://firebasestorage.googleapis.com/v0/b/termproject-15aed.appspot.com/o/burgerking.png?alt=media&token=f893dc8b-53af-4269-a5fe-0a8222f1da05",
        arrMenuList, null, 8.4, null)



        val restourantList = ArrayList<Restaurants>()
        restourantList.add(r1)

        binding.rvRestoranListesi.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        var adapter = context?.let { RestaurantAdapter(it, restourantList) }

        binding.rvRestoranListesi.adapter = adapter



    }


}