package com.selcukokc.fooddelivery.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.databinding.CardDesignMenupanelBinding
import com.selcukokc.fooddelivery.model.Menu
import com.selcukokc.fooddelivery.util.downloadFromUrl
import com.selcukokc.fooddelivery.util.placeholderProgressBar
import com.selcukokc.fooddelivery.view.MainActivity
import com.selcukokc.fooddelivery.viewmodel.restaurantviewmodel.RestaurantMenuPanelViewModel

interface OnClickImageListener{
    fun onClick()
}


class MenuAdapter(private val mContext : Context, private val menuList: ArrayList<Menu>,
                  private val restaurantMenuPanelViewModel : RestaurantMenuPanelViewModel)
    : RecyclerView.Adapter<MenuAdapter.CardViewHolder>(), OnClickImageListener {


        inner class CardViewHolder(view : View) : RecyclerView.ViewHolder(view){
            val binding = CardDesignMenupanelBinding.bind(view)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.card_design_menupanel,null,false)
            return CardViewHolder(view)
        }

        override fun getItemCount(): Int {
            return menuList.size
        }


        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: MenuAdapter.CardViewHolder, position: Int) {
            with(holder){


                binding.menuTitle.text = menuList[position].title
                binding.menuDesc.text = menuList[position].description
                binding.menuPrice.text = menuList[position].price.toString() + " TL"
                binding.menuID.text = menuList[position].menuId.toString()
                menuList[position].imageURL?.let {
                    binding.menuImage.downloadFromUrl(
                        it,
                        placeholderProgressBar(mContext))
                }

                binding.menuID.visibility = View.GONE

                binding.cardView.setOnClickListener{

                    val builder = AlertDialog.Builder(mContext)
                    builder.setTitle("Menü Güncelle")
                    val customLayout = (mContext as MainActivity).layoutInflater.inflate(R.layout.add_menu, null)
                    builder.setView(customLayout)

                    val menuTitleEditText = customLayout.findViewById<EditText>(R.id.menuTitleInput)
                    val menuDescEditText = customLayout.findViewById<EditText>(R.id.menuDescInput)
                    val menuPriceEditText = customLayout.findViewById<EditText>(R.id.menuPriceInput)
                    val menuImageInput = customLayout.findViewById<ImageView>(R.id.menuImageInput)

                    menuTitleEditText.setText(binding.menuTitle.text)
                    menuDescEditText.setText(binding.menuDesc.text)
                    menuPriceEditText.setText(binding.menuPrice.text.removeRange(binding.menuPrice.text.length-2,binding.menuPrice.text.length))
                    menuList[position].imageURL?.let { it1 ->
                        menuImageInput.downloadFromUrl(
                            it1,
                            placeholderProgressBar(mContext))
                    }


                    menuImageInput.setOnClickListener {
                        onClick()

                    }


                    builder.setPositiveButton("GÜNCELLE") { _, _ ->
                            restaurantMenuPanelViewModel.updateMenu(binding.menuID.text.toString(),
                                menuTitleEditText.text.toString(),
                                menuDescEditText.text.toString(), menuPriceEditText.text.toString().toDouble())

                        restaurantMenuPanelViewModel.showMenus()
                    }





                    builder.setNegativeButton("SİL") { _, _ ->
                        restaurantMenuPanelViewModel.deleteMenu(binding.menuID.text.toString())
                        restaurantMenuPanelViewModel.showMenus()
                    }

                    val dialog = builder.create();
                    dialog.show();

                }
            }


        }

    override fun onClick(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        (mContext as Activity).startActivityForResult(intent, 1)
    }


}