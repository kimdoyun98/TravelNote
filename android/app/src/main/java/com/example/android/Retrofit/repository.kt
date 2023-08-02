package com.example.android.Retrofit

import com.example.android.Retrofit.DTO.signupDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface repository {
    @FormUrlEncoded
    @POST("/accounts/signup/")
    fun postSignup(@Field("username") username:String, @Field("password") password:String): Single<signupDTO>

}