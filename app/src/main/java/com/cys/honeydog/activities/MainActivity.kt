package com.cys.honeydog.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityMainBinding
import com.cys.honeydog.fragments.HomeFragment
import com.cys.honeydog.fragments.ProfilMainFragment
import com.cys.honeydog.fragments.SearchMainFragment
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(binding.frameLayout.id, HomeFragment())
            .commit()

        initNavigationBar()

    }

    //Main Activity Fragment 화면 전환
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

                    R.id.menu_profil -> {
                        changeFragment(ProfilMainFragment())
                    }

                }
                true
            }
            selectedItemId = R.id.menu_home
        }
    }

    //initNavigationBar 메소드
    fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.frameLayout.id, fragment).commit()
    }////initNavigationBar 메소드 끝

}

