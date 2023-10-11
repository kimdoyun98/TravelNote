package com.example.android.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.GetPostingViewModel
import com.example.android.adapter.home.HomeAdapter
import com.example.android.adapter.home.HomeClickEvent
import com.example.android.common.MyApplication
import com.example.android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    val viewModel: GetPostingViewModel by viewModels()
    lateinit var binding : FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        Log.e("HomeFragment", "start")
        Log.e("token", MyApplication.prefs.getString("token", ""))
        val adapter = HomeAdapter(viewModel, this)
        binding.homeRecyclerview.adapter = adapter

        viewModel.getPostingInServer()

        viewModel.posting.observe(viewLifecycleOwner) {
            adapter.setPosting(it)
            Log.e("posting.observe", it.toString())
        }

        adapter.commentClick(object : HomeClickEvent {
            override fun readCommentClickEvent(posting_id: Int) {
                binding.commentSlide.animateOpen()
                //TODO 댓글 목록만
            }

            override fun writeCommentClickEvent() {
                TODO("Not yet implemented")
            }

        })




    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentHomeBinding.inflate(layoutInflater).root
    }
}