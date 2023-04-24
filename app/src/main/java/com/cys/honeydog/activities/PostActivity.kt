package com.cys.honeydog.activities

import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.UserProfile
import com.cys.honeydog.adapters.DogCommentAdapter
import com.cys.honeydog.databinding.ActivityPostBinding
import com.cys.honeydog.model.CatCmmItem
import com.cys.honeydog.model.DogCmmItem
import com.cys.honeydog.model.DogCommentItem
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PostActivity : AppCompatActivity() {
    private val binding: ActivityPostBinding by lazy {
        ActivityPostBinding.inflate(
            layoutInflater
        )
    }
    private val commentList: MutableList<DogCommentItem> = mutableListOf()
    private val UserProfile: UserProfile? = null
    private val postItem : DogCmmItem? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ViewCatPost()
        // 댓글 목록을 보여줄 RecyclerView에 어댑터 설정
        binding.recyclerComment.adapter = DogCommentAdapter(this, commentList)

        binding.sharePost.setOnClickListener { clickSharePost() }
        binding.like.setOnClickListener { clickLikeUp() }
        binding.unlike.setOnClickListener { clickUnlikeUp() }



    }

    private fun  clickUnlikeUp(){
        Toast.makeText(this, "+1", Toast.LENGTH_SHORT).show()
    }
    private fun clickLikeUp(){
        Toast.makeText(this, "+1", Toast.LENGTH_SHORT).show()
    }

    private fun clickSharePost(){
        Toast.makeText(this, "공유 구현 예정", Toast.LENGTH_SHORT).show()

    }

    private fun ViewCatPost() {
        val imgUri = intent.getStringExtra("imageUri")
        val title = intent.getStringExtra("title")
        val nickname = intent.getStringExtra("nickname")
        val postText = intent.getStringExtra("postText")
        val profile = intent.getStringExtra("profileUrl")
        val id = intent.getStringExtra("id")
        val no = intent.getIntExtra("no", -1)

        Glide.with(this).load(profile).into(binding.postCiv)
        Glide.with(this).load(imgUri).into(binding.postImv)
        binding.titleTv.text = title
        binding.postText.text = postText
        binding.postId.text = nickname
        binding.id.text = id

        binding.commentBtn.setOnClickListener { commentUpload(no) }
        loadComments()

        binding.deleteTv.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("게시물 삭제")
                .setMessage("정말로 게시물을 삭제하시겠습니까?")
                .setPositiveButton("예") { dialog, which ->
                    val db = FirebaseFirestore.getInstance()
                    db.collection("Post")
                        .whereEqualTo("no", no)
                        .whereEqualTo("id", G.userAccount!!.id)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                db.collection("DogComment").whereEqualTo("no",no).get().addOnSuccessListener { documents ->
                                    for (document in documents){
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

            //댓글 저장
            val commentRef = fireStore.collection("DogComment")
            commentRef.orderBy("DogCommentNum", Query.Direction.DESCENDING).limit(1)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val commentNum = if (!querySnapshot.isEmpty) {
                        querySnapshot.documents[0].getLong("DogCommentNum")!! + 1
                    } else {
                        1
                    }
                    val commentItem = hashMapOf(
                        "comment" to binding.etComment.text.toString(),
                        "nickname" to nickname,
                        "uid" to userId,
                        "imgUrl" to imageUrl,
                        "no" to no,
                        "DogCommentNum" to commentNum
                    )

                commentRef.document().set(commentItem).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this, "댓글이 작성되었습니다.", Toast.LENGTH_SHORT).show()
                        loadComments()

                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(binding.etComment.windowToken, 0)
                        binding.etComment.text.clear()


                    } else {
                        Toast.makeText(this, "댓글 작성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun loadComments() {
        val fireStore = FirebaseFirestore.getInstance()
        val commentRef = fireStore.collection("DogComment")
            .whereEqualTo("no", intent.getIntExtra("no", -1))
            .orderBy("DogCommentNum",Query.Direction.DESCENDING)

        commentRef.get().addOnSuccessListener { documents ->
            commentList.clear()

            for (document in documents) {
                val comment = document.toObject(DogCommentItem::class.java)
                commentList.add(comment)
            }
            binding.recyclerComment.adapter = DogCommentAdapter(this, commentList)
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "댓글 불러오기 실패", Toast.LENGTH_SHORT).show()
                Log.i("Error","${exception.message}")
        }
    }
}