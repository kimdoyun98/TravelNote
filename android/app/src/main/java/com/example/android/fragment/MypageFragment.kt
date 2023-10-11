package com.example.android.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.android.R
import com.example.android.activity.sign.LoginActivity
import com.example.android.common.MyApplication
import com.example.android.databinding.FragmentMypageBinding


class MypageFragment : Fragment() {
    lateinit var binding : FragmentMypageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMypageBinding.bind(view)






        //Toolbar Menu Set
        binding.mypageToolbar.inflateMenu(R.menu.mypage_menu)
        binding.mypageToolbar.setOnMenuItemClickListener {
            binding.slide.animateOpen()
            return@setOnMenuItemClickListener false
        }

        binding.signOut.setOnClickListener {
            Log.e("로그아웃", "CLick")
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
        }

        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }


}