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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Android 13 버전(api 33) 부터 알림에 대한 동적 퍼미션이 추가됨
        //이 앱이 알림에 대한 퍼미션을 허용한 상태인지 체크


        //Android 13 버전(api 33) 부터 알림에 대한 동적 퍼미션이 추가됨
        //이 앱이 알림에 대한 퍼미션을 허용한 상태인지 체크
        val checkResult =
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        if (checkResult == PackageManager.PERMISSION_DENIED) {
            //알림 허용요청하는 다이알로그를 보이기
            //requestPermission(); //예전방식
            //퍼미션 요청 결과를 받아주는 대행사 객체를 이용함.
            permissionResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }

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
    var permissionResultLauncher = registerForActivityResult<String, Boolean>(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) Toast.makeText(this, "알림 허용", Toast.LENGTH_SHORT)
            .show() else Toast.makeText(this, "알림을 보낼수 없습니다.", Toast.LENGTH_SHORT)
            .show()
    }

}