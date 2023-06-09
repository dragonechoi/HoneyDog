package com.cys.honeydog.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cys.honeydog.R
import com.cys.honeydog.activities.PostActivity
import com.cys.honeydog.model.MiniCmtItem

class MiniCmtItemAdapter constructor(var context: Context, var items: MutableList<MiniCmtItem>) :
    Adapter<MiniCmtItemAdapter.VH>() {

    inner class VH(itemView: View) : ViewHolder(itemView) {
        val tvTitle: TextView by lazy { itemView.findViewById(R.id.main_post_title) }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var itemView: View =
            LayoutInflater.from(context).inflate(R.layout.recycler_item_minicmm, parent, false)
        return VH(itemView)
    }


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        var item: MiniCmtItem = items[position]

        holder.tvTitle.text = item.title




        holder.tvTitle.setOnClickListener {
            val intent: Intent = Intent(context, PostActivity::class.java)
            intent.putExtra("imageUri", item.imageUri)
            intent.putExtra("title", item.title)
            intent.putExtra("nickname", item.nickname)
            intent.putExtra("postText", item.postText)
            intent.putExtra("id", item.userId)
            intent.putExtra("profileUrl", item.profileUrl)
            intent.putExtra("no", item.no)

            context.startActivity(intent)
        }


    }


}





