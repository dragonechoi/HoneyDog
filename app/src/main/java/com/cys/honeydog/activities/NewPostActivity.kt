package com.cys.honeydog.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cys.honeydog.databinding.ActivityNewPostBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.Date

class NewPostActivity : AppCompatActivity() {
    val binding: ActivityNewPostBinding by lazy { ActivityNewPostBinding.inflate(layoutInflater) }
    var imgUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.upLoadBtn.setOnClickListener { UpLoadBtn() }

        binding.imageBtn.setOnClickListener { clickImgSelectBnt() }

    }

    private fun UpLoadBtn() {
        //DB입력한 게시글 재목 및 내용 작성
        var title: String = binding.postTitle.text.toString()
        var postText: String = binding.etPost.text.toString()


        //FireStore DB저장 [ Map Collection 단위로 저장]
        val firestore = FirebaseFirestore.getInstance()

        val PostnRef = firestore.collection("Post")



        //Field값들을 Map 으로 준비
        val person: MutableMap<String, Any> = HashMap()
        person["title"] = title
        person["postText"] = postText



        PostnRef.add(person).addOnSuccessListener {
            finish()
        }.addOnFailureListener {

        }


        //DB 이미지 저장
        if (imgUri == null) return
        var fireBase: FirebaseStorage = FirebaseStorage.getInstance()


        //저장할 파일명이 중복되지 않도록..날짜로 변수명 정하기
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        val fileName = "IMG_" + sdf.format(Date()) + ".png"


        //저장할 파일 위치에 대한 참조객체
        val imgRef: StorageReference =
            fireBase.getReference("photo/$fileName") //photo 폴더가 없으면 만들고..있으면 그냥 참조

        //위 저장경로 참조객체에게 실제 파일업로드 시키기
        imgRef.putFile(imgUri!!).addOnSuccessListener {
            Toast.makeText(
                this,
                "게시글 업로드 완료",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener { e ->
            Toast.makeText(
                this,
                "에러 : " + e.message,
                Toast.LENGTH_SHORT
            ).show()
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

