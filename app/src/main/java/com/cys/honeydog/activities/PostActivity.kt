package com.cys.honeydog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    val binding: ActivityPostBinding by lazy { ActivityPostBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        viewPost()


    }



    fun viewPost(){
        val imageUri = intent.getStringExtra("image")
        val title = intent.getStringExtra("title")
        val nickname = intent.getStringExtra("nickname")
        val postText = intent.getStringExtra("postText")
        val profile = intent.getStringExtra("profileUrl")



        Glide.with(this).load(imageUri).into(binding.postImv)
        Glide.with(this).load(profile).into(binding.postCiv)
        binding.titleTv.text = title
        binding.postId.text = nickname
        binding.postText.text = postText




    }


    fun comment(){

    }
}