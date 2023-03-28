package com.cys.honeydog.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cys.honeydog.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

        val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_login)
        setContentView(binding.root)
        binding.newId.setOnClickListener{clickNewId()}
        binding.missId.setOnClickListener{clickMissId()}
        binding.missPw.setOnClickListener{clickMissPw()}



    }
    fun clickMissPw(){
        val intent=Intent(this,SearchPwActivity::class.java)
        startActivity(intent)
    }
    fun clickMissId(){
        val intent=Intent(this,SearchIdActivity::class.java)
        startActivity(intent)
    }

    fun clickNewId(){
        val intent=Intent(this,NewIdActivity::class.java)
        startActivity(intent)
    }



}