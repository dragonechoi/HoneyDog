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
import com.cys.honeydog.G
import com.cys.honeydog.R
import com.cys.honeydog.activities.PostActivity
import com.cys.honeydog.model.ProfilRecyclerItem
import com.google.firebase.firestore.FirebaseFirestore

class ProfilFragmentAdapter constructor(
    var context: Context,
    var posts: MutableList<ProfilRecyclerItem>
) : Adapter<ProfilFragmentAdapter.VH>() {

    inner class VH(itemView: View) : ViewHolder(itemView) {
        val rcTv: TextView by lazy { itemView.findViewById(R.id.recycler_item_tv) }
        val rcIV: ImageView by lazy { itemView.findViewById(R.id.recycler_profil_item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.recycler_item_profil, parent, false)
        return VH(itemView)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val post: ProfilRecyclerItem = posts[position]
        holder.rcTv.text = post.title
        if (post.imageUri?.isNotEmpty() == true) {
            Glide.with(context).load(post.imageUri).into(holder.rcIV)
        } else {
            Glide.with(context).load(R.drawable.cogi_love).into(holder.rcIV)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra("image", post.imageUri)
            intent.putExtra("title", post.title)
            intent.putExtra("nickname", post.nickname)
            intent.putExtra("postText", post.postText)
            intent.putExtra("id", post.id)
            intent.putExtra("profile",post.profileUrl)


            context.startActivity(intent)
        }

    }


}