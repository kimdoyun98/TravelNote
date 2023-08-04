package com.example.android.Retrofit

import com.example.android.Retrofit.DTO.TokenData
import com.example.android.Retrofit.DTO.signupDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface repository {
    @FormUrlEncoded
    @POST("/accounts/signup/")
    fun postSignup(@Field("username") username:String, @Field("password") password:String): Single<signupDTO>

    @FormUrlEncoded
    @POST("/accounts/token/")
    fun postLogin(@Field("username") username:String, @Field("password") password:String):Single<TokenData>

    @FormUrlEncoded
    @POST("/accounts/token/verify/")
    fun postVerifyToken(@Field("token") token:String):Single<TokenData>

}