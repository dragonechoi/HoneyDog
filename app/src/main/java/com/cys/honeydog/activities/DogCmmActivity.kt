package com.cys.honeydog.activities

import DogCmmAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cys.honeydog.databinding.ActivityDogCmmBinding
import com.cys.honeydog.model.DogCmmItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class DogCmmActivity : AppCompatActivity() {
    val binding: ActivityDogCmmBinding by lazy { ActivityDogCmmBinding.inflate(layoutInflater) }
    var item: MutableList<DogCmmItem> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)





        binding.editPost.setOnClickListener { newPostBtn() }
        binding.communityIntentBtn.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CatCmmActivity::class.java

                )

            )
            finish()
        }
        binding.goHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.recyclerDogCmm.adapter = DogCmmAdapter(this, item)
        loadData()

    }

    fun newPostBtn() {
        startActivity(
            Intent(this, NewPostActivity::class.java ))
    }

    override fun onResume() {
        super.onResume()
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
                binding.swipeRefreshLayout.isEnabled=true
        }

    }

    private fun loadData() {
        val fireStore = FirebaseFirestore.getInstance()
        val postRef = fireStore.collection("Post")
            .orderBy("no", Query.Direction.DESCENDING)

        //Post 컬렉션데이터 호춣
        postRef.get().addOnSuccessListener { documents ->
            item.clear() // 기존에 있던 데이터를 삭제
            for (document in documents) {
                val post = document.toObject(DogCmmItem::class.java)
                item.add(post)
            }

            //아답터 업데이트
            binding.recyclerDogCmm.adapter?.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false
        }.addOnFailureListener { exception ->

        }
    }
}
