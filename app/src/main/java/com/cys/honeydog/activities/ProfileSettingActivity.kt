package com.cys.honeydog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityProfileSettingBinding

class ProfileSettingActivity : AppCompatActivity() {
    val binding:ActivityProfileSettingBinding by lazy { ActivityProfileSettingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



    }
}