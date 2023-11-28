package com.example.android.fragment

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.android.GetPostingViewModel
import com.example.android.R
import com.example.android.common.GeoCoder
import com.example.android.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource

class MapFragment : Fragment(), OnMapReadyCallback{
    lateinit var binding : FragmentMapBinding
    private lateinit var locationSource: FusedLocationSource
    private val viewModel : GetPostingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)

        /**
         * NaverMap
         */
        val mapFragment = childFragmentManager.findFragmentById(R.id.naverMap) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.naverMap, it).commit()
            }
        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, 5000)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentMapBinding.inflate(layoutInflater).root
    }

    override fun onMapReady(naverMap: NaverMap) {
        naverMap.locationSource = locationSource

        naverMap.moveCamera(CameraUpdate.zoomTo(6.0))
        naverMap.moveCamera(CameraUpdate.scrollTo(LatLng(36.5317268372, 127.822090149)))

        viewModel.myPosting.observe(viewLifecycleOwner){
            Log.e("data", it.toString())
            it.forEach { data ->
                val marker = Marker()
                val location = GeoCoder.getXY(requireContext(), data.location)

                // 마커 설정
                marker.width = 60
                marker.height = 70
                marker.iconTintColor = Color.BLUE
                marker.position = LatLng(location.latitude, location.longitude)

                marker.map = naverMap

                marker.setOnClickListener {
                    val dialog = Dialog(requireContext())
                    dialog.setContentView(R.layout.map_info_dialog)

                    val params : WindowManager.LayoutParams = dialog.window!!.attributes
                    params.y = 200
                    dialog.window!!.attributes = params
                    dialog.window!!.setGravity(Gravity.BOTTOM)

                    Glide.with(requireContext())
                        .load(data.photo)
                        .centerCrop()
                        .into(dialog.findViewById(R.id.photo))
                    dialog.findViewById<TextView>(R.id.create_at).text = data.created_at.split("T")[0]
                    dialog.findViewById<TextView>(R.id.caption).text = data.caption

                    dialog.show()

                    false
                }
            }
        }
    }
}