package com.cys.honeydog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.cys.honeydog.R
import com.cys.honeydog.model.SearchItem

class SearchFragmentAdapter constructor(var context: Context,var items:MutableList<SearchItem>): Adapter<SearchFragmentAdapter.VH>() {

    inner class VH(itemView: View):ViewHolder(itemView){

        val searchImg: ImageView by lazy { itemView.findViewById(R.id.iv_hospital) }
        val searchTitle: TextView by lazy { itemView.findViewById(R.id.tv_title) }
        val searchNum: TextView by lazy { itemView.findViewById(R.id.tv_num) }
        val address: TextView by lazy { itemView.findViewById(R.id.address) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
       val itemView:View=LayoutInflater.from(context).inflate(R.layout.recycler_item_search,parent,false)
        return VH(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item:SearchItem=items[position]

        holder.searchTitle.text=item.title
        holder.searchNum.text=item.tvNum
        holder.address.text=item.address
        Glide.with(context).load(item.img).into(holder.searchImg)
    }
}