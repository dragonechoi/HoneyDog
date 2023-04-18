package com.cys.honeydog.activities

import DogCmmAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cys.honeydog.databinding.ActivityDogCmmBinding
import com.cys.honeydog.model.DogCmmItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class DogCmmActivity : AppCompatActivity() {
    val binding: ActivityDogCmmBinding by lazy { ActivityDogCmmBinding.inflate(layoutInflater) }
    var item: MutableList<DogCmmItem> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadData()

        binding.recyclerDogCmm.adapter = DogCmmAdapter(this, item)


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

    }

    fun newPostBtn() {
        startActivity(
            Intent(
                this,
                NewPostActivity::class.java
            )
        )
    }

    private fun loadData() {
        val fireStore = FirebaseFirestore.getInstance()
        val postRef = fireStore.collection("Post")

        //Post 컬렉션데이터 호춣
        postRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val post = document.toObject(DogCmmItem::class.java)
                item.add(post)
            }

            //아답터 업데이트
            binding.recyclerDogCmm.adapter?.notifyDataSetChanged()
        }.addOnFailureListener { exception ->

        }

    }
}









