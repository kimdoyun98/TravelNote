package com.example.android.adapter.mapviewpager

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.android.GetPostingViewModel
import com.example.android.common.StateUtils
import com.example.android.databinding.StateListItemBinding

class CityListAdapter(private val recyclerView: RecyclerView,private val getPostingViewModel: GetPostingViewModel, private val lifecycleOwner: LifecycleOwner) :RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    lateinit var binding : StateListItemBinding
    private var cityList = ArrayList<String>()
    private var locationData = ArrayList<String>()

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(position: Int){
            binding.cityName = cityList[position]

            if(!locationData.isNullOrEmpty()){
                //현재 시도(ex 서울특별시)의 방문한 시군구 개수
                var count = 0
                for (location in locationData.distinct()){
                    if(location.contains(cityList[position])) count++
                }
                binding.currentCityCount = count.toString()

                //Button 색
                binding.cityButton.setBackgroundColor(Color.parseColor(StateUtils.stateColor[cityList[position]]))
            }

        }
    }

    fun setCityList(list: ArrayList<String>, data: ArrayList<String>?){
        cityList = list
        if (data != null) {
            locationData = data
        }
        else locationData.clear()
        notifyItemRangeChanged(0, 17)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = StateListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            viewModel = getPostingViewModel
            lifecycleOwner = this.lifecycleOwner //이거 안해주면 databinding에서 안바뀜;
        }

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Log.e("Click", cityList[position])
            getPostingViewModel.selectState(cityList[position])
        }
        binding.cityButton.setOnClickListener {
            Log.e("ButtonClick", cityList[position])
            getPostingViewModel.selectState(cityList[position])
        }

        holder.bind(position)
    }

    override fun getItemCount(): Int = cityList.size



}