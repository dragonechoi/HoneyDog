package com.cys.honeydog.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityMainBinding
import com.cys.honeydog.fragments.HomeFragment
import com.cys.honeydog.fragments.ProfilMainFragment
import com.cys.honeydog.fragments.SearchMainFragment

class MainActivity : AppCompatActivity() {
    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(binding.frameLayout.id,HomeFragment()).commit()

        initNavigationBar()
    }

    fun initNavigationBar() {
        binding.bottomNav.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_home -> {
                        changeFragment(HomeFragment())
                    }
                    R.id.menu_search -> {
                        changeFragment(SearchMainFragment())
                    }
                    R.id.proufil -> {
                        changeFragment(ProfilMainFragment())
                    }
                    else -> return@setOnItemSelectedListener false
                }
                true
            }
            selectedItemId = R.id.menu_home
        }
    }


    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.frameLayout.id, fragment).commit()
    }
}
