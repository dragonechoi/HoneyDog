package com.cys.honeydog.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.R
import com.cys.honeydog.adapters.CatCmmAdapter
import com.cys.honeydog.databinding.ActivityCatCmmBinding
import com.cys.honeydog.model.CatCmmItem
import com.cys.honeydog.model.DogCmmItem
import com.cys.honeydog.model.ProfileItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class CatCmmActivity : AppCompatActivity() {
    val binding: ActivityCatCmmBinding by lazy { ActivityCatCmmBinding.inflate(layoutInflater) }
    var item: MutableList<CatCmmItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadData()

        binding.recyclerCatCmm.adapter = CatCmmAdapter(this, item)



        binding.communityIntentBtn.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DogCmmActivity::class.java
                )
            )
            finish()
        }
        binding.goHome.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        binding.editPost.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    NewPostCatActivity::class.java

                )
            )
            
        }


    }

    override fun onResume() {
        super.onResume()
        binding.swipeRefreshLayout.setOnRefreshListener {
            //데이터 로드
            loadData()
        }
        binding.swipeRefreshLayout.isEnabled = true
    }

    private fun loadData() {
        val fireStore = FirebaseFirestore.getInstance()
        val postRef = fireStore.collection("catPost")

        item.clear()

        //Post 컬렉션데이터 호춣
        postRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val post = document.toObject(CatCmmItem::class.java)
                item.add(post)
            }

            //아답터 업데이트
            binding.recyclerCatCmm.adapter?.notifyDataSetChanged()

            binding.swipeRefreshLayout.isRefreshing = false
        }.addOnFailureListener { exception ->

        }

    }
}