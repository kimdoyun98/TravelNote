package com.example.android.activity.post

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.android.adapter.searchaddress.AddressAdapter
import com.example.android.databinding.ActivityAddressSearchBinding
import com.example.android.retrofit.NetworkManager
import com.example.android.retrofit.httpRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class AddressSearchActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddressSearchBinding
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.addressToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = AddressAdapter().apply {
            setAddress(null)
        }

        adapter.setOnItemClickListener(object : AddressAdapter.OnItemClickListener {
            override fun onItemClicked(address: String?, address2: String?) {
                intent.apply {
                    putExtra("address", address)
                    //putExtra("address2", address2)
                    setResult(RESULT_OK, intent)
                }
                finish()
            }
        })

        binding.addressRecyclerView.adapter = adapter

        val retrofit = NetworkManager.getAddressSearchInstance().create(httpRepository::class.java)

        binding.addressSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("setOnQueryTextListener", query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                retrofit.getAddress(newText)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            adapter.setAddress(it.result.juso)
                        },
                        {
                            Log.e("onQueryTextChange", "${it.message}")
                        }
                    )

                return false
            }

        })
    }

    // 메모리 누수 방지
    override fun onDestroy() {
        super.onDestroy()
        disposable?.let{ disposable!!.dispose() }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}