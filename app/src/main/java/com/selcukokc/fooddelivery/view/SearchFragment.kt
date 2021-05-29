package com.selcukokc.fooddelivery.view

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.databinding.FragmentSearchBinding

class SearchFragment: BaseFragment(), SearchView.OnQueryTextListener {
    override var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.search_item)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


}