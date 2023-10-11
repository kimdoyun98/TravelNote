package com.example.android.common

import android.app.Application
import android.os.Build
import java.util.*
import java.util.prefs.Preferences

class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var decode: String
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        decode = decodeToken(prefs.getString("token", "Decode Token"))
        super.onCreate()
    }

    fun decodeToken(jwt : String): String{
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return "Requires SDK 26"
        val parts = jwt.split(".")
        return try {
            val charset = charset("UTF-8")
            val header = String(Base64.getUrlDecoder().decode(parts[0].toByteArray(charset)), charset)
            val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
            "$header"
            "$payload"
        } catch (e: Exception) {
            "Error parsing JWT: $e"
        }
    }
}