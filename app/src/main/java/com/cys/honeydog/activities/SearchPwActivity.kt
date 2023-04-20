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

        binding.searchBtn.setOnClickListener { Toast.makeText(this, "구현 예정", Toast.LENGTH_SHORT).show()}
    }


}