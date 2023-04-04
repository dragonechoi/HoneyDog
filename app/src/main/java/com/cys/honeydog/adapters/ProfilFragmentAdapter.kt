package com.cys.honeydog.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.cys.honeydog.R
import com.cys.honeydog.activities.PostActivity
import com.cys.honeydog.model.ProfilRecyclerItem

class ProfilFragmentAdapter constructor(var context: Context,var items:MutableList<ProfilRecyclerItem>): Adapter<ProfilFragmentAdapter.VH>() {

    inner class VH(itemView: View):ViewHolder(itemView){
        val rcTv:TextView by lazy { itemView.findViewById(R.id.recycler_item_tv) }
        val rcIV:ImageView by lazy { itemView.findViewById(R.id.recycler_profil_item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView:View=LayoutInflater.from(context).inflate(R.layout.recycler_item_profil,parent,false)
        return VH(itemView)
    }

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item:ProfilRecyclerItem= items[position]
        holder.rcTv.text=item.tvTitle
        Glide.with(context).load(item.viewImage).into(holder.rcIV)

        holder.itemView.setOnClickListener{context.startActivity(Intent(context,PostActivity::class.java)
        )}
    }
}