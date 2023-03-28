package com.cys.honeydog.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

        val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)
        setContentView(binding.root)
        binding.newId.setOnClickListener{clickId()}

    }

    fun clickId(){
        val intent=Intent(this,NewIdActivity::class.java)
        startActivity(intent)
    }

}