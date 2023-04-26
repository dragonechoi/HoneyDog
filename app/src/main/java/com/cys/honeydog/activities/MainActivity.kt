package com.cys.honeydog.activities

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityMainBinding
import com.cys.honeydog.fragments.HomeFragment
import com.cys.honeydog.fragments.ProfilMainFragment
import com.cys.honeydog.fragments.SearchMainFragment


class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var backPressedTime: Long = 0 // 뒤로가기 버튼을 누른 시간을 저장할 변수



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(binding.frameLayout.id, HomeFragment())
            .commit()

        initNavigationBar()


    }



    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 20) { // 2초 이내에 뒤로가기 버튼을 다시 누른 경우
            super.onBackPressed()
        } else {
            backPressedTime = currentTime // 이전 시간을 현재 시간으로 대체
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("앱을 종료하시겠습니까?")
            builder.setPositiveButton("예",
                DialogInterface.OnClickListener { dialog, which ->
                    finish() // 앱을 종료
                })
            builder.setNegativeButton("아니오", null)
            builder.show()
        }
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

