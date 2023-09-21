package com.example.android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R
import com.example.android.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    lateinit var binding : FragmentMapBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)

        binding.koreaMap
        //val vector = VectorChildFinder(context, R.drawable.koreamap, binding.koreaMap)

    }


}