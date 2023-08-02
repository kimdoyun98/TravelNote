package com.example.android.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android.R
import com.example.android.Retrofit.NetworkManager
import com.example.android.Retrofit.repository
import com.example.android.databinding.ActivityMainBinding
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
            val username = binding.signupIdEdit.text.toString()
            val password = binding.signupPasswordEdit.text.toString()
            getRepository(
                username,
                password
            )
        }


    }

    private fun getRepository(username: String, password: String) {
        NetworkManager.getRetrofitInstance().create(repository::class.java)
            .postSignup(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                Log.d("Success", "getRepository Success: ${it.username}")
            }, {
                Log.d("Fail", "getRepository Error: ${it.message}")
            })

    }
}