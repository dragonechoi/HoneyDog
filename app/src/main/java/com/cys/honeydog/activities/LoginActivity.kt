package com.cys.honeydog.activities

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cys.honeydog.G
import com.cys.honeydog.UserAccount
import com.cys.honeydog.databinding.ActivityLoginBinding
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity<TextInputLayout> : AppCompatActivity() {

    val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private var backPressedTime: Long = 0 // 뒤로가기 버튼을 누른 시간을 저장할 변수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.newId.setOnClickListener {
            startActivity(Intent(this, NewIdActivity::class.java))
        }
        binding.missId.setOnClickListener {
            startActivity(Intent(this, SearchIdActivity::class.java))
        }
        binding.missPw.setOnClickListener {
            startActivity(Intent(this, SearchPwActivity::class.java))
        }


        binding.btnLogin.setOnClickListener { btnLogin() }

    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 20) { // 2초 이내에 뒤로가기 버튼을 다시 누른 경우
            super.onBackPressed()
        } else {
            backPressedTime = currentTime // 이전 시간을 현재 시간으로 대체
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("멍냥멍냥을 종료 하시겠습니까?")
            builder.setPositiveButton("예",
                DialogInterface.OnClickListener { dialog, which ->
                    finish()
                })
            builder.setNegativeButton("아니오", null)
            builder.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun btnLogin() {
        singIn()
    }

    fun singIn() {

        var email: String = binding.layoutId.editText!!.text.toString()
        var password: String = binding.layoutPw.editText!!.text.toString()


        //FireBase FireStore DB 에서 이메일 과 패스워드  로그 확인
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()




        if (password == "" || email == "") {
            AlertDialog.Builder(this).setMessage("이메일과 비밀번호를 다시 확인해주시기 바랍니다")
                .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        binding.etId.selectAll()
                    }
                }).show()

            return
        }
        db.collection("idUsers")
            .whereEqualTo("id", email)
            .whereEqualTo("password", password)
            .get().addOnSuccessListener {
                if (it.documents.size > 0) {

                    var id: String = it.documents[0].id
                    G.userAccount = UserAccount(id, email)


                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)


                } else {

                    AlertDialog.Builder(this).setMessage("이메일과 비밀번호를 다시 확인해주시기 바랍니다")
                        .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                binding.layoutId.requestFocus()
                                binding.etId.selectAll()
                            }
                        }).show()


                }
            }
    }


}