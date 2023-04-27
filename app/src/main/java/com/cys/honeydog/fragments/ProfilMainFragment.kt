package com.cys.honeydog.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.cys.honeydog.G
import com.cys.honeydog.adapters.ProfilFragmentAdapter
import com.cys.honeydog.databinding.FragmentProfilMainBinding
import com.cys.honeydog.model.ProfilRecyclerItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Date

class ProfilMainFragment : Fragment() {
    private lateinit var Binding: FragmentProfilMainBinding
    var item: MutableList<ProfilRecyclerItem> = mutableListOf()

    var imgUri: Uri? = null
    var nickname: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Binding = FragmentProfilMainBinding.inflate(inflater, container, false)
        return Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Binding.profilRecycler.adapter = ProfilFragmentAdapter(requireContext(), item)

        Binding.profilImage.setOnClickListener { clickChangeProfile() }

        Binding.chProfileBtn.setOnClickListener { ClickChangeProfileBtn() }
        loadData()

        // Firebase 데이터 가져오기
        val db = FirebaseFirestore.getInstance()
        db.collection("idUsers")
            .document(G.userAccount!!.id)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    // 오류 처리
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    // 닉네임 가져오기
                    val nickname = snapshot.getString("nickname")

                    // 프로필 이미지 가져오기
                    val profileUrl = snapshot.getString("imageUrl")

                    // 닉네임을 TextView에 설정
                    Binding.profilNickname.text = nickname

                    // Glide를 사용하여 이미지 로드 및 ImageView에 설정
                    if (profileUrl != null) {
                        Glide.with(this)
                            .load(profileUrl)
                            .into(Binding.profilImage)
                    }
                }
            }
        recyclerView()

    }

    private fun recyclerView(){
        val firestore=FirebaseFirestore.getInstance()
        firestore.collection("idUsers").document(G.userAccount!!.id)

            }





    fun ClickChangeProfileBtn() {
        val db = FirebaseFirestore.getInstance()
        val documentRef = db.collection("idUsers").document(G.userAccount!!.id)
        val updates = hashMapOf<String, Any>()

        if (imgUri != null) {

            // 이미지 업로드
            val fireBase: FirebaseStorage = FirebaseStorage.getInstance()

            // 저장할 파일명이 중복되지 않도록 날짜로 변수명 정하기
            val sdf = java.text.SimpleDateFormat("yyyyMMddHHmmss")
            val fileName = "IMG_" + sdf.format(Date()) + ".png"

            // 저장할 파일 위치에 대한 참조객체
            val imgRef: StorageReference = fireBase.getReference("userProfile/$fileName")

            // 위 저장경로 참조객체에게 실제 파일 업로드 시키기
            imgRef.putFile(imgUri!!).addOnSuccessListener {
                imgRef.downloadUrl.addOnSuccessListener { uri ->
                    // 이미지 URL 업데이트
                    val imgUrl = uri.toString()
                    updates["imageUrl"] = imgUrl


                    // 프로필 정보 업데이트
                    documentRef.update(updates).addOnSuccessListener {


                    }.addOnFailureListener { e ->
                        // 업데이트 실패

                    }
                }
            }
        } else {
            // 이미지가 선택되지 않은 경우, 프로필 정보 업데이트
            documentRef.update(updates).addOnSuccessListener {
                Log.i("이미지 업로드 성공", "성공")

            }.addOnFailureListener { e ->

            }

        }

        // 닉네임 업데이트
        val newNickname = Binding.etNameChange.text.toString()
        if (newNickname.isNotEmpty()) {
            updates["nickname"] = newNickname
        }

        documentRef.update(updates)
            .addOnSuccessListener {
                // 업데이트 성공
                Toast.makeText(context, "프로필 변경 완료", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                // 업데이트 실패
                Toast.makeText(context, "프로필 변경 실패", Toast.LENGTH_SHORT).show()
            }
    }


    private fun loadData() {
        val fireStore = FirebaseFirestore.getInstance()
        val postRef = fireStore.collection("Post")

        //Post 컬렉션데이터 호출
        postRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val post = document.toObject(ProfilRecyclerItem::class.java)
                item.add(post)
            }
            // 데이터를 모두 가져온 후 어댑터 설정

            val adapter = ProfilFragmentAdapter(requireContext(), item)
            Binding.profilRecycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }.addOnFailureListener {
            // 호출 실패 시 처리할 내용
            Log.i("Error", it.message.toString())
        }
    }

    private fun clickChangeProfile() {
        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            var imageUri = result.data?.data
            Binding.profilImage.setImageURI(imageUri)

            imgUri = imageUri

        }
    }
}
