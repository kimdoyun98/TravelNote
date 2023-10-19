package com.example.android.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.GetPostingViewModel
import com.example.android.activity.MainActivity
import com.example.android.adapter.home.CommentAdapter
import com.example.android.adapter.home.HomeAdapter
import com.example.android.adapter.home.HomeClickEvent
import com.example.android.common.GeoCoder
import com.example.android.common.MyApplication
import com.example.android.databinding.FragmentHomeBinding
import com.example.android.retrofit.NetworkManager
import com.example.android.retrofit.httpRepository
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.navi.Constants
import com.kakao.sdk.navi.NaviClient
import com.kakao.sdk.navi.model.CoordType
import com.kakao.sdk.navi.model.NaviOption
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeFragment : Fragment() {
    val viewModel: GetPostingViewModel by viewModels()
    lateinit var binding : FragmentHomeBinding
    private lateinit var callback: OnBackPressedCallback
    private lateinit var mainActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        Log.e("my username", MyApplication.prefs.getString("username", ""))

        val adapter = HomeAdapter(viewModel, this)

        viewModel.getPostingInServer()

        //포스팅 목록
        viewModel.posting.observe(viewLifecycleOwner) {
            binding.homeRecyclerview.adapter = adapter.apply {
                setPosting(it)
            }
            Log.e("posting.observe", it.toString())
        }

        //댓글
        val commentAdapter = CommentAdapter(this)

        adapter.itemClick(object : HomeClickEvent {
            override fun CommentClickEvent(posting_id: Int) {
                binding.commentSlide.animateOpen()

                viewModel.getComment(posting_id)

                binding.postCommentButton.setOnClickListener {
                    Log.e("comment", binding.comment.text.toString())
                    viewModel.writeComment(posting_id, binding.comment.text.toString())
                    binding.comment.text = null
                }
            }

            override fun navigationClickEvent(address : String) {
                //TODO 내비게이션  latitude : 위도 / longitude : 경도
                val location : android.location.Location = GeoCoder.getXY(mainActivity, address)
                if(NaviClient.instance.isKakaoNaviInstalled(mainActivity)){
                    Log.e("KAKAO", "네비 가능")
                    startActivity(
                        NaviClient.instance.navigateIntent(
                            com.kakao.sdk.navi.model.Location(
                                address,
                                "${location.longitude}",
                                "${location.latitude}"
                            ),
                            NaviOption(
                                coordType = CoordType.WGS84
                            )
                        )
                    )
                }
                else{
                    Log.e("KAKAO", "네비 미설치")
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(Constants.WEB_NAVI_INSTALL)
                        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )
                }
            }
        })

        viewModel.comment.observe(viewLifecycleOwner){
            binding.commentRecycler.adapter = commentAdapter.apply {
                setComment(it)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentHomeBinding.inflate(layoutInflater).root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMyData()
        val keyHash = Utility.getKeyHash(mainActivity)
        Log.e("keyHash", keyHash)
    }

    //뒤로가기
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        var backPressedTime: Long = 0
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.commentSlide.isOpened)
                    binding.commentSlide.animateClose()
                else{
                    if(System.currentTimeMillis() - backPressedTime >= 2000) {
                        backPressedTime = System.currentTimeMillis()
                        Toast.makeText(mainActivity, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        activity?.finish()
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    private fun getMyData(){
        NetworkManager.getRetrofitInstance().create(httpRepository::class.java)
            .getMyData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                Log.e("myData", it.toString())
                MyApplication.prefs.setString("username", it[0].username)
                MyApplication.prefs.setString("pk", it[0].pk.toString())
            }, {
                Log.d("Fail", it.message.toString())
            })
    }

}