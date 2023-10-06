package com.example.android.activity.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.activity.MainActivity
import com.example.android.common.MyApplication
import com.example.android.databinding.ActivityPostBinding
import com.example.android.retrofit.NetworkManager
import com.example.android.retrofit.httpRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PostActivity : AppCompatActivity() {
    lateinit var binding : ActivityPostBinding
    lateinit var paths : ArrayList<String>
    lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("PostActivity", "open")

        setSupportActionBar(binding.postToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        paths = intent.getSerializableExtra("paths") as ArrayList<String>
        path = intent.getStringExtra("path")!! // 좌측 상단 사진 띄워놓을 용도

        binding.pathsSize = paths.size

        if(paths.size > 1){
            binding.postImage.setOnClickListener{
                val intent = Intent(this, PreviewActivity::class.java)
                intent.putExtra("paths", paths)
                startActivity(intent)
            }
        }

        Glide
            .with(this)
            .load(path)
            .centerCrop()
            .into(binding.postImage)

        binding.locationAdd.setOnClickListener {
            val intent = Intent(this, AddressSearchActivity::class.java)
            startActivityForResult(intent, 1)
            //getSearchResult.launch(intent) //TODO intent 된 창이 종료되면 intent 창이 한번 더 뜨는 오류 발생
        }


        //TODO 친구 태그


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                startActivity(Intent(this, Gallery::class.java))
                finish()
            }
            R.id.nextAddToolbar -> {
                if (binding.locationAdd.text == "위치 추가") {
                    Toast.makeText(this, "위치를 추가해주세요.", Toast.LENGTH_LONG).show()
                }
                else {
                    //TODO 등록
                    val photofile = File(path)
                    val photo = RequestBody.create(MediaType.parse("image/jpeg"), photofile)
                    val filePart : MultipartBody.Part = MultipartBody.Part.createFormData("photo","photo.jpg", photo)
                    val caption = RequestBody.create(MediaType.parse("text/plain"), binding.caption.text.toString())
                    val location = RequestBody.create(MediaType.parse("text/plain"), binding.locationAdd.text.toString())

                    val retrofit = NetworkManager.getRetrofitInstance().create(httpRepository::class.java)
                    retrofit.writePost(//photo, caption, location , tag_set, like_user_set
                        "JWT "+ MyApplication.prefs.getString("token",""),
                        filePart,
                        caption,
                        location)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                Toast.makeText(this, "등록했습니다.", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            },
                            {
                                Log.e("writePostFail", it.message.toString())
                                Toast.makeText(this, "실패했습니다.", Toast.LENGTH_LONG).show()
                            }
                        )
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(this, Gallery::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK){
            if(data != null){
                binding.locationAdd.text = data.getStringExtra("address")
            }
        }
    }
}