package com.example.android.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.SearchUserViewModel
import com.example.android.activity.user.UserPage
import com.example.android.adapter.search_user.SearchUserAdapter
import com.example.android.adapter.search_user.SearchUserClickEvent
import com.example.android.databinding.FragmentSearchBinding
import com.example.android.retrofit.dto.UserData

class SearchFragment : Fragment() {
    lateinit var binding : FragmentSearchBinding
    private val viewModel : SearchUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        val searchUserAdapter = SearchUserAdapter()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    binding.searchedItem.adapter = searchUserAdapter.apply {
                        setUserList(ArrayList())
                    }
                }
                else {
                    viewModel.getUserList(newText)
                    viewModel.userList.observe(viewLifecycleOwner) {
                        binding.searchedItem.adapter = searchUserAdapter.apply {
                            setUserList(it)
                        }
                    }
                }
                return false
            }
        })

        searchUserAdapter.setSearchUserClickEvent(object : SearchUserClickEvent{
            override fun searchUserClick(userdata: UserData) {
                activity?.let {
                    val intent = Intent(context, UserPage::class.java)
                    intent.putExtra("userdata", userdata)
                    startActivity(intent)

                    binding.searchView.setQuery("", false)
                    binding.searchView.clearFocus()
                }
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("SearchFragment", "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentSearchBinding.inflate(layoutInflater).root
    }
}