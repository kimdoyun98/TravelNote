package com.example.android.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.android.R
import com.example.android.activity.sign.LoginActivity
import com.example.android.retrofit.NetworkManager
import com.example.android.retrofit.repository
import com.example.android.common.MyApplication
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            getRepository()
        },
            2000)
    }

    private fun getRepository() {
        NetworkManager.getRetrofitInstance().create(repository::class.java)
            .postVerifyToken(MyApplication.prefs.getString("token", "token get"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                Log.d("Success", "getRepository Success: ${it.token}")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, {
                Log.d("Fail", MyApplication.prefs.getString("token", "get token"))
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            })

    }
}

