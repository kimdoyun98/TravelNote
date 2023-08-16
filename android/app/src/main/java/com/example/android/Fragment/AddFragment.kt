package com.example.android.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.Activity.AddActivity
import com.example.android.R


class AddFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            val intent = Intent(context, AddActivity::class.java)
            startActivity(intent)
        }
        activity?.finish()
    }
}