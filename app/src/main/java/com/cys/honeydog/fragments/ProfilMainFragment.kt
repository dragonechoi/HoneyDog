package com.cys.honeydog.fragments

import android.app.Activity
import android.content.Context
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
    lateinit var Pcontext: Context  //profile 변경및 닉네임 변경시 사용될 Context 초기화
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
                        Glide.with(Pcontext)
                            .load(profileUrl)
                            .into(Binding.profilImage)
                    }
                }
            }


    }

    fun ClickChangeProfileBtn() {
        val db = FirebaseFirestore.getInstance()
        val documentRef = db.collection("idUsers").document(G.userAccount!!.id)
        val poostRef = db.collection("Post").document()
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
                Toast.makeText(Pcontext, "프로필 변경 완료", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                // 업데이트 실패
                Toast.makeText(Pcontext, "프로필 변경 실패", Toast.LENGTH_SHORT).show()
            }
        // Firebase 데이터 가져오기

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
                        Glide.with(Pcontext)
                            .load(profileUrl)
                            .into(Binding.profilImage)
                    }


                    // 작성자 닉네임 변경
                    val postUpdates = hashMapOf<String, Any>()
                    postUpdates["nickname"] = newNickname
                    postUpdates["profileUrl"] = profileUrl.toString()
                    db.collection("Post")
                        .whereEqualTo("id", G.userAccount!!.id)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                document.reference.update(postUpdates)
                            }
                        }
                        .addOnFailureListener { e ->
                            // 실패 처리
                        }
                    val CatPostUpdate = HashMap<String , Any>()
                    CatPostUpdate ["nickname"] = newNickname
                    CatPostUpdate ["profile"] = profileUrl.toString()
                    db.collection("catPost")
                        .whereEqualTo("userId",G.userAccount!!.id)
                        .get()
                        .addOnSuccessListener { documents->
                            for (document in documents){
                                document.reference.update(CatPostUpdate)
                            }
                        }

                    val dogCommentUpdate= HashMap<String,Any>()
                    dogCommentUpdate ["nickname"] = newNickname
                    dogCommentUpdate["imgUrl"] = profileUrl.toString()
                    db.collection("DogComment")
                        .whereEqualTo("uid",G.userAccount!!.id)
                        .get()
                        .addOnSuccessListener { documments->
                            for (documment in documments){
                                documment.reference.update(dogCommentUpdate)
                            }
                        }
                    val catCommentUpdate = HashMap<String,Any>()
                    catCommentUpdate ["nickname"] = newNickname
                    catCommentUpdate["imgUrl"] = profileUrl.toString()
                    db.collection("CatComment")
                        .whereEqualTo("uid",G.userAccount!!.id)
                        .get()
                        .addOnSuccessListener { documments->
                            for (documment in documments){
                                documment.reference.update(catCommentUpdate)
                            }
                        }
                }
            }

        Binding.etNameChange.text.clear()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Pcontext = context //초기화한 콘텍스트를 Fragment에 연결 해 프로필 및 닉네임을 변경하여도 앱이 강제적으로 종료되는 현상을 방지
    }

}
