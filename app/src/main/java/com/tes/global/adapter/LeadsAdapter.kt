package com.tes.global.adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tes.global.R
import com.tes.global.databinding.ItemLeadBinding
import com.tes.global.helper.Tools
import com.tes.global.helper.viewBinding
import com.tes.global.model.response.LeadResponse

class LeadsAdapter(
    private val context: Context,
    var dataList: List<LeadResponse.Data>,
    val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<LeadsAdapter.MyViewHolder>(), Filterable {

    private var listFilter : List<LeadResponse.Data>?= mutableListOf()
    init {
        listFilter = dataList
    }

    val colors = arrayOf(
        R.color.blue_fadi,
        R.color.red_warning,
        R.color.light_green_600,
    )

    val colors2 = arrayOf(
        R.color.darkBlue,
        R.color.yellow_parane,
        R.color.Orange
    )

//    val randomColor = colors.random()

    inner class MyViewHolder(binding: ItemLeadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var tvName = binding.tvName
        var tvNumber = binding.tvNumber
        var tvAddress = binding.tvAddress

        var tvDate = binding.tvDate
        var tvGlobalOffice = binding.tvGlobalOffice

        var tvTypeOne = binding.tvTypeOne
        var tvTypeTwo = binding.tvTypeTwo

        var parent = binding.parentLayout

        var imgDelete = binding.imgDelete

        fun bind(data: LeadResponse.Data) {
            tvName.text = data.fullName
            tvNumber.text = data.number
            tvAddress.text = data.address
            tvDate.text = Tools.convertToDate(data.createdAt)
            tvGlobalOffice.text = data.branchOffice.name

            tvTypeOne.backgroundTintList = ContextCompat.getColorStateList(context, colors.random())
            tvTypeTwo.backgroundTintList = ContextCompat.getColorStateList(context, colors2.random())

            parent.setOnClickListener {
                cellClickListener.selectLead(data)
            }

            imgDelete.setOnClickListener {
                cellClickListener.deleteLead(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeadsAdapter.MyViewHolder {
        return MyViewHolder(parent.viewBinding(ItemLeadBinding::inflate))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listFilter!![position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listFilter?.size?: 0
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                Log.d("charSearch", charSearch)

                if (charSearch.isEmpty()) {
                    listFilter = dataList
                } else {
                    val resultList = mutableListOf<LeadResponse.Data>()
                    for (row in dataList){
                        if(row.fullName!!.contains(charSearch, true)){
                            resultList.add(row)
                        }
                    }
                    listFilter = resultList
                }

                val filterResults = FilterResults()
                filterResults.values = listFilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listFilter = results?.values as List<LeadResponse.Data>
                notifyDataSetChanged()
            }

        }
    }


    interface CellClickListener {
        fun selectLead(data: LeadResponse.Data)
        fun deleteLead(data: LeadResponse.Data)

    }
}

