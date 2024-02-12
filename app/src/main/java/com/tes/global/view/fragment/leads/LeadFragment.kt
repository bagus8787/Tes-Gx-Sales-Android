package com.tes.global.view.fragment.leads

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import com.tes.global.R
import com.tes.global.adapter.LeadsAdapter
import com.tes.global.databinding.FragmentLeadBinding
import com.tes.global.helper.viewBinding
import com.tes.global.model.Status
import com.tes.global.model.response.LeadResponse
import com.tes.global.view.activity.HomeActivity
import com.tes.global.view.base.BaseFragment

class LeadFragment : BaseFragment(R.layout.fragment_lead), LeadsAdapter.CellClickListener {

    private val binding by viewBinding(FragmentLeadBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLead()
        setView()
        setListener()
    }

    private fun setView(){
        binding.tvTitle.text = "Leads"

        binding.imgBack.visibility = View.GONE
        binding.imgBookmarkAdd.visibility = View.GONE

        binding.searchLead.setOnClickListener(View.OnClickListener { binding.searchLead.isIconified =
            false })
    }

    private fun getLead(){
        baseViewModel.getLead().observe(viewLifecycleOwner){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val data = resource.data?.data
                        data?.let {it1 ->
                            var adapter = LeadsAdapter(requireContext(), it1, this)
                            binding.rvLead.adapter = adapter

                            binding.searchLead.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                                override fun onQueryTextSubmit(s: String?): Boolean {
                                    return false
                                }

                                override fun onQueryTextChange(s: String?): Boolean {
                                    adapter.filter.filter(s)
                                    return false
                                }
                            })


                        }

                        pLoading.dismissDialog()
                    }
                    Status.ERROR -> {
                        pLoading.dismissDialog()
                    }
                    Status.LOADING -> {
                        pLoading.showLoading()
                    }
                }
            }
        }
    }

    private fun setListener(){

    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).showBottomBar(true)
    }

    override fun selectLead(data: LeadResponse.Data) {

    }

    override fun deleteLead(data: LeadResponse.Data) {
        val builder1: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder1.setMessage("Are you sure delete lead ${data.fullName} ?")
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "Yes"
        ) { dialog, id ->
            baseViewModel.deleteLead(data.id.toString()).observe(viewLifecycleOwner){
                it?.let { resource ->
                    when(resource.status){
                        Status.SUCCESS -> {
                            dialog.cancel()
                            pLoading.dismissDialog()

                            getLead()
                        }

                        Status.ERROR -> {
                            pLoading.dismissDialog()
                        }

                        Status.LOADING -> {
                            pLoading.showLoading()
                        }
                    }
                }
            }
        }

        builder1.setNegativeButton(
            "No"
        ) { dialog, id -> dialog.cancel() }
        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }
}