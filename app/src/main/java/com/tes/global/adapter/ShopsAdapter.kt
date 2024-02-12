package com.tes.global.adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tes.global.R
import com.tes.global.databinding.ItemShopBinding
import com.tes.global.helper.viewBinding

class ShopsAdapter(
    private val context: Context,
    var dataList: List<String>,
    val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<ShopsAdapter.MyViewHolder>(), Filterable {

    private var listFilter : List<String>?= mutableListOf()
    init {
        listFilter = dataList
    }

    inner class MyViewHolder(binding: ItemShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var tvName = binding.tvName
        var parent = binding.parentLayout

        var imgShop = binding.imgShop

        fun bind(data: String, position: Int) {
            tvName.text = data

            when(position){
                0 -> imgShop.setImageResource(R.drawable.img_one)
                1 -> imgShop.setImageResource(R.drawable.img_two)
                2 -> imgShop.setImageResource(R.drawable.img_three)
                3 -> imgShop.setImageResource(R.drawable.img_four)
                4 -> imgShop.setImageResource(R.drawable.img_five)
            }

            parent.setOnClickListener {
                cellClickListener.selectShops(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopsAdapter.MyViewHolder {
        return MyViewHolder(parent.viewBinding(ItemShopBinding::inflate))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listFilter!![position]
        holder.bind(data, position)
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
                    val resultList = mutableListOf<String>()
                    for (row in dataList){
                        if(row.contains(charSearch, true)){
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
                listFilter = results?.values as List<String>
                notifyDataSetChanged()
            }

        }
    }


    interface CellClickListener {
        fun selectShops(data: String)
    }
}

