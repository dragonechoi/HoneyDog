package com.cys.honeydog.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.mainDogCmm.setOnClickListener {
            startActivity(Intent(this,DogCmmActivity::class.java))

        }
        binding.mainCatCmt.setOnClickListener {
            startActivity(Intent(this,CatCmmActivity::class.java))
        }

        binding.mainSearch.setOnClickListener {
            startActivity(Intent(this,MainSearchActivity::class.java))
        }

        binding.mainSeting.setOnClickListener {
            startActivity(Intent(this,MainSettingActivity::class.java))
        }
        binding.tvGoCmm.setOnClickListener {
            startActivity(Intent(this,DogCmmActivity::class.java))
        }



    }
}