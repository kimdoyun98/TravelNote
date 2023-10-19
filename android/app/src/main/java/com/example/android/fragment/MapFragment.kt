package com.example.android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.android.adapter.mapviewpager.PagerAdapter
import com.example.android.databinding.FragmentMapBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MapFragment : Fragment() {
    lateinit var binding : FragmentMapBinding
    private val tabTextList = listOf("상세", "지도", "지도상세")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)
        binding.mapViewPager.adapter = PagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.mapViewPager) { tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()
        binding.mapViewPager.setCurrentItem(1, false)

        setTabItemMargin(binding.tabLayout, 15)


    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentMapBinding.inflate(layoutInflater).root
    }

    // TabLayout Tab 사이 간격 부여
    private fun setTabItemMargin(tabLayout: TabLayout, margin: Int) {
        for(i in 0 until 3) {
            val tabs = tabLayout.getChildAt(0) as ViewGroup
            for(i in 0 until tabs.childCount) {
                val tab = tabs.getChildAt(i)
                val lp = tab.layoutParams as LinearLayout.LayoutParams
                lp.marginEnd = margin
                lp.marginStart = margin
                tab.layoutParams = lp
                tabLayout.requestLayout()
            }
        }
    }
}