package com.cys.honeydog.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
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

