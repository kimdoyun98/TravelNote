package com.example.android.adapter.home

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SlidingDrawer
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.android.GetPostingViewModel
import com.example.android.R
import com.example.android.databinding.HomeItemBinding
import com.example.android.retrofit.dto.PostingData

class HomeAdapter(
    private val getPostingViewModel: GetPostingViewModel,
    private val lifecycleOwner: LifecycleOwner)
    : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private lateinit var binding: HomeItemBinding
    private var postingArray = ArrayList<PostingData>()
    private lateinit var commentClickEvent : HomeClickEvent

    fun setPosting(posting:ArrayList<PostingData>){
        Log.e("HomeAdapter", "setPosting")
        postingArray.clear()
        postingArray = posting
        notifyItemRangeChanged(0, postingArray.size)
        Log.e("notifyDataSetChanged", postingArray.toString())
    }

    fun commentClick(homeClickEvent: HomeClickEvent){
        commentClickEvent = homeClickEvent
    }

    inner class ViewHolder(view: View,
                           private val getPostingViewModel: GetPostingViewModel,
                           private val lifecycleOwner: LifecycleOwner):RecyclerView.ViewHolder(view)
    {
//        private val homeItem = HomeItemBinding.bind(view).apply {
//            viewModel = getPostingViewModel
//            lifecycleOwner = this@ViewHolder.lifecycleOwner //이거 안해주면 databinding에서 안바뀜;
//        }
        init {
            val position : Int = layoutPosition
            Log.e("position", position.toString())

        }

        fun bind(position: Int){
            /**
             * Data Set
             */
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
            /**
             * Click Event
             */
            //좋아요 클릭 이벤트
            binding.favoriteButton.setOnClickListener {
                //TODO viewmodel에 서버 좋아요 유저 update
                val username = postingArray[position].author.username // 포스팅 유저(현재) -> 로그인 유저로 바꿔야함
                if(!postingArray[position].is_like)
                    getPostingViewModel.likePosting(postingArray[position].id)
                else
                    getPostingViewModel.unlikePosting(postingArray[position].id)

                getPostingViewModel.getPostingInServer()
            }

            //댓글 버튼
            binding.commentButton.setOnClickListener {
                commentClickEvent.readCommentClickEvent(postingArray[position].id)
            }

            //댓글 쓰기 버튼
            binding.addComment.setOnClickListener {
                commentClickEvent.writeCommentClickEvent()
            }

            binding.executePendingBindings()
        }

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

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return postingArray.size
    }
}