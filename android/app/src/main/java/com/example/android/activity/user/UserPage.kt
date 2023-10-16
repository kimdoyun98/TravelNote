package com.example.android.activity.user

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.android.Secret
import com.example.android.UserPageViewModel
import com.example.android.adapter.userpage.UserPostingAdapter
import com.example.android.common.MyApplication
import com.example.android.databinding.ActivityUserPageBinding
import com.example.android.retrofit.dto.UserData

class UserPage : AppCompatActivity() {
    private val viewModel : UserPageViewModel by viewModels()
    lateinit var binding : ActivityUserPageBinding
    lateinit var userData : UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.userPageToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userData = intent.getSerializableExtra("userdata") as UserData
        Log.e("UserData", userData.toString())
        Log.e("jwt", MyApplication.prefs.getString("token", ""))

        binding.userPageToolbar.title = userData.username

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.userData = userData

        Glide
            .with(this)
            .load("${Secret.BaseUrl}${userData.avatar_url}")
            .centerCrop()
            .into(binding.profile)
        /**
         * 팔로우
         */
        if(userData.follower_set.contains(MyApplication.prefs.getString("pk", "")))
            viewModel.currentUserFollow(true)
        else viewModel.currentUserFollow(false)

        /**
         * 게시물
         */
        val userPostingAdapter = UserPostingAdapter()
            //TODO 서버에서 유저 관련 데이터 가져오기
        viewModel.userPosting(userData.username)

        viewModel.userPosting.observe(this, Observer{
            binding.userPosting.adapter = userPostingAdapter.apply {
                setUserPosting(it)
            }
            binding.postingCount.text = it.size.toString()
        })



    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }
}