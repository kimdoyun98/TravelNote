package com.example.android.common

import android.app.Application
import android.content.Context
import android.os.Build
import com.example.android.BuildConfig
import com.example.android.Secret
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk
import java.util.*
import java.util.prefs.Preferences

class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
        private lateinit var myApplication: MyApplication
        fun getInstance() : MyApplication = myApplication
    }
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()

        myApplication = this

        KakaoSdk.init(this, Secret.kakaoNativeAppKey)

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.Client_ID)
    }
}