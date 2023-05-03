package com.cys.honeydog.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityNewIdBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Date


class NewIdActivity : AppCompatActivity() {
    val binding: ActivityNewIdBinding by lazy { ActivityNewIdBinding.inflate(layoutInflater) }
    var ac: AutoCompleteTextView? = null
    var imgUri: Uri? = null

    lateinit var profileUrl: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ac = binding.acTv
        val animal = resources.getStringArray(R.array.dog)
        val adapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_dropdown_item_1line, animal)
        ac!!.setAdapter(adapter)

        binding.clickBtn.setOnClickListener { clickBtn() }
        binding.profileNewIv.setOnClickListener { clickChangeImage() }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    fun clickBtn() {

        if (imgUri == null) {
            profileUrl = ""
            saveUserId()
        } else {
            var fireBase: FirebaseStorage = FirebaseStorage.getInstance()


            //저장할 파일명이 중복되지 않도록..날짜로 변수명 정하기
            val sdf = java.text.SimpleDateFormat("yyyyMMddHHmmss")
            val fileName = "IMG_" + sdf.format(Date()) + ".png"


            //저장할 파일 위치에 대한 참조객체
            val imgRef: StorageReference =
                fireBase.getReference("userProfile/$fileName") //userProfile 폴더가 없으면 만들고..있으면 그냥 참조

            //위 저장경로 참조객체에게 실제 파일업로드 시키기
            imgRef.putFile(imgUri!!).addOnSuccessListener {
                imgRef.downloadUrl.addOnSuccessListener {
                    profileUrl = it.toString()
                    saveUserId()
                }
            }

        }
    }

    fun saveUserId() {
        val id = binding.etEmail.text.toString()
        val password = binding.etPw.text.toString()
        val passwordConfirm = binding.etPwRecheck.text.toString()
        val nickname = binding.etName.text.toString()
        val animalType = binding.acTv.text.toString()
        val regex =
            "^[A-Za-z0-9]+([._-][A-Za-z0-9]+)*@[A-Za-z0-9]+([.-][A-Za-z0-9]+)*\\.[A-Za-z]{2,}$|^[A-Za-z0-9]+$".toRegex()

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        if (password != passwordConfirm) {
            AlertDialog.Builder(this)
                .setMessage("패스워드 확인에 문제가 생겼습니다")
                .setPositiveButton("확인", null)
                .show()
            binding.etPwRecheck.selectAll()
            return
        }

        if (!id.matches(regex)) {
            AlertDialog.Builder(this)
                .setMessage("아이디 입력에 문제가 생겼습니다")
                .setPositiveButton("확인", null)
                .show()
            binding.etEmail.selectAll()
            return
        }

        db.collection("idUsers")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener {
                if (it.documents.size > 0) {
                    AlertDialog.Builder(this)
                        .setMessage("중복된 ID(아이디)가 있습니다.\n다시 확인한 후 입력 해주시기 바랍니다.")
                        .setPositiveButton("확인", null)
                        .show()
                    binding.etEmail.requestFocus()
                    binding.etEmail.selectAll()
                    return@addOnSuccessListener
                }

                val user: MutableMap<String, String> = mutableMapOf()
                user.put("id", id)
                user.put("password", password)
                user.put("nickname", nickname)
                user.put("animalType", animalType)
                user.put("imageUrl", profileUrl)

                db.collection("idUsers").document(id)
                    .set(user)
                    .addOnSuccessListener {
                        AlertDialog.Builder(this)
                            .setMessage("환영합니다. 회원가입에 성공 하였습니다.")
                            .setPositiveButton("확인", null)
                            .show()
                        finish()
                    }
                    .addOnFailureListener {
                        AlertDialog.Builder(this)
                            .setMessage("회원가입에 실패 하였습니다.")
                            .setPositiveButton("확인", null)
                            .show()
                        return@addOnFailureListener
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }


    fun clickChangeImage() {
        //이미지 선택
        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode != RESULT_CANCELED) {

            imgUri = it.getData()?.getData()
            Glide.with(this).load(imgUri).into(binding.profileNewIv)

        }
    }

}




