package com.example.android.activity.post

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.android.adapter.preview.PreviewAdapter
import com.example.android.databinding.ActivityPreviewBinding

class PreviewActivity : AppCompatActivity() {
    lateinit var binding: ActivityPreviewBinding
    lateinit var paths : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.previewToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        paths = intent.getSerializableExtra("paths") as ArrayList<String>

        //TODO Viewpager2로 이미지 화면 전환 구현
        binding.previewPager.adapter = PreviewAdapter(paths)
        binding.previewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }

    override fun onBackPressed() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}