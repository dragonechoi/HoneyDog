package com.cys.honeydog.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.Profile
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityNewPostBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.Date

class NewPostActivity : AppCompatActivity() {
    val binding: ActivityNewPostBinding by lazy { ActivityNewPostBinding.inflate(layoutInflater) }
    var imgUri: Uri? = null
    var profileUrl: String? = null  // 클래스 변수로 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.upLoadBtn.setOnClickListener { upLoadBtn() }

        binding.imageBtn.setOnClickListener { clickImgSelectBnt() }

        saveUserProfile()
    }

    fun saveUserProfile(){
        val db=FirebaseFirestore.getInstance()
        db.collection("idUsers")
            .document(G.userAccount!!.id)
            .get()
            .addOnSuccessListener { document ->
                val nickname = document.getString("nickname")
                profileUrl = document.getString("imageUrl")  // 클래스 변수에 저장
                val userId = document.getString("id")
                binding.accountId.text = userId
                binding.postId.text = nickname

                if (profileUrl != null && binding.postCiv != null) {
                    Glide.with(this)
                        .load(profileUrl)
                        .into(binding.postCiv)
                    Toast.makeText(this, "읽어오기 성공", Toast.LENGTH_SHORT).show()
                }else{
                    Glide.with(this).load(R.drawable.baseline_insert_photo_24)
                }
            }
            .addOnFailureListener { e ->
                // 문서 읽기 실패
                Toast.makeText(this, "서버 문제로 인해 유저 정보를 불러오지 못했습니다", Toast.LENGTH_SHORT).show()
            }
    }

    private fun upLoadBtn() {
        //DB입력한 게시글 제목 및 내용 작성
        val title: String = binding.postTitle.text.toString()
        val postText: String = binding.etPost.text.toString()
        val nickname: String = binding.postId.text.toString()
        val userId: String = binding.accountId.text.toString()

        //FireStore DB 저장 [ Map Collection 단위로 저장]
        val firestore = FirebaseFirestore.getInstance()
        val postRef = firestore.collection("Post")

        //DB 이미지 저장
        val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()

        if (imgUri == null ) {
            // 이미지를 선택하지 않은 경우, 이미지 없이 게시글만 저장
            val post: MutableMap<String, Any> = HashMap()
            post["title"] = title
            post["postText"] = postText
            post["nickname"] = nickname
            post["profileUrl"] = profileUrl!!
            post["id"] = userId

            postRef.add(post).addOnSuccessListener {
                Toast.makeText(
                    this,
                    "게시글 업로드 완료",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }.addOnFailureListener { e ->

            }
        } else {
            // 이미지를 선택한 경우, 이미지 업로드 후 게시글 저장
            //저장할 파일명이 중복되지 않도록..날짜로 변수명 정하기
            val sdf = SimpleDateFormat("yyyyMMddHHmmss")
            val fileName = "IMG_" + sdf.format(Date()) + ".png"

            //저장할 파일 위치에 대한 참조객체
            val imgRef: StorageReference = firebaseStorage.getReference("photo/$fileName")

            //위 저장경로 참조객체에게 실제 파일업로드 시키기
            imgRef.putFile(imgUri!!)
                .addOnSuccessListener {
                    // 이미지 업로드 성공
                    imgRef.downloadUrl.addOnSuccessListener { imgUri ->
                        // 게시글 데이터 추가
                        val post: MutableMap<String, Any> = HashMap()
                        post["title"] = title
                        post["postText"] = postText
                        post["nickname"] = nickname
                        post["profileUrl"] = profileUrl!!
                        post["id"] = userId
                        post["imageUri"] = imgUri.toString()

                        postRef.add(post).addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "게시글 업로드 완료",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }.addOnFailureListener { e ->

                        }
                    }
                }
        }
    }



    private fun clickImgSelectBnt() {
        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
        resultLauncher.launch(intent)

    }

    var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode != RESULT_CANCELED) {

            imgUri = it.getData()?.getData()

            Glide.with(this).load(imgUri).into(binding.resultImage)

        }
    }
}

