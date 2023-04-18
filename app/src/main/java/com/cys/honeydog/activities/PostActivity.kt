package com.cys.honeydog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.R
import com.cys.honeydog.UserProfile
import com.cys.honeydog.adapters.CatCommentAdapter
import com.cys.honeydog.adapters.DogCommentAdapter
import com.cys.honeydog.databinding.ActivityCatPostBinding
import com.cys.honeydog.databinding.ActivityPostBinding
import com.cys.honeydog.model.CommentItem
import com.cys.honeydog.model.DogCommentItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class PostActivity : AppCompatActivity() {
    private val binding: ActivityCatPostBinding by lazy {
        ActivityCatPostBinding.inflate(
            layoutInflater
        )
    }
    private val commentList: MutableList<DogCommentItem> = mutableListOf()
    private val UserProfile: UserProfile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ViewCatPost()
        // 댓글 목록을 보여줄 RecyclerView에 어댑터 설정
        binding.recyclerComment.adapter = DogCommentAdapter(this, commentList)

    }

    private fun ViewCatPost() {
        val imgUri = intent.getStringExtra("imgUri")
        val title = intent.getStringExtra("title")
        val nickname = intent.getStringExtra("nickname")
        val postText = intent.getStringExtra("postText")
        val profile = intent.getStringExtra("profileUrl")
        val no = intent.getIntExtra("no", -1)

        Glide.with(this).load(profile).into(binding.postCiv)
        Glide.with(this).load(imgUri).into(binding.postImv)
        binding.titleTv.text = title
        binding.postText.text = postText
        binding.postId.text = nickname
        loadComments()

        binding.commentBtn.setOnClickListener { commentUpload(no) }
    }

    private fun commentUpload(no: Int) {
        val fireStore = FirebaseFirestore.getInstance()
        val userId = G.userAccount?.id ?: return

        // idUsers 컬렉션에서 유저의 닉네임과 imgUrl 가져오기
        val idUserDocRef = fireStore.collection("idUsers").document(userId)
        idUserDocRef.get().addOnSuccessListener { idUserDocSnapshot ->
            val nickname = idUserDocSnapshot.getString("nickname") ?: UserProfile?.nickname
            val imageUrl = idUserDocSnapshot.getString("imageUrl") ?: UserProfile?.ProfileUri

            // comment 컬렉션에 댓글 저장
            val commentDocRef = fireStore.collection("DogComment").document()
            val commentItem = hashMapOf(
                "comment" to binding.etComment.text.toString(),
                "nickname" to nickname,
                "uid" to userId,
                "imgUrl" to imageUrl,
                "no" to no
            )

            commentDocRef.set(commentItem).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "댓글이 작성되었습니다.", Toast.LENGTH_SHORT).show()
                    loadComments()
                } else {
                    Toast.makeText(this, "댓글 작성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun loadComments() {
        val fireStore = FirebaseFirestore.getInstance()
        val commentRef =
            fireStore.collection("DogComment").whereEqualTo("no", intent.getIntExtra("no", -1))

        commentRef.get().addOnSuccessListener { documents ->
            commentList.clear()
            for (document in documents) {
                val comment = document.toObject(DogCommentItem::class.java)
                commentList.add(comment)
            }
            binding.recyclerComment.adapter = DogCommentAdapter(this, commentList)
        }.addOnFailureListener { exception ->

        }
    }
}