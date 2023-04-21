package com.cys.honeydog.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cys.honeydog.Profile
import com.cys.honeydog.databinding.ActivitySearchIdBinding

class SearchIdActivity : AppCompatActivity() {
    val binding: ActivitySearchIdBinding by lazy { ActivitySearchIdBinding.inflate(layoutInflater) }
    val item: MutableList<Profile> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_search_id)
        setContentView(binding.root)

        binding.btnId.setOnClickListener {
            Toast.makeText(this, "구현 예정", Toast.LENGTH_SHORT).show()
        }

    }


}