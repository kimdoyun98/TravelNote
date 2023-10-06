package com.example.android.fragment.viewpager.map

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.devs.vectorchildfinder.VectorChildFinder
import com.example.android.GetPostingViewModel
import com.example.android.R
import com.example.android.common.StateUtils
import com.example.android.databinding.FragmentMap2Binding

class CityMap : Fragment() {
    private val viewModel: GetPostingViewModel by viewModels()
    lateinit var binding : FragmentMap2Binding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMap2Binding.bind(view)

        /**
         * 지도 색 칠하기
         */
        val vector = VectorChildFinder(context, R.drawable.koreamap, binding.koreaMap)

        viewModel.getPostingInServer()

        viewModel.posting.observe(viewLifecycleOwner){
            Log.e("MapFragment", it.toString())
            for(i in it){
                val arrayLocation = i.location.split(" ")
                val location = arrayLocation[0]+" "+arrayLocation[1]

                vector.findPathByName(location).fillColor = Color.parseColor(StateUtils.stateColor[arrayLocation[0]])
            }
            binding.koreaMap.invalidate()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map2, container, false)
    }
}