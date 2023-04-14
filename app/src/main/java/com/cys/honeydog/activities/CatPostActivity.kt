package com.cys.honeydog.activities

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance()
        ViewCatPost()


       binding.recyclerComment.adapter=CommentAdapter(this,item)


        binding.commentBtn.setOnClickListener {
            val commentText=binding.etComment.text.toString()
            if (commentText.isEmpty()){
                Toast.makeText(this, "댓글을 입력해 주세요", Toast.LENGTH_SHORT).show()
            }else{

            }
        }


    }



    fun ViewCatPost(){
        val imgUri=intent.getStringExtra("imgUri")
        val title= intent.getStringExtra("title")
        val postText = intent.getStringExtra("postText")


        Glide.with(this).load(imgUri).into(binding.postImv)
        binding.titleTv.text=title
        binding.postText.text=postText

        // 닉네임과 프로필 사진을 가져와 뷰에 적용
        loadUserData(binding.postId,binding.postCiv)




    }

    private fun loadUserData(nicknameView: TextView, profileImgView: ImageView) {
        val firestore= FirebaseFirestore.getInstance()
        firestore.collection("idUsers")
            .document(G.userAccount!!.id)
            .addSnapshotListener{snapshot, e ->
                if (e != null) {
                    // 오류 처리
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    // 닉네임 가져오기
                    val nickname = snapshot.getString("nickname")
                    // 프로필 사진 가져오기
                    val profileUrl = snapshot.getString("imageUrl")
                    // 가져온 정보로 뷰 업데이트하기
                    nicknameView.text = nickname
                    Glide.with(profileImgView.context).load(profileUrl).into(profileImgView)
                }
            }
    }



}