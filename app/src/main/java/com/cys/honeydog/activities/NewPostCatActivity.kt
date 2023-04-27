package com.cys.honeydog.activities

import android.content.DialogInterface
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityNewPostCatBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Date

class NewPostCatActivity : AppCompatActivity() {
    val binding: ActivityNewPostCatBinding by lazy {
        ActivityNewPostCatBinding.inflate(
            layoutInflater
        )
    }
    private var backPressedTime: Long = 0 // 뒤로가기 버튼을 누른 시간을 저장할 변수
    var imguri: Uri? = null
    var profileUrl: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageBtn.setOnClickListener { choiceImg() }

        binding.upLoadBtn.setOnClickListener { upLoadBtn() }

        saveUserProfile()


    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 20) { // 2초 이내에 뒤로가기 버튼을 다시 누른 경우
            super.onBackPressed()
        } else {
            backPressedTime = currentTime // 이전 시간을 현재 시간으로 대체
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("게시글 작성을 종료 하시겠습니까??")
            builder.setPositiveButton("예",
                DialogInterface.OnClickListener { dialog, which ->
                    finish()
                })
            builder.setNegativeButton("아니오", null)
            builder.show()
        }
    }

    private fun choiceImg() {
        val intent: Intent = Intent(MediaStore.ACTION_PICK_IMAGES)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode != RESULT_CANCELED) {

            imguri = it.getData()?.getData()

            Glide.with(this).load(imguri).into(binding.resultImage)

        }
    }

    private fun upLoadBtn() {
        val title: String = binding.postTitle.text.toString()
        val postText: String = binding.etPost.text.toString()
        val nickname: String = binding.postId.text.toString()
        val userId: String = binding.accountId.text.toString()

        val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()
        val catPostRef = firebase.collection("catPost")

        val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()

        if (imguri == null) {
            val catPost: MutableMap<String, Any> = HashMap()
            catPost["title"] = title
            catPost["postText"] = postText
            catPost["nickname"] = nickname
            catPost["userId"] = userId
            catPost["profile"] = profileUrl!!

            // 이전에 저장된 데이터 개수를 가져와서 1을 더해 새로운 데이터를 추가합니다.
            catPostRef.get().addOnSuccessListener { querySnapshot ->
                val no = querySnapshot.size() + 1
                catPost["no"] = no
                catPostRef.add(catPost).addOnSuccessListener {
                    val commentData: MutableMap<String, Any> = HashMap()
                    commentData["timestamp"] = FieldValue.serverTimestamp()
                    commentData["userId"] = userId
                    commentData["nickname"] = nickname
                    commentData["profile"] = profileUrl!!


                    Toast.makeText(this, "게시글 작성 완료", Toast.LENGTH_SHORT).show()
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "게시글 작성에 실패 했습니다 다시 확인 부탁드립니다.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "게시글 작성에 실패 했습니다 다시 확인 부탁드립니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            val sdf = SimpleDateFormat("yyyyMMddHHmm")
            val fileName = "IMG" + sdf.format(Date()) + ".png"

            val imgRef: StorageReference = firebaseStorage.getReference("photo/$fileName")

            imgRef.putFile(imguri!!)
                .addOnSuccessListener {
                    imgRef.downloadUrl.addOnSuccessListener { imgUri ->
                        val catPost: MutableMap<String, Any> = HashMap()
                        catPost["title"] = title
                        catPost["postText"] = postText
                        catPost["nickname"] = nickname
                        catPost["userId"] = userId
                        catPost["profile"] = profileUrl!!
                        catPost["imgUri"] = imgUri.toString()

                        // 이전에 저장된 데이터 개수를 가져와서 1을 더해 새로운 데이터를 추가합니다.
                        catPostRef.get().addOnSuccessListener { querySnapshot ->
                            val no = querySnapshot.size() + 1
                            catPost["no"] = no
                            catPostRef.add(catPost).addOnSuccessListener {
                                Toast.makeText(this, "게시글 업로드 성공", Toast.LENGTH_SHORT).show()
                                finish()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    this,
                                    "게시글 업로드에 실패 했습니다 다시 확인 부탁드립니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }.addOnFailureListener { e ->

                        }
                    }
                }


        }

    }

    fun saveUserProfile() {
        val db = FirebaseFirestore.getInstance()
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

                } else {
                    Glide.with(this).load(R.drawable.baseline_insert_photo_24)
                }
            }
            .addOnFailureListener { e ->
                // 문서 읽기 실패
                Toast.makeText(this, "서버 문제로 인해 유저 정보를 불러오지 못했습니다", Toast.LENGTH_SHORT).show()
            }
    }
}
