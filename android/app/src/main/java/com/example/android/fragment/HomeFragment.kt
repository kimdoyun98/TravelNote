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
import com.example.android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    val viewModel: GetPostingViewModel by viewModels()
    lateinit var binding : FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        Log.e("HomeFragment", "start")

        val adapter = HomeAdapter(viewModel, this)
        binding.homeRecyclerview.adapter = adapter

        viewModel.getPostingInServer()

        viewModel.posting.observe(viewLifecycleOwner) {
            adapter.setPosting(it)
            Log.e("setPosting", it.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentHomeBinding.inflate(layoutInflater).root
    }
}