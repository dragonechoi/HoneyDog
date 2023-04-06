package com.cys.honeydog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    val binding:ActivityPostBinding by lazy { ActivityPostBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


//        var title:String?=intent.getStringExtra("title")
//
//        var nickname:String=intent.getStringExtra("nickname") as String
//
//        var image:Int=intent.getIntExtra("image",R.drawable.cogi_love)
//
//
//        binding.titleTv.text=title
//
//        binding.postId.text=nickname
//
//        Glide.with(this).load(image).into(binding.postImv)




    }
}