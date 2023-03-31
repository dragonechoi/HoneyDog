package com.cys.honeydog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cys.honeydog.databinding.ActivitySearchIdBinding

class SearchIdActivity : AppCompatActivity() {
    val binding:ActivitySearchIdBinding by lazy { ActivitySearchIdBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_search_id)
        setContentView(binding.root)



    }
}