package com.cys.honeydog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.cys.honeydog.G
import com.cys.honeydog.R
import com.cys.honeydog.UserProfile
import com.cys.honeydog.databinding.ActivitySearchPwBinding
import com.google.firebase.firestore.FirebaseFirestore

class SearchPwActivity : AppCompatActivity() {
    val binding:ActivitySearchPwBinding by lazy { ActivitySearchPwBinding.inflate(layoutInflater) }
    private val UserProfile: UserProfile? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {searchPw()}
    }

    private fun searchPw() {
        val firestore = FirebaseFirestore.getInstance()
        val nickname: String = binding.searchNickname.toString()
        val id: String = binding.searchId.toString()

        val idUserCollectionRef = firestore.collection("idUsers").document().collection(G.userAccount!!.id)
        val query = idUserCollectionRef.whereEqualTo("nickname", nickname).whereEqualTo("id", id)

        query.get().addOnSuccessListener { documents ->
            if (documents.size() > 0) {
                val password = documents.documents[0].getString("password")
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Your password is $password")
                builder.setPositiveButton("OK", null)
                builder.show()
            } else {
                // 회원 정보가 일치하지 않는 경우에 대한 처리
                Toast.makeText(this, "회원 정보가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}