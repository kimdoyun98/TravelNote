package com.example.android.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    lateinit var binding : ActivityPostBinding
    lateinit var paths : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("PostActivity", "open")

        setSupportActionBar(binding.postToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        paths = intent.getSerializableExtra("paths") as ArrayList<String>
        val path = intent.getStringExtra("path")

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


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                val intent = Intent(this, Gallery::class.java)
                startActivity(intent)
            }
            R.id.nextAddToolbar -> {
                //TODO 등록
            }
        }
        finish()
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