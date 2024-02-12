package com.tes.global.view.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.tes.global.R
import com.tes.global.adapter.MenuAdapter
import com.tes.global.databinding.FragmentHomeBinding
import com.tes.global.helper.viewBinding
import com.tes.global.model.DataMenu
import com.tes.global.model.Status
import com.tes.global.view.activity.HomeActivity
import com.tes.global.view.base.BaseFragment

class HomeFragment : BaseFragment(R.layout.fragment_home), MenuAdapter.CellClickListener {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    companion object {
        var MENU_ALL_LEADS = "Total All\nLeads"
        var MENU_CANCEL_LEADS = "Total Cancel\nLeads"
        var MENU_PENDING_LEADS = "Total Leads\nPending"
        var MENU_CONVERTED_LEADS = "Total Leads\nConverted"
    }

    private var fromDate = ""
    private var toDate = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDashboard()
        setView()
        setListener()
    }

    private fun getDashboard(){
        baseViewModel.getDashboard(fromDate,toDate).observe(viewLifecycleOwner){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val data = resource.data
                        if (data != null){
                            val value = data.data.statuses

                            var cAllLeads = value.size
                            var cCancel = value.find { it.name == "Cancel" }?.total?: 0
                            var cPending = value.find { it.name == "Pending" }?.total?: 0
                            var cConverted = value.find { it.name == "Consideration" }?.total?: 0

                            binding.rvMenu.layoutManager = GridLayoutManager(requireContext(), 2)
                            binding.rvMenu.adapter = MenuAdapter(requireContext(), listMenu(cAllLeads, cCancel, cPending, cConverted), this)
                        }
                    }
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {

                    }
                }
            }
        }
    }

    private fun setListener() {

    }

    private fun setView(){
        val dataUser = session.dataUser
        if (dataUser != null){
            binding.toolbar.tvName.text = dataUser.name
            binding.toolbar.tvEmail.text = dataUser.email
        }
    }

    private fun listMenu(countLeads: Int,countCancel: Int, countPending: Int, countConverted: Int): List<DataMenu>{
        val data = mutableListOf<DataMenu>()
        data.add(
            DataMenu(
                MENU_ALL_LEADS,
                countLeads
            )
        )
        data.add(
            DataMenu(
                MENU_CANCEL_LEADS,
                countCancel
            )
        )
        data.add(
            DataMenu(
                MENU_PENDING_LEADS,
                countPending
            )
        )
        data.add(
            DataMenu(
                MENU_CONVERTED_LEADS,
                countConverted
            )
        )

        return data
    }

    override fun selectMenu(data: DataMenu) {

    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).showBottomBar(true)
    }
}