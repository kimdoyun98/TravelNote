package com.example.android

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.retrofit.NetworkManager
import com.example.android.retrofit.dto.SearchUser
import com.example.android.retrofit.httpRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchUserViewModel : ViewModel(){
    private val retrofit = NetworkManager.getRetrofitInstance().create(httpRepository::class.java)

    private var _userList = MutableLiveData<ArrayList<SearchUser>>()
    var userList : LiveData<ArrayList<SearchUser>> = _userList


    fun getUserList(key : String?){
        retrofit.searchUser(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _userList.postValue(it)
                },
                {
                    Log.e("GetPostingViewModel", it.message.toString())
                }
            )
    }
}