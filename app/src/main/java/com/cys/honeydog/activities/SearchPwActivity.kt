package com.cys.honeydog.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cys.honeydog.UserProfile
import com.cys.honeydog.databinding.ActivitySearchPwBinding

class SearchPwActivity : AppCompatActivity() {
    val binding: ActivitySearchPwBinding by lazy { ActivitySearchPwBinding.inflate(layoutInflater) }
    private val UserProfile: UserProfile? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            Toast.makeText(this, "구현 예정", Toast.LENGTH_SHORT).show()
        }
    }


}