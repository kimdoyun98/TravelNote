package com.example.android.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.Secret
import com.example.android.activity.sign.LoginActivity
import com.example.android.adapter.userpage.UserPostingAdapter
import com.example.android.common.MyApplication
import com.example.android.databinding.FragmentMypageBinding
import com.example.android.retrofit.NetworkManager
import com.example.android.retrofit.httpRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class MypageFragment : Fragment() {
    lateinit var binding : FragmentMypageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMypageBinding.bind(view)

        binding.mypageToolbar.title = MyApplication.prefs.getString("username", "")
        getMyData()

        //포스팅 관련
        getPostingList()

        //Toolbar Menu Set
        binding.mypageToolbar.inflateMenu(R.menu.mypage_menu)
        binding.mypageToolbar.setOnMenuItemClickListener {
            binding.slide.animateOpen()
            return@setOnMenuItemClickListener false
        }

        binding.signOut.setOnClickListener {
            customDialog(view)
        }
    }

    private fun customDialog(v : View){
        val dialogView = layoutInflater.inflate(R.layout.logout_dialog, null)
        val builder = AlertDialog.Builder(v.context)
        builder.setView(dialogView)

        val alertDialog : AlertDialog = builder.create()
        alertDialog.show()

        val cancelButton = dialogView.findViewById<Button>(R.id.cancel)
        val signOutButton = dialogView.findViewById<Button>(R.id.logout)

        signOutButton.setOnClickListener {
            MyApplication.prefs.setString("token", null)
            activity?.let {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            alertDialog.dismiss()
        }

        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    private fun getMyData(){
        NetworkManager.getRetrofitInstance().create(httpRepository::class.java)
            .getMyData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                binding.followingCount.text = it[0].following_set.size.toString()

                Glide
                    .with(this)
                    .load("${Secret.BaseUrl}${it[0].avatar_url}")
                    .centerCrop()
                    .into(binding.profile)

            }, {
                Log.d("Fail", it.message.toString())
            })
    }

    private fun getPostingList(){
        NetworkManager.getRetrofitInstance().create(httpRepository::class.java)
            .getUserPost(MyApplication.prefs.getString("username", ""))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    binding.postingCount.text = it.size.toString()
                    binding.myPosting.adapter = UserPostingAdapter().apply {
                        setUserPosting(it)
                    }
                    Log.e("userPostIng", it.toString())
                },
                {
                    Log.e("getPostingList", it.message.toString())
                }
            )
    }
}