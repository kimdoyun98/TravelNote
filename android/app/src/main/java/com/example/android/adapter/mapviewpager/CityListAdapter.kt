package com.example.android.adapter.mapviewpager

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.GetPostingViewModel
import com.example.android.common.StateUtils
import com.example.android.databinding.StateListItemBinding

class CityListAdapter(private val getPostingViewModel: GetPostingViewModel) :RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    lateinit var binding : StateListItemBinding
    private var cityList = ArrayList<String>()
    private var locationData = ArrayList<String>()
    lateinit var cityButtonClick: CityButtonClick
    private var select  = false

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(position: Int){
            binding.cityName = cityList[position]

            binding.cityButton.setOnClickListener {
                cityButtonClick.cityButtonClick(cityList[position])
            }

            if(!select){
                //현재 시도(ex 서울특별시)의 방문한 시군구 개수
                var count = 0
                for (location in locationData.distinct()){
                    if(location.contains(cityList[position])) count++
                }
                binding.currentCityCount = count.toString()

                //Button 색
                binding.cityButton.setBackgroundColor(Color.parseColor(StateUtils.stateColor[cityList[position]]))
            }
            else{
                if(locationData.contains(cityList[position])) binding.cityButton.setBackgroundColor(Color.GREEN)
                else binding.cityButton.setBackgroundColor(Color.GRAY)
            }

        }
    }
    fun setCityClickEvent(cityButtonClick: CityButtonClick){
        this.cityButtonClick = cityButtonClick
    }

    fun setCityList(list: ArrayList<String>, data: ArrayList<String>?, _select: Boolean){
        cityList = list
        select = _select
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
        holder.bind(position)
    }

    override fun getItemCount(): Int = cityList.size



}