package com.example.android.retrofit.dto

import com.google.gson.annotations.SerializedName

data class AddressDTO(
    @SerializedName("results") val result : Result
){
    data class Result(
        @SerializedName("juso") val juso : ArrayList<Juso>
    ){
        data class Juso(
            @SerializedName("jibunAddr") val jibunAddr : String,
            @SerializedName("roadAddr") val roadAddr : String
        )
    }
}
