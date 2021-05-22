package com.selcukokc.fooddelivery.view

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.selcukokc.fooddelivery.R

class SearchFragment: BaseFragment(), SearchView.OnQueryTextListener {
    override var bottomNavigationViewVisibility = View.VISIBLE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
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