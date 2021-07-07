package com.selcukokc.fooddelivery.view.user

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.selcukokc.fooddelivery.R
import com.selcukokc.fooddelivery.adapters.SearchAdapter
import com.selcukokc.fooddelivery.databinding.FragmentSearchBinding
import com.selcukokc.fooddelivery.view.BaseFragment
import com.selcukokc.fooddelivery.viewmodel.userviewmodel.SearchViewModel

class SearchFragment: BaseFragment(), SearchView.OnQueryTextListener {
    override var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.restaurantList.layoutManager = LinearLayoutManager(context)
        binding.restaurantList.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        searchAdapter = context?.let { SearchAdapter(it, arrayListOf()) }!!

        searchViewModel.restaurantListMutableLiveData.observe(viewLifecycleOwner, {
            searchAdapter = context?.let { it1 -> SearchAdapter(it1, it) }!!
            binding.restaurantList.adapter = searchAdapter
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.search_item)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchViewModel.searchQuery(query)
            searchViewModel.searchQueryLiveData.observe(viewLifecycleOwner, {
                searchViewModel.getRestaurantResults(it)
            })

            searchViewModel.restaurantListMutableLiveData.observe(viewLifecycleOwner, {
                searchAdapter = context?.let { it1 -> SearchAdapter(it1, it) }!!
                binding.restaurantList.adapter = searchAdapter
            })
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchViewModel.searchQuery(newText)
            searchViewModel.searchQueryLiveData.observe(viewLifecycleOwner, {
                searchViewModel.getRestaurantResults(it)
            })

            searchViewModel.restaurantListMutableLiveData.observe(viewLifecycleOwner, {
                searchAdapter = context?.let { it1 -> SearchAdapter(it1, it) }!!
                binding.restaurantList.adapter = searchAdapter
            })
        }

        return true
    }


}