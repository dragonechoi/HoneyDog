package com.cys.honeydog.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cys.honeydog.R
import com.cys.honeydog.adapters.CatCmmAdapter
import com.cys.honeydog.databinding.ActivityCatCmmBinding
import com.cys.honeydog.model.CatCmmItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CatCmmActivity : AppCompatActivity() {
    val binding: ActivityCatCmmBinding by lazy { ActivityCatCmmBinding.inflate(layoutInflater) }
    var item: MutableList<CatCmmItem> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
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

        binding.swipeRefreshLayout.setOnRefreshListener {

            //데이터 로드
            loadData()
        }
    }


    private fun loadData() {
        val fireStore = FirebaseFirestore.getInstance()
        val postRef = fireStore.collection("catPost")
            .orderBy("no", Query.Direction.DESCENDING)

        item.clear()

        //Post 컬렉션데이터 호출
        postRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val post = document.toObject(CatCmmItem::class.java)
                item.add(post)
            }
            val adapter = binding.recyclerCatCmm.adapter as? CatCmmAdapter
            adapter?.items = item
            //아답터 업데이트
            adapter?.notifyDataSetChanged()

            //여기서 새로고침 기능을 수행
            binding.swipeRefreshLayout.isRefreshing = false
            binding.swipeRefreshLayout.isEnabled = true
            binding.swipeRefreshLayout.setColorSchemeResources(R.color.purple_200)
        }.addOnFailureListener { exception ->

        }
    }
}