package com.cys.honeydog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.R
import com.cys.honeydog.databinding.RecyclerCommentItemBinding
import com.cys.honeydog.model.CommentItem
import com.cys.honeydog.model.DogCommentItem
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

class DogCommentAdapter(var context: Context, var items: MutableList<DogCommentItem>) :
    RecyclerView.Adapter<DogCommentAdapter.VH>() {

    inner class VH(val binding: RecyclerCommentItemBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            RecyclerCommentItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: VH, position: Int) {
        val commentItem = items[position]

        if (commentItem.imgUrl.isNullOrEmpty()) {
            Glide.with(context).load(R.drawable.cat_community_header)
                .into(holder.binding.civCommentProfile)
        } else {
            Glide.with(context).load(commentItem.imgUrl).into(holder.binding.civCommentProfile)
        }
        holder.binding.commentTv.text = commentItem.comment
        holder.binding.commentNum.text = commentItem.no.toString()
        holder.binding.commentNickname.text = commentItem.nickname
        holder.binding.commentNum.text = commentItem.commentNum.toString()

        //가져온 FireBase 데이터를 리사이클러뷰 아이템에 적용
        commentDater(
            holder.binding.civCommentProfile,
            holder.binding.commentNickname,
            holder.binding.commentTv,
            holder.binding.commentNum,
            holder.binding.commentNum
        )

    }

    private fun commentDater(
        profileView: CircleImageView,
        nicknameView: TextView,
        commentView: TextView,
        numView: TextView,
        commentNumView :TextView

    ) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("DogComment")
            .document(G.userAccount!!.id)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val profile = snapshot.getString("imgUrl")
                    val nickname = snapshot.getString("nickname")
                    val comment = snapshot.getString("comment")
                    val no = snapshot.getString("no").toString()
                    val dogcommentNum = snapshot.getString("commentNum").toString()

                    Glide.with(context).load(profile).into(profileView)
                    nicknameView.text = nickname
                    commentView.text = comment
                    numView.text = no
                    commentNumView.text=dogcommentNum


                }
            }
    }
}


