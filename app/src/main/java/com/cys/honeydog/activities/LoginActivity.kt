package com.cys.honeydog.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.cys.honeydog.G
import com.cys.honeydog.model.UserAccount
import com.cys.honeydog.databinding.ActivityLoginBinding
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity<TextInputLayout> : AppCompatActivity() {

    val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }


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


        //FireBase FireStore DB 에서 이메일 과 패스워드  로그을 확인
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()




        if (password == "" || email == "") {
            AlertDialog.Builder(this).setMessage("아이디 OR 비밀번호를 입력 해주세요").show()
            binding.etId.selectAll()
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

                    AlertDialog.Builder(this).setMessage("이메일과 비밀번호를 다시 확인해주시기 바랍니다").show()
                    binding.layoutId.requestFocus()
                    binding.etId.selectAll()

                }
            }
    }


}