package com.example.android.common

import android.app.Application
import android.os.Build
import com.example.android.Secret
import com.kakao.sdk.common.KakaoSdk
import java.util.*
import java.util.prefs.Preferences

class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var decode: String
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()

        KakaoSdk.init(this, Secret.kakaoNativeAppKey)
    }


}