package com.cys.honeydog.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cys.honeydog.R
import com.cys.honeydog.adapters.DogCmmAdapter
import com.cys.honeydog.databinding.ActivityDogCmmBinding
import com.cys.honeydog.model.DogCmmItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class DogCmmActivity : AppCompatActivity() {
    val binding: ActivityDogCmmBinding by lazy { ActivityDogCmmBinding.inflate(layoutInflater) }
    var item: MutableList<DogCmmItem> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadData()

        binding.recyclerDogCmm.adapter = DogCmmAdapter(this, item)

        binding.editPost.setOnClickListener {newPostBtn()}
        binding.communityIntentBtn.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CatCmmActivity::class.java
                )
            )
        }
        binding.goHome.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
    }

    fun newPostBtn(){
        startActivity(
            Intent(
                this,
                NewPostActivity::class.java
            )
        )




    }

    override fun onResume() {
        super.onResume()
    }

    private fun loadData() {



    }
}