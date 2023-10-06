package com.example.android.retrofit

import com.example.android.Secret
import com.example.android.retrofit.dto.AddressDTO
import com.example.android.retrofit.dto.PostingData
import com.example.android.retrofit.dto.TokenData
import com.example.android.retrofit.dto.signupDTO
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface httpRepository {
    @FormUrlEncoded
    @POST("/accounts/signup/")
    fun postSignup(@Field("username") username:String,
                   @Field("password") password:String): Single<signupDTO>

    @FormUrlEncoded
    @POST("/accounts/token/")
    fun postLogin(@Field("username") username:String,
                  @Field("password") password:String):Single<TokenData>

    @FormUrlEncoded
    @POST("/accounts/token/verify/")
    fun postVerifyToken(@Field("token") token:String):Single<TokenData>

    @Multipart
    @POST("/api/posts/")
    fun writePost(@Header("Authorization") token : String,
                  @Part photo : MultipartBody.Part,
                  @Part("caption") caption : RequestBody?,
                  @Part("location") location : RequestBody)
    :Single<Any>

    @GET("/api/posts/")
    fun getPost(@Header("Authorization") token : String) : Observable<ArrayList<PostingData>>

    @GET("addrLinkApi.do?currentPage=1&countPerPage=10&resultType=json")
    fun getAddress(@Query("keyword") keyword: String?,
                   @Query("confmKey") confmkey: String = Secret.AddressSecretKey):Observable<AddressDTO>
}