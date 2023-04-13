package com.cys.honeydog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.cys.honeydog.R
import com.cys.honeydog.databinding.RecyclerCommentItemBinding
import com.cys.honeydog.model.CommentItem

class CommentAdapter (var context:Context, var items:MutableList<CommentItem>):
        Adapter<CommentAdapter.VH>() {

    inner class VH(val binding: RecyclerCommentItemBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RecyclerCommentItemBinding.inflate(LayoutInflater.from(context))
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var list:CommentItem = items[position]

        if (list.imgUrl.isNullOrEmpty()){
            Glide.with(context).load(R.drawable.cat_community_header).into(holder.binding.civCommentProfile)
        }else{
            Glide.with(context).load(list.imgUrl).into(holder.binding.civCommentProfile)

        }
        holder.binding.commentNickname.text=list.nickname
        holder.binding.commentTv.text=list.comment




    }

}