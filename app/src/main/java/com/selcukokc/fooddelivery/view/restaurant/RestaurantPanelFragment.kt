package com.selcukokc.fooddelivery.view.restaurant

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.load.model.stream.UrlLoader
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantPanelBinding
import com.selcukokc.fooddelivery.util.downloadFromUrl
import com.selcukokc.fooddelivery.util.placeholderProgressBar
import com.selcukokc.fooddelivery.view.BaseFragment
import com.selcukokc.fooddelivery.viewmodel.restaurantviewmodel.RestaurantMenuPanelViewModel
import com.selcukokc.fooddelivery.viewmodel.restaurantviewmodel.RestaurantPanelViewModel
import java.net.URLDecoder



class RestaurantPanelFragment : BaseFragment() {

    override var bottomNavigationViewVisibility = View.GONE
    private lateinit var restaurantPanelViewModel: RestaurantPanelViewModel
    private lateinit var binding: FragmentRestaurantPanelBinding
    private lateinit var imageUri: Uri

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
                binding.txtLastOrders.visibility = View.GONE
            }
            else{
                binding.progressBar.visibility = View.GONE
                binding.txtName.visibility = View.VISIBLE
                binding.txtLastOrders.visibility = View.VISIBLE
                binding.imgLogo.visibility = View.VISIBLE
                restaurantPanelViewModel.showPicture()
            }

        })

        restaurantPanelViewModel.restaurantInfoError.observe(viewLifecycleOwner, Observer{
            when(it){
                true -> binding.errorMessage.visibility = View.VISIBLE
                false -> binding.errorMessage.visibility = View.GONE
            }
        })


        restaurantPanelViewModel.restaurantInformationMutableLiveData.observe(viewLifecycleOwner, Observer{ restaurant->
            if (restaurant != null)
                binding.txtName.text = restaurant.restaurantName.toString()

        })

        restaurantPanelViewModel.downloadRestaurantPicture.observe(viewLifecycleOwner, Observer{ downloadedPicture ->
            if(!downloadedPicture.equals("")){
                context?.let { placeholderProgressBar(it) }?.let {
                    binding.imgLogo.downloadFromUrl(downloadedPicture,
                        it
                    )
                }
            }

        })


        restaurantPanelViewModel.restaurantPicture.observe(viewLifecycleOwner, Observer{ picture ->
            if(picture != null){
                Toast.makeText(context,"Fotoğraf başarıyla yüklendi.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Yükleme sırasında bir hata oluştu.", Toast.LENGTH_SHORT).show()
            }
        })

        restaurantPanelViewModel.restLoggedOutMutableLiveData.observe(viewLifecycleOwner, Observer{
            if(it){
                Toast.makeText(context, "ÇIKIŞ YAPILDI", Toast.LENGTH_SHORT).show()
                val action = RestaurantPanelFragmentDirections.actionRestaurantPanelFragmentToRestaurantLoginFragment()
                view?.let{ it1 -> Navigation.findNavController(it1).navigate(action) }
            }

        })


        binding.imgLogo.setOnClickListener {
            choosePicture()
        }

    }

    private fun choosePicture() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == RESULT_OK && data?.data !=null){
            imageUri = data.data!!
            binding.imgLogo.setImageURI(imageUri)
            restaurantPanelViewModel.uploadPicture(imageUri)
        }
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