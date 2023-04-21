package com.cys.honeydog.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.R
import com.cys.honeydog.activities.CatPostActivity
import com.cys.honeydog.databinding.RecyclerCommunityListItemBinding
import com.cys.honeydog.model.CatCmmItem
import com.google.firebase.firestore.FirebaseFirestore

class CatCmmAdapter(var context: Context, var items: MutableList<CatCmmItem>) :
    Adapter<CatCmmAdapter.VH>() {


    inner class VH(val binding: RecyclerCommunityListItemBinding) :
        ViewHolder(binding.root) {

    }

    //ViewHolder가 생성 되었을때 ViewBinding 해주기
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding =
            RecyclerCommunityListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(binding)
    }

    //MutableList<DogCmmItem>의 사이즈 = 값 찻지
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var list: CatCmmItem = items[position]


        if (list.imgUri.isNullOrEmpty()) {
            // 이미지가 없을 경우 대체 이미지 사용
            Glide.with(context).load(R.drawable.cat_community_header)
                .into(holder.binding.communityListIv)
        } else {
            // 이미지가 있을 경우 원본 이미지 사용
            Glide.with(context).load(list.imgUri).into(holder.binding.communityListIv)
        }

        holder.binding.communityListTitle.text = list.title
        holder.binding.communityListNickname.text = list.nickname
        holder.binding.num.text = list.no?.toString()

        // 닉네임과 프로필 이미지 설정하기
        loadCatPost(
            holder.binding.communityListTitle,
            holder.binding.communityListIv,
            holder.binding.communityListNickname
        )

        holder.binding.communityList.setOnClickListener {
            val intent: Intent = Intent(context, CatPostActivity::class.java)
            intent.putExtra("imgUri", list.imgUri)
            intent.putExtra("title", list.title)
            intent.putExtra("nickname", list.nickname)
            intent.putExtra("postText", list.postText)
            intent.putExtra("profile", list.profile)
            intent.putExtra("userId", list.userId)
            intent.putExtra("no", list.no)



            context.startActivity(intent)
        }
    }

    private fun loadCatPost(titleView: TextView, img: ImageView, nicknameView: TextView) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("catPost")
            .document(G.userAccount!!.id)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val nickName = snapshot.getString("nickname")
                    val title = snapshot.getString("title")
                    val iv = snapshot.getString("imgUri")
                    titleView.text = title
                    Glide.with(context).load(iv).into(img)
                    nicknameView.text = nickName
                }
            }
    }

}