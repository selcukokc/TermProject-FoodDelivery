package com.selcukokc.fooddelivery.view.restaurant

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.adapters.MenuAdapter
import com.selcukokc.fooddelivery.databinding.FragmentRestaurantMenuPanelBinding
import com.selcukokc.fooddelivery.util.downloadFromUrl
import com.selcukokc.fooddelivery.util.placeholderProgressBar
import com.selcukokc.fooddelivery.view.BaseFragment
import com.selcukokc.fooddelivery.viewmodel.restaurantviewmodel.RestaurantMenuPanelViewModel


class RestaurantMenuPanelFragment : BaseFragment() {
    override var bottomNavigationViewVisibility = View.GONE
    private lateinit var binding: FragmentRestaurantMenuPanelBinding
    private lateinit var restaurantMenuPanelViewModel: RestaurantMenuPanelViewModel
    private lateinit var menuAdapter : MenuAdapter
    private lateinit var imageUri: Uri


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentRestaurantMenuPanelBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restaurantMenuPanelViewModel = ViewModelProvider(this).get(RestaurantMenuPanelViewModel::class.java)
        restaurantMenuPanelViewModel.restaurantMenuLoading.observe(viewLifecycleOwner, Observer{ loading->

            if(loading){
                binding.menuList.visibility = View.GONE
                binding.txtEmptyMenu.visibility = View.GONE
                binding.menuLoadingProgressBar.visibility = View.VISIBLE
            } else{
               binding.menuLoadingProgressBar.visibility = View.GONE
               binding.menuList.visibility = View.VISIBLE
            }


        })

        binding.menuList.layoutManager = LinearLayoutManager(context)
        binding.menuList.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        menuAdapter = context?.let { MenuAdapter(it, arrayListOf(),restaurantMenuPanelViewModel) }!!

        restaurantMenuPanelViewModel.showMenus()

        restaurantMenuPanelViewModel.restaurantMenuListMutableLiveData.observe(viewLifecycleOwner, Observer { list->
            if(list.isNotEmpty()) {
                binding.txtEmptyMenu.visibility = View.INVISIBLE
                menuAdapter = context?.let { MenuAdapter(it, list, restaurantMenuPanelViewModel ) }!!
                binding.menuList.adapter = menuAdapter
            } else {
                binding.txtEmptyMenu.visibility = View.VISIBLE
            }

        })


        restaurantMenuPanelViewModel.addMenuMutableLiveData.observe(viewLifecycleOwner, Observer { menu ->
            if(menu!=null) {
                Toast.makeText(context, "Menü başarıyla eklendi", Toast.LENGTH_SHORT).show()
                restaurantMenuPanelViewModel.showMenus()
            }
            else{
                Toast.makeText(context, "Menü eklenirken bir hata oluştu", Toast.LENGTH_SHORT).show()
            }
        })



        restaurantMenuPanelViewModel.updateMenu.observe(viewLifecycleOwner, Observer { menuUpdated->
            if(menuUpdated)
                Toast.makeText(context, "Menü başarıyla güncellendi. ", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Güncelleme esnasında bir hata oluştu", Toast.LENGTH_SHORT).show()
        })


        restaurantMenuPanelViewModel.deleteMenu.observe(viewLifecycleOwner, Observer { menuDeleted->
            if(menuDeleted)
                Toast.makeText(context, "Menü başarıyla silindi", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Silme esnasında bir hata oluştu", Toast.LENGTH_SHORT).show()
        })

        binding.addMenu.setOnClickListener {

            val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle("Menü Ekle")
            val customLayout = layoutInflater.inflate(R.layout.add_menu, null)
            builder?.setView(customLayout)
            val menuTitle = customLayout.findViewById<EditText>(R.id.menuTitleInput)
            val menuDesc = customLayout.findViewById<EditText>(R.id.menuDescInput)
            val menuPrice = customLayout.findViewById<EditText>(R.id.menuPriceInput)
            val menuImage = customLayout.findViewById<ImageView>(R.id.menuImageInput)

            menuImage.setOnClickListener {
                choosePicture()
            }

            restaurantMenuPanelViewModel.menuImage.observe(viewLifecycleOwner, Observer { uri->

                context?.let { placeholderProgressBar(it) }?.let {

                    menuImage.downloadFromUrl(uri.toString(),
                        it
                    )
                }

            })

            builder?.setPositiveButton("ONAYLA") { _, _ ->

                restaurantMenuPanelViewModel.getMenuImageUri()?.let { it1 ->
                    restaurantMenuPanelViewModel.addMenu(menuTitle.text.toString(), menuDesc.text.toString(),
                        menuPrice.text.toString().toDouble(), it1
                    )
                }
            }

            val dialog = builder?.create()
            dialog?.show();

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
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data?.data !=null){
            imageUri = data.data!!
            restaurantMenuPanelViewModel.updateMenuImage(imageUri)

        }
    }





    }
