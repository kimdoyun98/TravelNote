package com.example.android.fragment.viewpager.map

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devs.vectorchildfinder.VectorChildFinder
import com.example.android.GetPostingViewModel
import com.example.android.R
import com.example.android.adapter.mapviewpager.CityListAdapter
import com.example.android.common.StateUtils
import com.example.android.databinding.FragmentVisitedLocationBinding

class VisitedLocation : Fragment() {
    lateinit var binding : FragmentVisitedLocationBinding
    val viewModel:GetPostingViewModel by viewModels()
    lateinit var adapter : CityListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVisitedLocationBinding.bind(view)

        viewModel.getPostingInServer()

        /**
         * 시도 버튼 클릭 유무
         */
        viewModel.selectState.observe(viewLifecycleOwner){
            Log.e("selectState", it)
            val selectState = it
            val vector : VectorChildFinder

            if(it == "No Select") {
                binding.koreaStateMap2.setImageResource(R.drawable.ic_koreamap2)
                vector = VectorChildFinder(context, R.drawable.ic_koreamap2, binding.koreaStateMap2)
                binding.toolbarTitle.text = "지역"
                binding.toolbarBackButton.visibility = View.GONE
            }
            else {
                //상제 지도
                binding.koreaStateMap2.setImageResource(R.drawable.koreamap)
                vector = VectorChildFinder(context, R.drawable.koreamap, binding.koreaStateMap2)
                binding.toolbarTitle.text = it
                binding.toolbarBackButton.visibility = View.VISIBLE
            }

            /**
             * 포스팅 한 지역 색칠 및 adapter setting
             */
            viewModel.posting.observe(viewLifecycleOwner){
                val locationData = ArrayList<String>()

                for(i in it){
                    //지도 색 칠
                    val arrayLocation = i.location.split(" ")
                    val stateName = arrayLocation[0]
                    val cityName = arrayLocation[1]
                    if(selectState == "No Select"){
                        vector.findPathByName(stateName).fillColor = Color.parseColor(StateUtils.stateColor[stateName])
                        //adapter에 전송할 data
                        locationData.add("$stateName $cityName")
                    }
                    else{
                        vector.findPathByName("$stateName $cityName").fillColor = Color.parseColor(StateUtils.stateColor[stateName])
                    }

                }
                binding.koreaStateMap2.invalidate()

                adapter = CityListAdapter(binding.mapRecyclerView, viewModel, this)

                binding.mapRecyclerView.adapter = adapter.apply {
                    if(selectState == "No Select") setCityList(StateUtils.stateList, locationData)
                    else adapter.setCityList(StateUtils.hashList[selectState]!!, null)
                }

            }

        }

        binding.toolbarBackButton.setOnClickListener {
            viewModel.selectState("No Select")
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_visited_location, container, false)
    }

}