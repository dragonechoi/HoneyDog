package com.cys.honeydog.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.UserProfile
import com.cys.honeydog.adapters.CommentAdapter
import com.cys.honeydog.databinding.ActivityCatPostBinding
import com.cys.honeydog.model.CommentItem
import com.google.firebase.firestore.FirebaseFirestore

class CatPostActivity : AppCompatActivity() {
    val binding: ActivityCatPostBinding by lazy { ActivityCatPostBinding.inflate(layoutInflater) }
    val item: MutableList<CommentItem> = mutableListOf()
    val UserProfile: UserProfile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ViewCatPost()

        binding.recyclerComment.adapter = CommentAdapter(this, item)




    }

    fun ViewCatPost() {
        val imgUri = intent.getStringExtra("imgUri")
        val title = intent.getStringExtra("title")
        val nickname = intent.getStringExtra("nickname")
        val postText = intent.getStringExtra("postText")
        val profile = intent.getStringExtra("profile")
        val no = intent.getIntExtra("no",-1)

        binding.commentBtn.setOnClickListener { commentUpload(no) }

        Glide.with(this).load(profile).into(binding.postCiv)
        Glide.with(this).load(imgUri).into(binding.postImv)
        binding.titleTv.text = title
        binding.postText.text = postText
        binding.postId.text = nickname

        binding.recyclerComment.adapter?.notifyDataSetChanged()


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
            val commentDocRef = fireStore.collection("comment").document()
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
                } else {
                    Toast.makeText(this, "댓글 작성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}

//private fun commentUpload() {
//    val fireStore = FirebaseFirestore.getInstance()
//    val userId = G.userAccount?.id ?: return
//
//    // idUsers 컬렉션에서 유저의 닉네임과 imgUrl 가져오기
//    val idUserDocRef = fireStore.collection("idUsers").document(userId)
//    idUserDocRef.get().addOnSuccessListener { idUserDocSnapshot ->
//        val nickname = idUserDocSnapshot.getString("nickname") ?: UserProfile?.nickname
//        val imageUrl = idUserDocSnapshot.getString("imageUrl") ?: UserProfile?.ProfileUri
//
//        // 게시글의 index값 가져오기
//        val catPostRef = fireStore.collection("catPost").whereEqualTo("userId", userId)
//        catPostRef.get().addOnSuccessListener { querySnapshot ->
//            val catPostDocSnapshot = querySnapshot.documents.firstOrNull()
//            val no = catPostDocSnapshot?.getLong("no")?.toInt()
//
//            // comment 컬렉션에 댓글 저장
//            val commentDocRef = fireStore.collection("comment").document()
//            val commentItem = hashMapOf(
//                "comment" to binding.etComment.text.toString(),
//                "nickname" to nickname,
//                "uid" to userId,
//                "imgUrl" to imageUrl,
//                "no" to no
//            )
//
//            commentDocRef.set(commentItem).addOnCompleteListener { task ->
//                if (task.isSuccessful) {