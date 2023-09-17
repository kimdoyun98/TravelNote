package com.example.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private var _location = MutableLiveData<String>()
    var location : LiveData<String> = _location

    fun setLocation(keyword : String) = viewModelScope.launch {

    }
}