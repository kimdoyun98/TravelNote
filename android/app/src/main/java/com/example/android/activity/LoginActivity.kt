package com.example.android.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.android.retrofit.NetworkManager
import com.example.android.retrofit.repository
import com.example.android.common.MyApplication
import com.example.android.databinding.ActivityLoginBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupText.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.LoginButton.setOnClickListener{
            getRepository(
                binding.idEdit.text.toString(),
                binding.passwordEdit.text.toString()
            )
        }
    }

    private fun getRepository(username: String, password: String) {
        NetworkManager.getRetrofitInstance().create(repository::class.java)
            .postLogin(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                Log.d("Success", "getRepository Success: ${it.token}")
                MyApplication.prefs.setString("token", it.token)
                Log.d("Token", MyApplication.prefs.getString("token", "token check"))
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            },
                {
                    Log.d("Fail", "getRepository Error: ${it.message}")
                    Toast.makeText(this, "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_LONG).show()
                })

    }
}