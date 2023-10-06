package com.example.android.adapter.mapviewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.android.fragment.MapFragment
import com.example.android.fragment.viewpager.map.StateMap
import com.example.android.fragment.viewpager.map.CityMap
import com.example.android.fragment.viewpager.map.VisitedLocation

class PagerAdapter(fragmentActivity: MapFragment): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> VisitedLocation()
            1 -> StateMap()
            else -> CityMap()
        }
    }
}