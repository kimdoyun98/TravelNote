package com.example.android.retrofit

import com.example.android.Secret
import com.example.android.retrofit.DTO.AddressDTO
import com.example.android.retrofit.DTO.TokenData
import com.example.android.retrofit.DTO.signupDTO
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

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

    @GET("addrLinkApi.do?currentPage=1&countPerPage=10&resultType=json")
    fun getAddress(@Query("keyword") keyword: String?,
                   @Query("confmKey") confmkey: String = Secret.AddressSecretKey):Observable<AddressDTO>
}