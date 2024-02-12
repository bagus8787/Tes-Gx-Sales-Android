package com.tes.global.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tes.global.databinding.ItemMenuBinding
import com.tes.global.helper.viewBinding
import com.tes.global.model.DataMenu

class MenuAdapter(
    private val context: Context,
    var dataList: List<DataMenu>,
    val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<MenuAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var tvDesc = binding.tvDesc
        var tvCount = binding.tvCount

        var parent = binding.parentLayout

        fun bind(data: DataMenu) {
            tvDesc.text = data.title
            tvCount.text = data.count.toString()

            parent.setOnClickListener {
                cellClickListener.selectMenu(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuAdapter.MyViewHolder {
        return MyViewHolder(parent.viewBinding(ItemMenuBinding::inflate))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface CellClickListener {
        fun selectMenu(data: DataMenu)
    }
}

