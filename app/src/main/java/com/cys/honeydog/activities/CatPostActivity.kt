package com.cys.honeydog.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.adapters.CatCommentAdapter
import com.cys.honeydog.databinding.ActivityCatPostBinding
import com.cys.honeydog.model.CommentItem
import com.cys.honeydog.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class CatPostActivity : AppCompatActivity() {
    private val binding: ActivityCatPostBinding by lazy {
        ActivityCatPostBinding.inflate(
            layoutInflater
        )
    }
    private val commentList: MutableList<CommentItem> = mutableListOf()
    private val UserProfile: UserProfile? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ViewCatPost()
        // 댓글 목록을 보여줄 RecyclerView에 어댑터 설정
        binding.recyclerComment.adapter = CatCommentAdapter(this, commentList)

        binding.sharePost.setOnClickListener { clickSharePost() }
        binding.like.setOnClickListener { Toast.makeText(this, "+1", Toast.LENGTH_SHORT).show() }
        binding.unlike.setOnClickListener { Toast.makeText(this, "+1", Toast.LENGTH_SHORT).show() }

    }

    private fun clickSharePost() {
        Toast.makeText(this, "공유", Toast.LENGTH_SHORT).show()
    }

    private fun ViewCatPost() {
        val imgUri = intent.getStringExtra("imgUri")
        val title = intent.getStringExtra("title")
        val nickname = intent.getStringExtra("nickname")
        val postText = intent.getStringExtra("postText")
        val profile = intent.getStringExtra("profile")
        val no = intent.getIntExtra("no", -1)


        Glide.with(this).load(profile).into(binding.postCiv)
        Glide.with(this).load(imgUri).into(binding.postImv)
        binding.titleTv.text = title
        binding.postText.text = postText
        binding.postId.text = nickname
        loadComments()

        binding.commentBtn.setOnClickListener { commentUpload(no) }

        binding.deleteTv.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("게시물 삭제")
                .setMessage("정말로 게시물을 삭제하시겠습니까?")
                .setPositiveButton("예") { dialog, which ->
                    val db = FirebaseFirestore.getInstance()
                    db.collection("catPost")
                        .whereEqualTo("no", no)
                        .whereEqualTo("userId", G.userAccount!!.id)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                db.collection("CatComment").whereEqualTo("no", no).get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            document.reference.delete()
                                        }
                                    }
                                document.reference.delete()
                                finish()
                            }
                        }
                }
                .setNegativeButton("아니오", null)
                .show()
        }
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
            val commentCatRef = fireStore.collection("CatComment")
            commentCatRef.orderBy("commentNum", Query.Direction.DESCENDING).limit(1)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val commentNum = if (!querySnapshot.isEmpty) {
                        querySnapshot.documents[0].getLong("commentNum")!! + 1
                    } else {
                        1
                    }

                    val commentItem = hashMapOf(
                        "comment" to binding.etComment.text.toString(),
                        "nickname" to nickname,
                        "uid" to userId,
                        "imgUrl" to imageUrl,
                        "no" to no,
                        "commentNum" to commentNum

                    )

                    commentCatRef.document().set(commentItem).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "댓글이 작성되었습니다.", Toast.LENGTH_SHORT).show()
                            loadComments()

                            val imm =
                                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(binding.etComment.windowToken, 0)
                            binding.etComment.text.clear()

                        } else {
                            Toast.makeText(this, "댓글 작성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }


    fun loadComments() {
        val fireStore = FirebaseFirestore.getInstance()
        val commentRef = fireStore.collection("CatComment")
            .whereEqualTo("no", intent.getIntExtra("no", -1))
            .orderBy("commentNum", Query.Direction.DESCENDING)




        commentRef.get().addOnSuccessListener { documents ->
            commentList.clear()
            Log.i("클리어", "클리어 완료 ")

            for (document in documents) {
                val comment = document.toObject(CommentItem::class.java)
                commentList.add(comment)
            }
            binding.recyclerComment.adapter = CatCommentAdapter(this, commentList)
        }.addOnFailureListener { exception ->
            // 실패 시 동작
            Toast.makeText(this, "댓글 불러오기 실패", Toast.LENGTH_SHORT).show()
            Log.i("Error", "${exception.message}")
        }
    }

}


