package com.example.android.activity.post

import android.Manifest.permission.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.android.adapter.gallery.GalleryAdapter
import com.example.android.R
import com.example.android.CheckPhotoData
import com.example.android.GalleryViewModel
import com.example.android.activity.MainActivity
import com.example.android.databinding.ActivityGalleryBinding

class Gallery : AppCompatActivity() {
    lateinit var binding : ActivityGalleryBinding
    val viewModel:GalleryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.galleryToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkPermission()

        if(ContextCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED){
            // 권한 승인

        }

        /**
         * 사진 가져오기
         */
        val gallery = getGallery()
        //val viewModel = GalleryViewModel()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val galleryAdapter = GalleryAdapter(viewModel, this).apply {
            setGalleryPath(gallery)
        }

        binding.recyclerView2.adapter = galleryAdapter

        viewModel.currentSelectedPhoto.observe(this, Observer {
            Log.e("observe", it)
            Glide
                .with(this)
                .load(it)
                .centerCrop()
                .into(binding.imageView)
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_toolbar, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nextAddToolbar -> {
                Log.e("Gallery", viewModel.multiCheckPhoto.toString())
                val intent = Intent(this, PostActivity::class.java)
                // 갤러리 경로 보내기
                intent.putExtra("path", viewModel.multiCheckPhoto[viewModel.multiCheckPhoto.size-1])
                intent.putExtra("paths", viewModel.multiCheckPhoto)
                startActivity(intent)
            }
        }
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkPermission(){
        val permissionArray =
            arrayOf(
                CAMERA,
                RECORD_AUDIO,
                READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE,
                ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION
            )

        if (permissionArray.all {
                ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED} &&
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissionArray, 1000)
        }
    }

    private fun getGallery(): ArrayList<String> {
        var fileList = ArrayList<String>()
        val url = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_TAKEN
        )

        val cursor = this.contentResolver.query(url, projection, null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val columnIndex = cursor.getColumnIndex(
                    MediaStore.Images.Media.DATA
                )
                val filePath = cursor.getString(columnIndex)
                fileList.add(filePath)
            }
            cursor.close()
        }

        return fileList.reversed() as ArrayList<String>

    }

    object StringToImage {
        fun ArrayList<String>.toSelectImageList(): ArrayList<CheckPhotoData> {
            val arrayList = ArrayList<CheckPhotoData>()
            for (str in this) {
                arrayList.add(CheckPhotoData.create(0, str))
            }
            return arrayList
        }
    }

}