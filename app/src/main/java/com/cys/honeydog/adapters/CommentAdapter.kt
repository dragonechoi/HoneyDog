package com.cys.honeydog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.R
import com.cys.honeydog.databinding.RecyclerCommentItemBinding
import com.cys.honeydog.model.CommentItem
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import okhttp3.internal.cache.DiskLruCache.Snapshot

class CommentAdapter(var context: Context, var items: MutableList<CommentItem>) :
    Adapter<CommentAdapter.VH>() {
    val binding: RecyclerCommentItemBinding by lazy {
        RecyclerCommentItemBinding.inflate(
            LayoutInflater.from(context)
        )
    }

    inner class VH(val binding: RecyclerCommentItemBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RecyclerCommentItemBinding.inflate(LayoutInflater.from(context))
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val commentItem = items[position]

        val firestore = FirebaseFirestore.getInstance()
        val userRef = firestore.collection("idUsers").document(G.userAccount!!.id)

        userRef.get().addOnSuccessListener { userSnapshot ->
            commentItem.imgUrl = userSnapshot.getString("imageUrl")!!.toString()
            commentItem.nickname = userSnapshot.getString("nickname")!!.toString()
            commentItem.comment=userSnapshot.getString("comment")!!.toString()

            if (commentItem.imgUrl.isNullOrEmpty()) {
                Glide.with(context).load(R.drawable.cat_community_header).into(holder.binding.civCommentProfile)
            } else {
                Glide.with(context).load(commentItem.imgUrl).into(holder.binding.civCommentProfile)
            }

            holder.binding.commentNickname.text = commentItem.nickname
            holder.binding.commentTv.text = commentItem.comment
        }
    }


}