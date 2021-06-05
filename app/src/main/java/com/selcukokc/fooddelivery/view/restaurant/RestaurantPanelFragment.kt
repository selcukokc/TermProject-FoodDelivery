package com.selcukokc.fooddelivery.view.restaurant

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantPanelBinding
import com.selcukokc.fooddelivery.viewmodel.restaurantviewmodel.RestaurantPanelViewModel



class RestaurantPanelFragment : Fragment() {

    private lateinit var restaurantPanelViewModel: RestaurantPanelViewModel
    private lateinit var binding: FragmentRestaurantPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantPanelBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restaurantPanelViewModel = ViewModelProvider(this).get(RestaurantPanelViewModel::class.java)

        restaurantPanelViewModel.restaurantInformation()

        restaurantPanelViewModel.restaurantInfoLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.progressBar.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.GONE
                binding.txtName.visibility = View.GONE
                binding.imgLogo.visibility = View.GONE
            }
            else{
                binding.progressBar.visibility = View.GONE
                binding.txtName.visibility = View.VISIBLE
                binding.imgLogo.visibility = View.VISIBLE
            }

        })

        restaurantPanelViewModel.restaurantInfoError.observe(viewLifecycleOwner, Observer{
            when(it){
                true -> binding.errorMessage.visibility = View.VISIBLE
                false -> binding.errorMessage.visibility = View.GONE
            }
        })


        restaurantPanelViewModel.restaurantInformationMutableLiveData.observe(viewLifecycleOwner, Observer{ restaurant->
            if (restaurant != null) {
                binding.txtName.text = restaurant.restaurantName.toString()

            } else {
                Log.e("ssss", "empty list")
            }

        })



        restaurantPanelViewModel.restLoggedOutMutableLiveData.observe(viewLifecycleOwner, Observer{
            if(it){
                Toast.makeText(context, "ÇIKIŞ YAPILDI", Toast.LENGTH_SHORT).show()
                val action = RestaurantPanelFragmentDirections.actionRestaurantPanelFragmentToRestaurantLoginFragment()
                view?.let{ it1 -> Navigation.findNavController(it1).navigate(action) }
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.restaurant_panel_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           R.id.restaurant_logout -> restaurantPanelViewModel.logout()
           R.id.itemMenus -> view?.let{ Navigation.findNavController(it).navigate(R.id.action_restaurantPanelFragment_to_restourantMenuPanelFragment) }
           R.id.itemProfile -> view?.let { Navigation.findNavController(it).navigate(R.id.action_restaurantPanelFragment_to_restaurantProfileFragment) }
        }


        return true
    }



}