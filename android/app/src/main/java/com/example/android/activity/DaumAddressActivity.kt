package com.example.android.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import com.example.android.PostViewModel
import com.example.android.Secret
import com.example.android.databinding.ActivityLocationSetBinding

class DaumAddressActivity : AppCompatActivity() {
    lateinit var binding : ActivityLocationSetBinding
    val viewModel: PostViewModel by viewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationSetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("다음주소검색", "Load")

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.addJavascriptInterface(BridgeInterface(), "Android")

        binding.webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                binding.webView.loadUrl("javascript:sample2_execDaumPostcode();")
            }
        }

        binding.webView.loadUrl(Secret.BaseUrl+"/searchaddress")

    }

    private inner class BridgeInterface {
        @JavascriptInterface
        fun processData(data:String) {
            Log.e("data", data)
            Intent().apply {
                putExtra("data", data)
                setResult(RESULT_OK, this)
            }
            finish()
        }
    }
}