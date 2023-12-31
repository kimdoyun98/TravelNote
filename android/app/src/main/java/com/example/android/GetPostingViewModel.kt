package com.example.android

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.android.common.MyApplication
import com.example.android.retrofit.NetworkManager
import com.example.android.retrofit.dto.Comment
import com.example.android.retrofit.dto.PostingData
import com.example.android.retrofit.dto.UserPostingData
import com.example.android.retrofit.httpRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class GetPostingViewModel : ViewModel() {
    private val retrofit = NetworkManager.getRetrofitInstance().create(httpRepository::class.java)

    private var _posting = MutableLiveData<ArrayList<PostingData>>()
    var posting : LiveData<ArrayList<PostingData>> = _posting

    private var _myPosting = MutableLiveData<ArrayList<UserPostingData>>()
    var myPosting : LiveData<ArrayList<UserPostingData>> = _myPosting

    private var _comment = MutableLiveData<ArrayList<Comment>>()
    val comment : LiveData<ArrayList<Comment>> = _comment

    init {
        getPostingInServer()
        getMyData()
    }

    /**
     * Home Fragment
     */
    // 포스팅 목록
    fun getPostingInServer(){
        retrofit.getPost()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _posting.postValue(it)
                },
                {
                    Log.e("GetPostingViewModel", it.message.toString())
                }
            )
    }
    // 좋아요
    fun likePosting(posting_id:Int){
        retrofit.likePost(posting_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e("success like", it.toString())
                    //getPostingInServer()
                },
                {
                    Log.e("like", it.message.toString())
                }
            )
    }

    //좋아요 취소
    fun unlikePosting(posting_id:Int){
        retrofit.unLikePost(posting_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e("success unlike", it.toString())
                    //getPostingInServer()
                },
                {
                    Log.e("unlike", it.message.toString())
                }
            )
    }

    //댓글
    fun getComment(post_id : Int){
        retrofit.getComment(post_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e("getComment", it.toString())
                    _comment.postValue(it)
                },
                {
                    Log.e("getComment", it.message.toString())
                }
            )

    }
    fun writeComment(post_id: Int, comment: String){
        retrofit.writeComment(post_id, comment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e("writeComment", it.toString())
                    getComment(post_id)
                },
                {
                    Log.e("writeComment", it.message.toString())
                }
            )
    }


    /**
     * Map Fragment
     */
    fun getMyData(){
        retrofit.getUserPost(MyApplication.prefs.getString("username", ""))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _myPosting.postValue(it)
                },
                {
                    Log.e("GetPostingViewModel", it.message.toString())
                }
            )
    }


    object BindingAdapterHelper {
        @BindingAdapter("profileUrl")
        @JvmStatic
        fun setProfileImage(view: ImageView, url: String?) {
            url?.let {
                Glide.with(view.context).load(it).circleCrop().into(view)
            }
        }
    }
}