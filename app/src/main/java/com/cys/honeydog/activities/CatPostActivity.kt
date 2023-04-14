package com.cys.honeydog.activities


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.Profile
import com.cys.honeydog.adapters.CommentAdapter
import com.cys.honeydog.databinding.ActivityCatPostBinding
import com.cys.honeydog.model.CommentItem
import com.google.firebase.firestore.FirebaseFirestore

class CatPostActivity : AppCompatActivity() {
    val binding: ActivityCatPostBinding by lazy { ActivityCatPostBinding.inflate(layoutInflater) }
    var item: MutableList<CommentItem> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ViewCatPost()
        binding.recyclerComment.adapter = CommentAdapter(this, item)
        binding.commentBtn.setOnClickListener { comment() }

    }

    fun ViewCatPost() {
        val imgUri = intent.getStringExtra("imgUri")
        val title = intent.getStringExtra("title")
        val nickname = intent.getStringExtra("nickname")
        val postText = intent.getStringExtra("postText")
        val profile = intent.getStringExtra("profile")

        Glide.with(this).load(profile).into(binding.postCiv)
        Glide.with(this).load(imgUri).into(binding.postImv)
        binding.titleTv.text = title
        binding.postText.text = postText
        binding.postId.text = nickname

    }

    fun comment() {
        val comment = binding.etComment.text.toString()
        val firestore = FirebaseFirestore.getInstance()
        val postCommentsRef =
            firestore.collection("idUsers").document("post_id_1").collection("post_id_1_comments")
        val commentData = hashMapOf("comment" to comment, "id" to G.userAccount!!.id, "nickname" to Profile.profileInfo!!.nickname)
        postCommentsRef.add(commentData)
            .addOnSuccessListener { documentReference ->
                val newComment = CommentItem(G.userAccount!!.id, Profile.profileInfo!!.ProfileUri, Profile.profileInfo!!.nickname, comment)
                item.add(newComment)
                binding.recyclerComment.adapter?.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to add comment: ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }
}





