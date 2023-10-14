package com.example.android.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.GetPostingViewModel
import com.example.android.adapter.home.CommentAdapter
import com.example.android.adapter.home.HomeAdapter
import com.example.android.adapter.home.HomeClickEvent
import com.example.android.common.MyApplication
import com.example.android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    val viewModel: GetPostingViewModel by viewModels()
    lateinit var binding : FragmentHomeBinding
    private lateinit var callback: OnBackPressedCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val adapter = HomeAdapter(viewModel, this)
        binding.homeRecyclerview.adapter = adapter

        viewModel.getPostingInServer()

        //포스팅 목록
        viewModel.posting.observe(viewLifecycleOwner) {
            adapter.setPosting(it)
            Log.e("posting.observe", it.toString())
        }

        //댓글
        val commentAdapter = CommentAdapter(this)

        adapter.commentClick(object : HomeClickEvent {
            override fun CommentClickEvent(posting_id: Int) {
                binding.commentSlide.animateOpen()

                viewModel.getComment(posting_id)

                binding.postCommentButton.setOnClickListener {
                    Log.e("comment", binding.comment.text.toString())
                    viewModel.writeComment(posting_id, binding.comment.text.toString())
                    binding.comment.text = null
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

    //뒤로가기
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.commentSlide.isOpened)
                    binding.commentSlide.animateClose()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}