package com.example.android.retrofit

import com.example.android.Secret
import com.example.android.common.MyApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object NetworkManager {
    fun getRetrofitInstance(): Retrofit{
        return Retrofit.Builder()
            .client(provideOkhttpClient(AppInterceptor()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Secret.BaseUrl)
            .build()
    }

    fun getAddressSearchInstance():Retrofit{
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Secret.AddressUrl)
            .build()
    }


    private fun provideOkhttpClient(interceptor: AppInterceptor): OkHttpClient =
        OkHttpClient.Builder().run {
            addInterceptor(interceptor)
            build()
        }

    class AppInterceptor() : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain)
                : Response = with(chain) {
            val token = MyApplication.prefs.getString("token", "")
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "jwt $token")
                .build()

            proceed(newRequest)
        }
    }

}