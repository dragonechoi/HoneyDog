package com.cys.honeydog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityCatPostBinding

class CatPostActivity : AppCompatActivity() {
    val binding:ActivityCatPostBinding by lazy { ActivityCatPostBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ViewCatPost()

    }

    fun ViewCatPost(){
        val imgUri=intent.getStringExtra("imgUri")
        val title= intent.getStringExtra("title")
        val nickname= intent.getStringExtra("nickname")
        val postText = intent.getStringExtra("postText")
        val profile = intent.getStringExtra("profile")


        Glide.with(this).load(profile).into(binding.postCiv)
        Glide.with(this).load(imgUri).into(binding.postImv)
        binding.titleTv.text=title
        binding.postText.text=postText
        binding.postId.text=nickname


    }
}