package com.example.android.retrofit

import com.example.android.Secret
import com.example.android.retrofit.dto.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface httpRepository {
    /**
     * 로그인 관련
     */
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

    /**
     * 포스팅 관련
     */
    // 포스팅 작성
    @Multipart
    @POST("/api/posts/")
    fun writePost(@Part photo : MultipartBody.Part,
                  @Part("caption") caption : RequestBody?,
                  @Part("location") location : RequestBody)
    :Single<Any>

    //포스팅 목록 리스트
    @GET("/api/posts/")
    fun getPost() : Observable<ArrayList<PostingData>>

    //포스팅 좋아요 & 취소
    @POST("/api/posts/{post_id}/like/")
    fun likePost(@Path("post_id") post_id : Int) : Single<Any>

    @DELETE("/api/posts/{post_id}/like/")
    fun unLikePost(@Path("post_id") post_id : Int) : Single<Any>

    //포스팅 댓글
    @GET("/api/posts/{post_id}/comments/")
    fun getComment(@Path("post_id") post_id : Int) : Single<ArrayList<Comment>>

    @FormUrlEncoded
    @POST("/api/posts/{post_id}/comments/")
    fun writeComment(@Path("post_id") post_id : Int, @Field("message") comment: String) : Single<Any>

    /**
     * 공공 api 도로명 주소
     */
    @GET("addrLinkApi.do?currentPage=1&countPerPage=10&resultType=json")
    fun getAddress(@Query("keyword") keyword: String?,
                   @Query("confmKey") confmkey: String = Secret.AddressSecretKey):Observable<AddressDTO>
}