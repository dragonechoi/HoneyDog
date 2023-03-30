package com.cys.honeydog.activities

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cys.honeydog.R
import com.cys.honeydog.databinding.ActivityNewIdBinding
import com.google.firebase.firestore.FirebaseFirestore


class NewIdActivity : AppCompatActivity() {
    val binding: ActivityNewIdBinding by lazy { ActivityNewIdBinding.inflate(layoutInflater) }
    var ac: AutoCompleteTextView? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ac = binding.acTv
        val animal = resources.getStringArray(R.array.dog)
        val adapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_dropdown_item_1line, animal)
        ac!!.setAdapter(adapter)

        binding.clickBtn.setOnClickListener { clickBtn() }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    fun clickBtn() {
        var id: String = binding.etEmail.text.toString()
        var password: String = binding.etPw.text.toString()
        var passwordConfirm: String = binding.etPwRecheck.text.toString()
        var nickname: String = binding.etName.text.toString()
        var animalType: String = binding.acTv.text.toString()


        val db: FirebaseFirestore = FirebaseFirestore.getInstance()




        if (password != passwordConfirm) {
            AlertDialog.Builder(this).setMessage("패스워드 확인에 문제가 생겼습니다").show()
            binding.etPwRecheck.selectAll()

        }else if(id==""){
            AlertDialog.Builder(this).setMessage("아이디 입력에 문제가 생겼습니다").show()
            binding.etEmail.selectAll()
            return
        }



        db.collection("idUsers")
            .whereEqualTo("id", id)
            .get().addOnSuccessListener {
                if (it.documents.size > 0) {
                    AlertDialog.Builder(this)
                        .setMessage("중복된 ID(아이디)가 있습니다\n다시 확인한 후 입력 해주시기 바랍니다.").show()
                    binding.etEmail.requestFocus()
                    binding.etEmail.selectAll()
                } else {
                    val user: MutableMap<String, String> = mutableMapOf()
                    user.put("id", id)
                    user.put("password", password)
                    user.put("nickname", nickname)
                    user.put("animalType", animalType)

                    db.collection("idUsers").add(user).addOnSuccessListener {
                        AlertDialog.Builder(this)
                            .setMessage("환영합니다 \n 회원가입에 성공 하였습니다")
                            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    finish()
                                }

                            }).show()
                    }.addOnFailureListener {
             Toast.makeText(this, "회원가입에 오류가 발생했습니다 \n다시 시도해주세요", Toast.LENGTH_SHORT).show()
    }
 }
}           .addOnFailureListener {
             Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
 }

 }

 }




