package com.cys.honeydog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.R
import com.cys.honeydog.UserAccount
import com.cys.honeydog.databinding.RecyclerCommentItemBinding
import com.cys.honeydog.model.CommentItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class CommentAdapter(var context: Context, var items: MutableList<CommentItem>) :
    Adapter<CommentAdapter.VH>() {

    inner class VH(val binding: RecyclerCommentItemBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RecyclerCommentItemBinding.inflate(LayoutInflater.from(context))
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var list: CommentItem = items[position]

        if (list.imgUrl.isNullOrEmpty()) {
            Glide.with(context).load(R.drawable.cat_community_header)
                .into(holder.binding.civCommentProfile)
        } else {
            Glide.with(context).load(list.imgUrl).into(holder.binding.civCommentProfile)

        }
        holder.binding.commentNickname.text = list.nickname
        holder.binding.commentTv.text = list.comment

        loadUser(holder.binding.civCommentProfile, holder.binding.commentNickname)
        loadComment(holder.binding.commentTv,holder.binding.commentNum)

    }

    private fun loadUser(profile: ImageView, nicknameView: TextView) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("idUsers")
            .document(G.userAccount!!.id)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {

                    val nicknames = snapshot.getString("nickname")
                    val iv = snapshot.getString("imgUrl")
                    nicknameView.text = nicknames
                    Glide.with(context).load(iv).into(profile)


                }
            }
    }

    private fun loadComment(commentView: TextView,numView:TextView) {
        val comments = FirebaseFirestore.getInstance()
        comments.collection("catPost")
            .document()
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val comment = snapshot.getString("comment")
                    val commentNum=snapshot.getString("no")
                    commentView.text = comment
                    numView.text=commentNum
                }


            }
    }
}