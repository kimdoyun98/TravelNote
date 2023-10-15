package com.example.android

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.retrofit.NetworkManager
import com.example.android.retrofit.dto.PostingData
import com.example.android.retrofit.dto.UserPostingData
import com.example.android.retrofit.httpRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class UserPageViewModel : ViewModel() {
    private val retrofit = NetworkManager.getRetrofitInstance().create(httpRepository::class.java)

    //팔로우 상태
    private var _currentFollow = MutableLiveData<Boolean>()
    var currentFollow : LiveData<Boolean> = _currentFollow

    //포스팅 목록
    private var _userPosting = MutableLiveData<ArrayList<UserPostingData>>()
    var userPosting : LiveData<ArrayList<UserPostingData>> = _userPosting

    fun currentUserFollow(follow : Boolean){
        _currentFollow.postValue(follow)
    }

    fun follow(username: String){
        retrofit.followUser(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _currentFollow.postValue(true)
                },
                {
                    Log.e("Follow Error", it.message.toString())
                }
            )
    }

    fun unfollow(username: String){
        retrofit.unFollowUser(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _currentFollow.postValue(false)
                },
                {
                    Log.e("Follow Error", it.message.toString())
                }
            )
    }

    fun userPosting(username: String){
        retrofit.getUserPost(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _userPosting.postValue(it)
                    Log.e("userPostIng", it.toString())
                },
                {
                    Log.e("userPosting", it.message.toString())
                }
            )

    }

}