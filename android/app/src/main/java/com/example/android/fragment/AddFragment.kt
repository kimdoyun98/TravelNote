package com.example.android.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.android.activity.post.Gallery


class AddFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            val intent = Intent(context, Gallery::class.java)
            startActivity(intent)
        }
        activity?.finish()
    }
}