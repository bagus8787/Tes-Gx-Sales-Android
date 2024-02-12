package com.tes.global.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import com.tes.global.R
import com.tes.global.adapter.ShopsAdapter
import com.tes.global.databinding.FragmentShopBinding
import com.tes.global.helper.viewBinding
import com.tes.global.view.base.BaseFragment

class ShopFragment : BaseFragment(R.layout.fragment_shop), ShopsAdapter.CellClickListener {

    private val binding by viewBinding(FragmentShopBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
        setListener()
    }

    private fun setView(){
        binding.tvTitle.text = "Shop Item"

        binding.imgBack.visibility = View.GONE
        binding.imgBookmarkAdd.visibility = View.GONE

        binding.searchLead.setOnClickListener(View.OnClickListener { binding.searchLead.isIconified =
            false })


        val shopsAdapter = ShopsAdapter(requireContext(), listShop(), this)
        binding.rvShop.adapter = shopsAdapter

        binding.searchLead.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                shopsAdapter.filter.filter(s)
                return false
            }
        })

    }

    private fun setListener(){

    }

    private fun listShop(): List<String> {
        val data = mutableListOf<String>()
        data.add("A810R AC1200 Router")
        data.add("A810R AC1200 Router")
        data.add("A810R AC1200 Router")
        data.add("A810R AC1200 Router")
        data.add("A810R AC1200 Router")

        return data
    }

    override fun selectShops(data: String) {

    }
}