package com.cys.honeydog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivitySearchPwBinding

class SearchPwActivity : AppCompatActivity() {
    val binding:ActivitySearchPwBinding by lazy { ActivitySearchPwBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}