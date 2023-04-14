package com.cys.honeydog.activities

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.R
import com.cys.honeydog.adapters.CommentAdapter
import com.cys.honeydog.databinding.ActivityCatPostBinding
import com.cys.honeydog.model.CommentItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class CatPostActivity : AppCompatActivity() {
    val binding:ActivityCatPostBinding by lazy { ActivityCatPostBinding.inflate(layoutInflater) }
    val item:MutableList<CommentItem> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ViewCatPost()

       binding.recyclerComment.adapter=CommentAdapter(this,item)


        binding.commentBtn.setOnClickListener { saveUserId() }
        saveUserId()
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

    fun saveUserId(){

    }



}