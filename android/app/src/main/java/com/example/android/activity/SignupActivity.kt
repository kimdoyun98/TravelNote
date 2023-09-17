package com.example.android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.android.retrofit.NetworkManager
import com.example.android.retrofit.repository
import com.example.android.databinding.ActivitySignupBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SignupActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SignupButton.setOnClickListener {
            if (binding.signupPasswordEdit.text.toString() !=
                binding.signupCheckPassword.text.toString()){
                Toast.makeText(this, "비밀번호 불일치", Toast.LENGTH_LONG).show()
            }
            else{
                getRepository(
                    binding.signupIdEdit.text.toString(),
                    binding.signupPasswordEdit.text.toString()
                )
            }
        }



    }

    private fun getRepository(username: String, password: String) {
        NetworkManager.getRetrofitInstance().create(repository::class.java)
            .postSignup(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                Log.d("Success", "getRepository Success: ${it.username}")
                Toast.makeText(this, "회원가입 완료", Toast.LENGTH_LONG).show()
                finish()
            }, {
                Log.d("Fail", "getRepository Error: ${it.message}")
                Toast.makeText(this, "아이디 중복", Toast.LENGTH_LONG).show()
            })

    }
}