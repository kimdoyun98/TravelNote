package com.example.android.adapter.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.android.GetPostingViewModel
import com.example.android.databinding.HomeItemBinding
import com.example.android.retrofit.dto.PostingData

class HomeAdapter(
    private val getPostingViewModel: GetPostingViewModel,
    private val lifecycleOwner: LifecycleOwner)
    : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private lateinit var binding: HomeItemBinding
    private var postingArray = ArrayList<PostingData>()

    inner class ViewHolder(view: View,
                           private val getPostingViewModel: GetPostingViewModel,
                           private val lifecycleOwner: LifecycleOwner):RecyclerView.ViewHolder(view)
    {
//        private val homeItem = HomeItemBinding.bind(view).apply {
//            viewModel = getPostingViewModel
//            lifecycleOwner = this@ViewHolder.lifecycleOwner //이거 안해주면 databinding에서 안바뀜;
//        }

        fun bind(position: Int){
            binding.postingData = postingArray[position]

            //ViewPager
            binding.picturesViewpager.apply {
                adapter = HomeViewpagerAdapter(postingArray[position].photo)
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
//                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//                    override fun onPageSelected(position: Int) {
//                        super.onPageSelected(position)
//                        val view = (getChildAt(0) as RecyclerView).layoutManager?.findViewByPosition(position) // 표시된 아이템의 뷰 객체
//                        view?.post { // UI 스레드에서 해당 뷰 height를 다시 설정해서 그려줌
//                            val wMeasureSpec =
//                                View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
//                            val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//                            view.measure(wMeasureSpec, hMeasureSpec)
//
//                            if (getChildAt(0).layoutParams.height != view.measuredHeight) {
//                                getChildAt(0).layoutParams = (getChildAt(0).layoutParams).also { lp ->
//                                    lp.height = view.measuredHeight
//                                    Log.e("onPageSelected", lp.height.toString()) // 길이 0.. 아마 image setting 이전에 측정해서 0이 나오는게 아닐까
//                                }
//                            }
//                        }
//                    }
//                })
            }
        }

    }

    fun setPosting(posting:ArrayList<PostingData>){
        postingArray = posting
        notifyDataSetChanged() //TODO 데이터 꼬임 일어날 예정
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        binding = HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            viewModel = getPostingViewModel
            lifecycleOwner = this.lifecycleOwner
        }
        return ViewHolder(binding.root, getPostingViewModel, lifecycleOwner)
    }

    // Set
    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return postingArray.size
    }
}